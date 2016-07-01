package com.qq.route;

import static spark.Spark.get;

import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

import spark.Request;
import spark.Response;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;

public class IndexRoute extends RegistrableRoute
{

    public IndexRoute( ConnectionSource connectionSource )
    {
        super( connectionSource );
    }

    @Override
    public Object handle( final Request request, final Response response )
                                                                          throws Exception
    {
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream(
            "com/qq/ng/pages/index.html" );
                final OutputStream out = response.raw().getOutputStream())
        {
            IOUtils.copy( in, out );
        }
        catch(Exception e)
        {
            response.redirect( getFullUrl( request, "/404" ) );
        }
        return response.raw();
    }

    @Override
    public void register()
    {
        get( "/", this );
        get( "/index", this );
    }

}
