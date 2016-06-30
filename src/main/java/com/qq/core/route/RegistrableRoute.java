package com.qq.core.route;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.JsonTransformer;
import com.qq.facade.UserFacade;
import com.qq.model.User;

import spark.Request;
import spark.Response;
import spark.Route;

public abstract class RegistrableRoute implements Route
{
    private final UserFacade userFacade;

    private final ConnectionSource connectionSource;

    private final JsonTransformer jsonTransformer = new JsonTransformer();

    public RegistrableRoute( ConnectionSource connectionSource )
    {
        this.connectionSource = connectionSource;
        try
        {
            userFacade = new UserFacade( connectionSource );
        }
        catch ( SQLException e )
        {
            throw new RuntimeException( e );
        }
    }

    public ConnectionSource getConnectionSource()
    {
        return connectionSource;
    }

    public JsonTransformer getJsonTransformer()
    {
        return jsonTransformer;
    }

    @Override
    public Object handle( Request request, Response response ) throws Exception
    {
        response.redirect( getFullUrl( request, "/404" ) );
        return response.raw();
    }

    protected String getFullUrl( Request request, String path )
    {
        StringBuilder builder = new StringBuilder( request.host() );
        builder.insert( 0, "http://" );
        builder.append( path );
        return builder.toString();
    }

    protected Map<String, Object> getNewPageModel( Request request ) throws SQLException
    {
        Map<String, Object> page = new HashMap<>();
        User user = (User)request.session().attribute( "user" );
        if ( user != null )
        {
            user = userFacade
                .getUserById( Integer.toString( user.getUserId() ) );
        }
        page.put( "user", user );
        return page;
    }

    public abstract void register();

}
