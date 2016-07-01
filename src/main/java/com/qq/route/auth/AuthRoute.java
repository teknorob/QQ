package com.qq.route.auth;

import static spark.Spark.get;
import static spark.Spark.post;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;

public class AuthRoute extends RegistrableRoute
{
    GoogleIdTokenVerifier verifier;

    public AuthRoute( final ConnectionSource connectionSource )
    {
        super( connectionSource );
        NetHttpTransport transport;
        if ( System.getProperty( "http.proxyHost" ) != null)
        {
            Proxy proxy = new Proxy( Proxy.Type.HTTP,
                new InetSocketAddress( System.getProperty( "http.proxyHost"),
                    Integer.parseInt( System.getProperty( "http.proxyPort" ) ) ) );
            transport = new NetHttpTransport.Builder().setProxy( proxy ).build();
        }
        else
        {
            transport = new NetHttpTransport();
        }

        verifier = new GoogleIdTokenVerifier.Builder( transport, new GsonFactory() )
            .setAudience( Arrays.asList(
                "793252566542-hsgoksf7aq342gsbpq50lnvilem7gbds.apps.googleusercontent.com" ) )
            .setIssuer( "accounts.google.com" ).build();
    }

    public void register()
    {
        get( "/auth", ( request, response ) -> {
            return response.raw();
        } );

        post( "/login", ( request, response ) -> {
            String tokenId = request.queryParams( "idtoken" );
            
            GoogleIdToken idToken = verifier.verify( tokenId );
            if ( idToken != null )
            {
                System.out.println( "=User verified=");
                Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println( "User ID: " + userId );

                // Get profile information from payload
                System.out.println( "Email: " + payload.getEmail() );
                System.out.println( "Email Verified: " + payload.getEmailVerified() );
                System.out.println( "Name: " + (String)payload.get( "name" ) );
                System.out.println( "Picture: " + (String)payload.get( "picture" ) );
                System.out.println( "Locale: " + (String)payload.get( "locale" ) );
                System.out.println( "Family Name: " + (String)payload.get( "family_name" ) );
                System.out.println( "Given Name: " + (String)payload.get( "given_name" ) );
            }
            else
            {
                System.out.println( "nuffin!" );
            }
            return response.raw();
        } );

        get( "/logout", ( request, response ) -> {
            return response.raw();
        } );
    }
}