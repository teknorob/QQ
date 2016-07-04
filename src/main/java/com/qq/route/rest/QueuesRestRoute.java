package com.qq.route.rest;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;

import java.sql.SQLException;
import java.util.Map;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;

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
            
            return page;
        }, getJsonTransformer() );

        get( "/queues/:queueId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            return page;
        }, getJsonTransformer() );

        delete( "/queues/:queueId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            return page;
        }, getJsonTransformer() );
        
        put( "/queues", "application/json", ( request, response ) -> {
            return null;
        }, getJsonTransformer() );
    }
}
