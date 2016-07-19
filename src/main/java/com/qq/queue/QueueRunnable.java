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

public class QueueRunnable implements Runnable
{
    Queue queue;

    QueueFacade queueFacade;

    TicketFacade ticketFacade;

    UserFacade userFacade;

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

        try
        {
            // While the queue exists
            while ( ( queue = queueFacade
                .getQueueById( queue.getQueueId() + "" ) ) != null )
            {
                Ticket ticket = ticketFacade
                    .getNextTicketForQueue( queue.getQueueId() + "" );
                if ( ticket == null )
                {
                    // No more tickets in queue.
                    return;
                }
                headUser = userFacade.getUserById( ticket.getUserId() );

                boolean acceptedNotification = true;// = notifyAndWaitForUser(headUser)
                if ( acceptedNotification )
                {

                }
                else
                {
                    if ( offendingUsers.contains( headUser ) )
                    {
                        offendingUsers.remove( headUser );
                    }
                    else
                    {
                        offendingUsers.add( headUser );
                        ticket.setLastUpdated(
                            new Timestamp( System.currentTimeMillis() ) );
                        ticketFacade.updateTicket(ticket);
                    }
                }

                ticketFacade.deleteTicketById( ticket.getTicketId() + "" );
            }
        }
        catch ( SQLException e )
        {
            throw new RuntimeException( e );
        }

    }

}
