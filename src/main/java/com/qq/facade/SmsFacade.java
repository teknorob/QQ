package com.qq.facade;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.persist.facade.ModelFacade;
import com.qq.model.Sms;
import com.qq.model.TwilioConfig;
import com.twilio.sdk.Twilio;
import com.twilio.sdk.creator.api.v2010.account.MessageCreator;
import com.twilio.sdk.resource.api.v2010.account.Message;
import com.twilio.sdk.type.PhoneNumber;

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

        Twilio.init( twilioConfig.getAccountSid(), twilioConfig.getAuthToken() );

        Message message = new MessageCreator( twilioConfig.getAccountSid(),
            new PhoneNumber( sms.getToNumber() ), new PhoneNumber( twilioConfig.getFromNumber() ),
            sms.getBody() ).execute();

        System.out.println( message.getSid() );
        return message.getSid();
    }

}
