package com.qq.core.route;

import spark.Request;
import spark.Response;

import com.j256.ormlite.support.ConnectionSource;

public abstract class CRUDRoute extends RegistrableRoute
{

    public CRUDRoute( ConnectionSource connectionSource )
    {
        super( connectionSource );
    }

    @Override
    public final Object handle( Request request, Response response ) throws Exception
    {
        request.session( true );

        switch ( request.requestMethod() )
        {
            case "GET":
            case "get":
                return readAction( request, response );
            case "POST":
            case "post":
                return createAction( request, response );
            case "PUT":
            case "put":
                return updateAction( request, response );
            case "DELETE":
            case "delete":
                return deleteAction( request, response );
            default:
                break;
        }
        return null;
    }

    public Object deleteAction( Request request, Response response )
    {
        return response;
    }

    public Object updateAction( Request request, Response response )
    {
        return response;
    }

    public Object createAction( Request request, Response response )
    {
        return response;
    }

    public Object readAction( Request request, Response response )
    {
        return response;
    }

    /**
     * Must be implemented by all CRUD Routes. We can use Spark register the
     * route against HTTP verbs which will map like so: POST -> CREATE GET ->
     * READ PUT -> UPDATE DELETE -> DELETE
     */
    @Override
    public abstract void register();
}
