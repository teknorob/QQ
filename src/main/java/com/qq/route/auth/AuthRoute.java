package com.qq.route.auth;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;
import com.qq.facade.UserFacade;
import com.qq.model.QQConfig;
import com.qq.model.User;
import com.qq.util.LoggerUtil;

public class AuthRoute extends RegistrableRoute
{
    GoogleIdTokenVerifier verifier;

    QQConfig qqConfig;

    Logger logger = LoggerUtil.getLogger();

    public AuthRoute( final ConnectionSource connectionSource )
    {
        super( connectionSource );

        qqConfig = new Gson().fromJson(
            new InputStreamReader( AuthRoute.class
                .getResourceAsStream( "/com/qq/config/qq_config.json" ) ),
            QQConfig.class );

        NetHttpTransport transport;
        if( !StringUtils.isEmpty( qqConfig.getHttpProxyHost() ) )
        {
            Proxy proxy = new Proxy( Proxy.Type.HTTP,
                new InetSocketAddress( System.getProperty( qqConfig.getHttpProxyHost() ),
                    Integer.parseInt( System.getProperty( qqConfig.getHttpProxyPort() ) ) ) );
            transport = new NetHttpTransport.Builder().setProxy( proxy ).build();
        }
        else
        {
            transport = new NetHttpTransport();
        }

        verifier = new GoogleIdTokenVerifier.Builder( transport, new GsonFactory() )
            .setAudience( Arrays.asList(
                qqConfig.getGoogleClientId() ) )
            .setIssuer( "accounts.google.com" ).build();
    }

    public void register()
    {
        post( "/login", ( request, response ) -> {
            String tokenId = getJsonTransformer().stringToMap( request.body() )
                .get( "tokenId" );

            GoogleIdToken idToken = verifier.verify( tokenId );
            if ( idToken != null )
            {
                Payload payload = idToken.getPayload();
                String googleId = payload.getSubject();
                UserFacade userFacade = new UserFacade( getConnectionSource() );
                User user = userFacade.getUserByGoogleId( googleId );

                if ( user == null )
                {
                    String userName = (String)payload.get( "name" );
                    String avatarURL = (String)payload.get( "picture" );
                    user = userFacade.createNewUser( userName, googleId, avatarURL );
                    logger.info( "New User Created. Id: " + user.getUserId() );
                }
                request.session( true ).attribute( "user", user );

            }
            Map<String, Object> page = getNewPageModel( request );
            return page;
        }, getJsonTransformer() );

        get( "/logout", ( request, response ) -> {
            request.session( true ).attribute( "user", null );
            Map<String, Object> page = getNewPageModel( request );
            return page;
        }, getJsonTransformer() );
    }
}