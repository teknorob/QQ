package com.qq.route.rest;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;

import java.sql.SQLException;
import java.util.Map;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;

public class UsersRestRoute extends RegistrableRoute
{

    public UsersRestRoute( final ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
    }

    @Override
    public void register()
    {
        get( "/users", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            return page;
        }, getJsonTransformer() );

        get( "/users/:userId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            return page;
        }, getJsonTransformer() );

        delete( "/users/:userId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            return page;
        }, getJsonTransformer() );

        put( "/users", "application/json", ( request, response ) -> {
            return null;
        }, getJsonTransformer() );
    }
}
