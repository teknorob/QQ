package com.qq.route.service;

import static spark.Spark.post;
import static spark.Spark.webSocket;

import java.util.Map;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;
import com.qq.queue.QueueManager;
import com.qq.util.SessionUtils;

import spark.Session;

public class InteractionRoute extends RegistrableRoute
{
    QueueManager queueManager;

    public InteractionRoute( ConnectionSource connectionSource )
    {
        super( connectionSource );
        queueManager = new QueueManager( getConnectionSource() );
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
            }
            return page;
        }, getJsonTransformer() );

        post( "/continueQueue", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );

            if ( SessionUtils.isServiceSession( request, getConnectionSource() ) )
            {
                // Tell the queue manager that the queue can now commence for
                // the given queueId
            }
            return page;
        }, getJsonTransformer() );

        webSocket( "/queues", this.getClass() );
    }

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        //Add an observer to the message manager and add  that observer to the queues
        //OnUpdate, send an update through the session.getRemote
    }
    
    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        //Do nothing
    }
    
    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        //Do nothing
    }

}