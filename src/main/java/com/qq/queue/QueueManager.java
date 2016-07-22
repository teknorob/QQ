package com.qq.queue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import org.eclipse.jetty.websocket.api.Session;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.facade.QueueFacade;
import com.qq.model.Queue;

public class QueueManager
{
    public static QueueManager singleton;

    private Map<String, QueueRunnable> queueRunnables = new HashMap<>();

    private Map<String, Thread> queueThreads = new HashMap<>();

    private Map<Session, Observer> queueObservers = new HashMap<>();

    private ConnectionSource connectionSource;

    private QueueManager( ConnectionSource connectionSource )
    {
        this.connectionSource = connectionSource;
    }

    public static void init( ConnectionSource connectionSource )
    {
        if ( singleton == null )
        {
            singleton = new QueueManager( connectionSource );
        }
    }

    public static QueueManager getInstance()
    {
        if ( singleton == null )
        {
            throw new RuntimeException( "QueueManager not yet initialized" );
        }
        return singleton;
    }

    public void buildNewQueueRunnable( Queue queue ) throws SQLException
    {
        if ( queueRunnables.get( queue.getQueueId() ) != null )
        {
            queueThreads.get( queue.getQueueId() ).interrupt();
        }
        QueueRunnable queueRunnable = new QueueRunnable( queue, connectionSource );
        Thread queueThread = new Thread( queueRunnable );
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

    /**
     * Adds the observer to all existing queues. Notifications will be json
     * notation of {@link com.qq.queue.QueueState}
     * 
     * @param user
     * @param observer
     */
    public void addObserverToQueues( Session user, Observer observer )
    {
        queueObservers.put( user, observer );
        queueRunnables.forEach( ( key, queueRunnable ) -> {
            queueRunnable.addObserver( observer );
        } );
    }

    public void removeObserverFromQueues( Session user )
    {
        queueRunnables.forEach( ( key, queueRunnable ) -> {
            queueRunnable.deleteObserver( queueObservers.get( user ) );
        } );
        queueObservers.remove( user );
    }

    public void startAllQueues() throws SQLException
    {
        new QueueFacade( connectionSource ).getAllQueues().forEach( queue -> {
            try
            {
                buildNewQueueRunnable( queue );
            }
            catch ( SQLException e )
            {
                throw new RuntimeException( e );
            }
        } );
    }

}
