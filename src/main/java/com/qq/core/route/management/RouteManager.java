package com.qq.core.route.management;

import static spark.Spark.before;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.CRUDRoute;
import com.qq.core.route.RegistrableRoute;
import com.qq.route.StaticContentRoute;
import com.qq.util.LoggerUtil;

public class RouteManager
{

    public static void insertRoutes( ConnectionSource connectionSource ) throws InstantiationException,
                                                                         IllegalAccessException
    {
        Logger logger = LoggerUtil.getLogger();
        before( ( req, res ) -> {
            res.raw().setCharacterEncoding( StandardCharsets.UTF_8.toString() );
            if ( req.pathInfo().endsWith( ".html" ) )
            {
                res.raw().setContentType( "text/html; charset=utf-8" );
            }
            else if ( req.pathInfo().endsWith( ".css" ) )
            {
                res.raw().setContentType( "text/css; charset=utf-8" );
            }
        } );

        Reflections reflections = new Reflections( "com.rocketcomp.route" );
        // Register the CRUD Routes
        try
        {
            Set<Class<? extends CRUDRoute>> allClasses = reflections
                .getSubTypesOf( CRUDRoute.class );

            for ( Class<? extends CRUDRoute> clazz : allClasses )
            {
                clazz.getConstructor( ConnectionSource.class )
                    .newInstance( connectionSource ).register();
                logger.info( "Route Registered: " + clazz.getName() );
            }
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "Couldn't register all routes.", e );
        }

        // Register the other Registrable Routes
        try
        {
            Set<Class<? extends RegistrableRoute>> allClasses = reflections
                .getSubTypesOf( RegistrableRoute.class );

            allClasses.remove( StaticContentRoute.class );
            for ( Class<? extends RegistrableRoute> clazz : allClasses )
            {
                clazz.getConstructor( ConnectionSource.class )
                    .newInstance( connectionSource ).register();
                logger.info( "Route Registered: " + clazz.getName() );
            }

            logger.info(
                "Registering Static Content Route (this must be done last)." );
            new StaticContentRoute( connectionSource ).register();
            logger.info(
                "Route Registered: " + StaticContentRoute.class.getName() );

            logger.info( "All Routes Registered." );

        }
        catch ( Exception e )
        {
            throw new RuntimeException( "Couldn't register all routes.", e );
        }

    }
}
