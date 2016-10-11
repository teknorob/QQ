package com.qq.queue;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Observable;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.constants.QueueStateConstants;
import com.qq.facade.QueueFacade;
import com.qq.facade.TicketFacade;
import com.qq.facade.UserFacade;
import com.qq.model.Queue;
import com.qq.model.Ticket;
import com.qq.model.User;
import com.qq.util.LoggerUtil;

public class QueueRunnable extends Observable implements Runnable
{
    Queue queue;

    QueueFacade queueFacade;

    TicketFacade ticketFacade;

    UserFacade userFacade;

    boolean acceptedNotification = false;

    public QueueRunnable( Queue queue,
                          ConnectionSource connectionSource ) throws SQLException
    {
        this.queue = queue;
        queueFacade = new QueueFacade( connectionSource );
        ticketFacade = new TicketFacade( connectionSource );
        userFacade = new UserFacade( connectionSource );
    }

    @Override
    public void run()
    {
        User headUser;
        // List<User> offendingUsers = new ArrayList<User>();
        long waitTime = queue.getTicketExpiryDuration() * 1000;
        try
        {
            // While the queue exists
            while ( ( queue = queueFacade
                .getQueueById( queue.getQueueId() + "" ) ) != null )
            {
                acceptedNotification = false;
                Ticket ticket = ticketFacade
                    .getNextTicketForQueue( queue.getQueueId() + "" );
                if ( ticket == null )
                {
                    // No more tickets in queue.
                    return;
                }
                headUser = userFacade.getUserById( ticket.getUserId() );

                TicketMaster.notifyUser( headUser, ticket, queue );

                // Notify observers that we're waiting for acceptance of a
                // notification
                notifyObservers(
                    new QueueState( ticket, QueueStateConstants.WAITING, false ) );

                // Wait for notification from queue manager or waitTime
                synchronized ( this )
                {
                    wait( waitTime );
                }

                if ( acceptedNotification )
                {
                    LoggerUtil.getLogger()
                        .info( headUser.getUserName() + " accepted notification" );
                    // Notify observers of acknowledgement of acceptance
                    notifyObservers( new QueueState( ticket,
                        QueueStateConstants.ACCEPTED, false ) );
                    synchronized ( this )
                    {
                        wait();
                    }

                    ticketFacade.deleteTicketById( ticket.getTicketId() + "" );
                }
                else
                {
                    // They didn't accept the notification in time
                    // if ( offendingUsers.contains( headUser ) )
                    if ( ticket.isOffender() )
                    {
                        // Remove the re-offender
                        TicketMaster.notifyUserOfTicketRevocation( headUser, ticket,
                            queue );
                        ticketFacade.deleteTicketById( ticket.getTicketId() + "" );
                        LoggerUtil.getLogger().info( headUser.getUserName()
                                + " did not accept notification and was removed from queue" );
                    }
                    else
                    {
                        // Mark them as an offender
                        LoggerUtil.getLogger().info( headUser.getUserName()
                                + " did not accept notification and was requeued" );
                        ticket.setOffender( true );
                        ticket.setLastUpdated(
                            new Timestamp( System.currentTimeMillis() ) );
                        ticketFacade.updateTicket( ticket );
                    }
                }

                // Notify observers that the tickets have updated
                notifyObservers(
                    new QueueState( null, QueueStateConstants.RUNNING, true ) );
            }
        }
        catch ( SQLException | InterruptedException e )
        {
            throw new RuntimeException( e );
        }

    }

    public void setAcceptedNotification()
    {
        acceptedNotification = true;
    }

}
