package com.qq.route.rest;

import static spark.Spark.get;

import java.sql.SQLException;
import java.util.Map;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;
import com.qq.facade.SmsFacade;
import com.qq.model.Sms;

public class SmsRestRoute extends RegistrableRoute
{

    public SmsRestRoute( final ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
    }

    @Override
    public void register()
    {
        get( "/testSms", "application/json", ( request, response ) -> {
            
            //TODO Need to find a better sms service than twilio... 
            if(true)
            {
                return getNewPageModel( request ); 
            }
            
            
            Sms sms = new Sms();
            sms.setToNumber( "+61402264598" );
            sms.setBody(
                "QuickQueue - This is a test message. If you've received this then your account is set up properly." );

            SmsFacade smsFacade = new SmsFacade( getConnectionSource() );
            String messageId = smsFacade.sendSms( sms );

            Map<String, Object> page = getNewPageModel( request );
            page.put( "messageId", messageId );
            return page;
        }, getJsonTransformer() );

    }
}
