package com.qq.queue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.model.Queue;

public class QueueManager
{
    public static Map<String, Thread> queueRunnables = new HashMap<>();
    
    public static void buildNewQueueRunnable(Queue queue, ConnectionSource connectionSource) throws SQLException
    {
        QueueRunnable queueRunnable = new QueueRunnable( queue, connectionSource );
        Thread queueThread = new Thread(queueRunnable);
        queueRunnables.put( queue.getQueueId() + "", queueThread );
    }
    
    public static boolean isQueueRunning(Queue queue)
    {
        return queueRunnables.get( queue.getQueueId() + "" ).isAlive();
    }
}
