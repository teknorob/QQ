package com.qq.queue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.model.Queue;

public class QueueManager
{
    public Map<String, QueueRunnable> queueRunnables = new HashMap<>();

    public void buildNewQueueRunnable( Queue queue,
                                       ConnectionSource connectionSource ) throws SQLException
    {
        if ( queueRunnables.get( queue.getQueueId() ) != null )
        {
            queueRunnables.get( queue.getQueueId() ).interrupt();
        }
        QueueRunnable queueRunnable = new QueueRunnable( queue, connectionSource );
        queueRunnables.put( queue.getQueueId() + "", queueRunnable );
        queueRunnable.start();
    }

    public boolean isQueueRunning( Queue queue )
    {
        return isQueueRunning( queue.getQueueId() + "" );
    }

    public boolean isQueueRunning( String queueId )
    {
        return queueRunnables.get( queueId ).isAlive();
    }

    public void acceptNotification( String queueId )
    {
        if ( queueRunnables.get( queueId ).isAlive() )
        {
            QueueRunnable queueRunnable = queueRunnables.get( queueId );
            queueRunnable.setAcceptedNotification();
            queueRunnable.notify();
        }
    }

}
