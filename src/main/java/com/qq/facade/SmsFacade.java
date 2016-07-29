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
import com.qq.config.QQConfig;
import com.qq.config.TwilioConfig;
import com.qq.core.persist.facade.ModelFacade;
import com.qq.model.Sms;
import com.qq.route.auth.AuthRoute;

public class SmsFacade extends ModelFacade
{
    public SmsFacade( ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
    }

    public String sendSms( Sms sms ) throws IOException
    {
        TwilioConfig twilioConfig = TwilioConfig.build();
        QQConfig qqConfig = QQConfig.build();

        System.getProperties().put( "http.proxyHost", qqConfig.getHttpProxyHost() );
        System.getProperties().put( "http.proxyPort", qqConfig.getHttpProxyPort() );

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property( ClientProperties.PROXY_URI,
            qqConfig.getHttpProxyHost() + ":" + qqConfig.getHttpProxyPort() );

        Client client = ClientBuilder.newClient( clientConfig );

        client.getConfiguration().getProperty( ClientProperties.PROXY_URI );

        WebTarget target = client
            .target( "https://www.google.com/" + twilioConfig.getAccountSid() )
            .path( "Messages" );

        Form form = new Form();
        form.param( "To", "+61402264598" );
        form.param( "From", twilioConfig.getFromNumber() );
        form.param( "MessagingServiceSid", twilioConfig.getAuthToken() );

        target.request( MediaType.APPLICATION_JSON_TYPE ).post(
            Entity.entity( form, MediaType.APPLICATION_FORM_URLENCODED_TYPE ) );

        return null;
    }

}
