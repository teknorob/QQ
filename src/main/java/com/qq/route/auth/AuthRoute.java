package com.qq.route.auth;

import static spark.Spark.get;
import static spark.Spark.halt;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.auth.openid4java.SteamOpenID;
import com.qq.core.route.RegistrableRoute;
import com.qq.facade.UserFacade;
import com.qq.model.User;

public class AuthRoute extends RegistrableRoute
{

    private final SteamOpenID openid = null;

    public AuthRoute( final ConnectionSource connectionSource )
    {
        super( connectionSource );
//        openid = new SteamOpenID();
    }

    @Override
    public void register()
    {
        
    }
    
    public void steamRegister()
    {
        get( "/auth", ( request, response ) -> {
            String steamId = openid.verify( request.url(),
                request.queryMap().toMap() );
            request.session( true ).attribute( "steamId", steamId );
            if ( steamId == null )
            {
                // User has not successfully logged in, redirect to root.
                response.redirect( getFullUrl( request, "/" ) );
            }
            else
            {
                try
                {
                    UserFacade userFacade = new UserFacade(
                        getConnectionSource() );
                    User user = userFacade.getUserBySteamId( steamId );

                    // If user does not exist then send to the registration
                    // route
                    if ( user == null )
                    {
                        response.redirect( getFullUrl( request, "/registration" ) );
                    }
                    else
                    {
                        request.session( true ).attribute( "user", user );
                        response.redirect( getFullUrl( request, "/" ) );
                    }

                }
                catch ( SQLException e )
                {
                    throw new RuntimeException( e );
                }
            }
            return response.raw();
        } );

        get( "/login", ( request, response ) -> {
            response.redirect( openid.login( getFullUrl( request, "/auth" ) ) );
            // We should never return here.
            halt( 403, "Preventing OID login provider redirect" );
            return response.raw();
        } );

        get( "/logout", ( request, response ) -> {
            request.session( true ).removeAttribute( "steamId" );
            request.session( true ).removeAttribute( "user" );
            response.redirect( getFullUrl( request, "/" ) );
            return response.raw();
        } );

        get( "/registration", ( request, response ) -> {
            UserFacade userFacade = new UserFacade( getConnectionSource() );
            String steamId = request.session().attribute( "steamId" );
            User user = userFacade.getUserBySteamId( steamId );
            if ( user == null )
            {
                try
                {
                    int userId = userFacade.registerNewUserBySteamId( steamId );
                    user = userFacade
                        .getUserById( Integer.toString( userId ) );
                    request.session().attribute( "user", user );
                }
                catch ( IOException e )
                {
                    response.redirect( getFullUrl( request, "/users" ) );
                    throw new RuntimeException(
                        "Could not obtain user information from steam", e );
                }
            }

            response.redirect( getFullUrl( request, "/" ) );
            return response.raw();
        } );
    }
}