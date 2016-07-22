package com.qq.core.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.JsonTransformer;

public abstract class RegistrableWebsocket
{
    private final JsonTransformer jsonTransformer = new JsonTransformer();

    public RegistrableWebsocket()
    {
    }

    public JsonTransformer getJsonTransformer()
    {
        return jsonTransformer;
    }

    public abstract void register();
    
    @OnWebSocketConnect
    public abstract void onConnect(Session user) throws Exception;

    @OnWebSocketClose
    public abstract void onClose(Session user, int statusCode, String reason);

    @OnWebSocketMessage
    public abstract void onMessage(Session user, String message);

}
