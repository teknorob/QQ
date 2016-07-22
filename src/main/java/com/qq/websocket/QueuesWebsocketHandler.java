package com.qq.websocket;

import static spark.Spark.webSocket;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableWebsocket;
import com.qq.queue.QueueManager;

@WebSocket
public class QueuesWebsocketHandler extends RegistrableWebsocket
{
    QueueManager queueManager;

    public QueuesWebsocketHandler()
    {
        super();
        queueManager = QueueManager.getInstance();
    }

    @OnWebSocketConnect
    public void onConnect( Session user ) throws Exception
    {
        queueManager.addObserverToQueues( user, new Observer()
        {
            public void update( Observable obj, Object arg )
            {
                try
                {
                    user.getRemote()
                        .sendString( getJsonTransformer().render( arg ) );
                }
                catch ( IOException e )
                {
                    throw new RuntimeException( e );
                }
            }
        } );
    }

    @OnWebSocketClose
    @OnWebSocketError
    public void onClose( Session user, int statusCode, String reason )
    {
        queueManager.removeObserverFromQueues( user );
    }

    @OnWebSocketMessage
    public void onMessage( Session user, String message )
    {
        // Do nothing
    }

    @Override
    public void register()
    {
        webSocket( "/queuesWS", this.getClass() );
    }

}