package com.qq.util.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtility
{

    public static void sendMail( String smtpHost, int smtpPort, boolean auth,
                                 String smtpAuthUser, String smtpAuthPwd, String to,
                                 String from, String body,
                                 String subject ) throws Exception
    {
        Properties props = new Properties();
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.smtp.host", smtpHost );

        if ( smtpPort == 465 )
        {
            props.put( "mail.smtp.socketFactory.port", smtpPort );
            props.put( "mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory" );
        }
        else
        {
            props.put( "mail.smtp.port", smtpPort );
        }

        props.put( "mail.smtp.auth", Boolean.toString( auth ) );
        Session mailSession = Session.getInstance( props,
            auth ? new javax.mail.Authenticator()
            {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication( smtpAuthUser, smtpAuthPwd );
                }
            } : null );

        mailSession.setDebug( true );
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage( mailSession );

        message.setText( body );
        message.setFrom( new InternetAddress( from ) );
        message.setSubject( subject );
        message.addRecipient( Message.RecipientType.TO, new InternetAddress( to ) );

        transport.connect();
        transport.sendMessage( message,
            message.getRecipients( Message.RecipientType.TO ) );
        transport.close();

    }
}