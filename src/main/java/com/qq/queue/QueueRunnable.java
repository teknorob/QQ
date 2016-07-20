package com.qq.queue;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.facade.QueueFacade;
import com.qq.facade.TicketFacade;
import com.qq.facade.UserFacade;
import com.qq.model.Queue;
import com.qq.model.Ticket;
import com.qq.model.User;
import com.qq.util.LoggerUtil;

public class QueueRunnable extends Thread
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
        List<User> offendingUsers = new ArrayList<User>();
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

                // Wait for notification from queue manager or waitTime
                this.wait( waitTime );

                if ( acceptedNotification )
                {
                    LoggerUtil.getLogger()
                        .info( headUser.getUserName() + " accepted notification" );
                }
                else
                {
                    if ( offendingUsers.contains( headUser ) )
                    {
                        offendingUsers.remove( headUser );
                        LoggerUtil.getLogger().info( headUser.getUserName()
                                + " did not accept notification and was removed from queue" );
                    }
                    else
                    {
                        LoggerUtil.getLogger().info( headUser.getUserName()
                                + " did not accept notification and was requeued" );
                        offendingUsers.add( headUser );
                        ticket.setLastUpdated(
                            new Timestamp( System.currentTimeMillis() ) );
                        ticketFacade.updateTicket( ticket );
                    }
                }

                ticketFacade.deleteTicketById( ticket.getTicketId() + "" );
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
