package com.qq.route.rest;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;
import com.qq.facade.QueueFacade;
import com.qq.model.Queue;
import com.qq.util.SessionUtils;

public class QueuesRestRoute extends RegistrableRoute
{
    public QueuesRestRoute( final ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
    }

    @Override
    public void register()
    {
        get( "/queues", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            QueueFacade queueFacade = new QueueFacade( getConnectionSource() );
            page.put( "queues", queueFacade.getAllQueues() );
            return page;
        }, getJsonTransformer() );

        get( "/queues/:queueId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            QueueFacade queueFacade = new QueueFacade( getConnectionSource() );
            page.put( "queues",
                queueFacade.getQueueById( request.params( ":teamId" ) ) );
            return page;
        }, getJsonTransformer() );

        delete( "/queues/:queueId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            if ( SessionUtils.isAdminSession( request, getConnectionSource() ) )
            {
                QueueFacade queueFacade = new QueueFacade( getConnectionSource() );
                queueFacade.deleteQueueById( request.params( ":queueId" ) );
            }
            return page;
        }, getJsonTransformer() );

        post( "/queues", "application/json", ( request, response ) -> {
            Queue queue = new ObjectMapper()
                .setDateFormat(
                    new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" ) )
                .readValue( request.body(), Queue.class );
            Map<String, Object> page = getNewPageModel( request );
            if ( SessionUtils.isAdminSession( request, getConnectionSource() ) )
            {
                QueueFacade queueFacade = new QueueFacade( getConnectionSource() );
                queue = queueFacade.createQueue( queue );
            }
            page.put( "queues", queue );
            return page;
        }, getJsonTransformer() );
    }
}
