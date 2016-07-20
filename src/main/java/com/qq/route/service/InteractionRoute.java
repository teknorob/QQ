package com.qq.route.service;

import static spark.Spark.post;

import java.util.Map;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;
import com.qq.util.SessionUtils;

public class InteractionRoute extends RegistrableRoute
{
    public InteractionRoute( ConnectionSource connectionSource )
    {
        super( connectionSource );
    }

    @Override
    public void register()
    {
        post( "/acceptNotification", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );

            if ( SessionUtils.isServiceSession( request, getConnectionSource() ) )
            {
                
            }
            return page;
        }, getJsonTransformer() );
    }

}