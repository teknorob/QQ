package com.qq.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.qq.util.gmailcomponent.LocalServerReceiver;

public class GmailServiceFactory
{
    public static GmailServiceFactory singleton;

    public Gmail gmailService;

    /** Application name. */
    private static final String APPLICATION_NAME = "QuickQueue";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty( "user.home" ), ".credentials/gmail-java-quickstart" );

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory
        .getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved credentials at
     * ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES = Arrays
        .asList( GmailScopes.GMAIL_SEND );

    static
    {
        try
        {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory( DATA_STORE_DIR );
        }
        catch ( Throwable t )
        {
            t.printStackTrace();
            System.exit( 1 );
        }
    }

    private GmailServiceFactory()
    {
        try
        {
            gmailService = buildGmailService();
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Couldn't create gmail service.", e );
        }
    }

    public static GmailServiceFactory getInstance()
    {
        if ( singleton == null )
        {
            singleton = new GmailServiceFactory();
            throw new RuntimeException( "QueueManager not yet initialized" );
        }
        return singleton;
    }

    /**
     * Creates an authorized Credential object.
     * 
     * @return an authorized Credential object.
     * @throws IOException
     */
    private static Credential authorize() throws IOException
    {
        // Load client secrets.
        InputStream in = GmailServiceFactory.class
            .getResourceAsStream( "/com/qq/config/client_secret.json" );
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load( JSON_FACTORY,
            new InputStreamReader( in ) );

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES )
                .setDataStoreFactory( DATA_STORE_FACTORY ).setAccessType( "offline" )
                .build();
        
        Credential credential = new AuthorizationCodeInstalledApp( flow,
            new LocalServerReceiver.Builder().setPort(44340).build() ).authorize( "user" );
        
        System.out
            .println( "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath() );
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * 
     * @return an authorized Gmail client service
     * @throws IOException
     */
    private static Gmail buildGmailService() throws IOException
    {
        Credential credential = authorize();
        return new Gmail.Builder( HTTP_TRANSPORT, JSON_FACTORY, credential )
            .setApplicationName( APPLICATION_NAME ).build();
    }
    
    public Gmail getGmailService()
    {
        return gmailService;
    }
}
