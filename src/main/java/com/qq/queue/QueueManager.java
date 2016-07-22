package com.qq.queue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import org.eclipse.jetty.websocket.api.Session;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.model.Queue;

public class QueueManager
{
    private Map<String, QueueRunnable> queueRunnables = new HashMap<>();
    private Map<String, Thread> queueThreads = new HashMap<>();
    private Map<Session, Observer> queueObservers = new HashMap<>();

    private ConnectionSource connectionSource;

    public QueueManager( ConnectionSource connectionSource )
    {
        this.connectionSource = connectionSource;
    }

    public void buildNewQueueRunnable( Queue queue ) throws SQLException
    {
        if ( queueRunnables.get( queue.getQueueId() ) != null )
        {
            queueThreads.get( queue.getQueueId() ).interrupt();
        }
        QueueRunnable queueRunnable = new QueueRunnable( queue, connectionSource );
        Thread queueThread = new Thread(queueRunnable);
        queueRunnables.put( queue.getQueueId() + "", queueRunnable );
        queueThreads.put( queue.getQueueId() + "", queueThread );
        queueThread.start();
    }

    public boolean isQueueRunning( Queue queue )
    {
        return isQueueRunning( queue.getQueueId() + "" );
    }

    public boolean isQueueRunning( String queueId )
    {
        return queueThreads.get( queueId ).isAlive();
    }

    public void acceptNotification( String queueId )
    {
        if ( queueThreads.get( queueId ).isAlive() )
        {
            QueueRunnable queueRunnable = queueRunnables.get( queueId );
            queueRunnable.setAcceptedNotification();
            queueRunnable.notify();
        }
    }
    
    public void declineNotification( String queueId )
    {
        if ( queueThreads.get( queueId ).isAlive() )
        {
            QueueRunnable queueRunnable = queueRunnables.get( queueId );
            queueRunnable.notify();
        }
    }
    
    public void notifyQueue( String queueId )
    {
        if ( queueThreads.get( queueId ).isAlive() )
        {
            QueueRunnable queueRunnable = queueRunnables.get( queueId );
            queueRunnable.notify();
        }
    }

    public void addObserverToQueues( Session user, Observer observer )
    {
        queueObservers.put( user, observer );
        queueRunnables.forEach( (key, queueRunnable) ->{
            queueRunnable.addObserver( observer );
        });
    }
    
    public void removeObserverFromQueues( Session user )
    {
        queueRunnables.forEach( (key, queueRunnable) ->{
            queueRunnable.addObserver( queueObservers.get( user ) );
        });
               
    }

}
