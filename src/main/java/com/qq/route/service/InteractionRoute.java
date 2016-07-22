package com.qq.route.service;

import static spark.Spark.post;
import static spark.Spark.webSocket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;
import com.qq.model.Ticket;
import com.qq.queue.QueueManager;
import com.qq.util.SessionUtils;

public class InteractionRoute extends RegistrableRoute
{
    QueueManager queueManager;

    public InteractionRoute( ConnectionSource connectionSource ) 
    throws SQLException
    {
        super( connectionSource );
        queueManager = QueueManager.getInstance( );
//        queueManager.startAllQueues();
    }

    @Override
    public void register()
    {
        post( "/acceptNotification", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );

            if ( SessionUtils.isServiceSession( request, getConnectionSource() ) )
            {
                // Tell the queue manager that the notification was accepted for
                // the given queueId
                queueManager.acceptNotification("QUEUEIDGOESHERE");
            }
            return page;
        }, getJsonTransformer() );

        post( "/declineNotification", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );

            if ( SessionUtils.isServiceSession( request, getConnectionSource() ) )
            {
                // Tell the queue manager that the queue can skip to the next
                // ticket for
                // the given queueId
                queueManager.declineNotification("QUEUEIDGOESHERE");
            }
            return page;
        }, getJsonTransformer() );

        post( "/continueQueue", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );

            if ( SessionUtils.isServiceSession( request, getConnectionSource() ) )
            {
                // Tell the queue manager that the queue can now commence for
                // the given queueId
                queueManager.notifyQueue( "QUEUEIDGOESHERE" );
            }
            return page;
        }, getJsonTransformer() );
    }

}