package com.qq.facade;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.google.gson.Gson;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.persist.facade.ModelFacade;
import com.qq.model.QQConfig;
import com.qq.model.Sms;
import com.qq.model.TwilioConfig;
import com.qq.route.auth.AuthRoute;

public class SmsFacade extends ModelFacade
{
    public SmsFacade( ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
    }

    public String sendSms( Sms sms ) throws IOException
    {
        TwilioConfig twilioConfig = new Gson().fromJson(
            new InputStreamReader( SmsFacade.class
                .getResourceAsStream( "/com/qq/config/twilio_config.json" ) ),
            TwilioConfig.class );
        QQConfig qqConfig = new Gson().fromJson(
            new InputStreamReader( AuthRoute.class
                .getResourceAsStream( "/com/qq/config/qq_config.json" ) ),
            QQConfig.class );

       
        System.getProperties().put("http.proxyHost", "localhost");
        System.getProperties().put("http.proxyPort", "3128");
        
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.PROXY_URI, 
            qqConfig.getHttpProxyHost() + ":" + qqConfig.getHttpProxyPort());
        
        Client client = ClientBuilder.newClient(clientConfig);
//        if ( qqConfig.getHttpProxyHost() != null
//                && qqConfig.getHttpProxyPort() != null )
//        {
//            client.property( ClientProperties.PROXY_URI,
//                qqConfig.getHttpProxyHost() + ":" + qqConfig.getHttpProxyPort() );
//        }

        
        client.getConfiguration().getProperty( ClientProperties.PROXY_URI );
        
        WebTarget target = client.target( "https://www.google.com/"
                + twilioConfig.getAccountSid() )
                .path( "Messages" );
        
//        WebTarget target = client.target( "https://api.twilio.com/2010-04-01/Accounts/"
//            + twilioConfig.getAccountSid() )
//            .path( "Messages" );
       

        Form form = new Form();
        form.param( "To", "+61402264598" );
        form.param( "From", twilioConfig.getFromNumber() );
        form.param( "MessagingServiceSid", twilioConfig.getAuthToken() );

        target.request( MediaType.APPLICATION_JSON_TYPE ).post(
            Entity.entity( form, MediaType.APPLICATION_FORM_URLENCODED_TYPE ) );

//      Twilio.init( twilioConfig.getAccountSid(), twilioConfig.getAuthToken() );
//
//      Message message = new MessageCreator( twilioConfig.getAccountSid(),
//          new PhoneNumber( sms.getToNumber() ), new PhoneNumber( twilioConfig.getFromNumber() ),
//          sms.getBody() ).execute();
//
//      System.out.println( message.getSid() );
//      return message.getSid();
        
        return null;
    }

}
