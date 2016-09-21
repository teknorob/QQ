package com.qq.queue;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;

import com.google.api.services.gmail.Gmail;
import com.qq.constants.UserConstants;
import com.qq.model.Queue;
import com.qq.model.Ticket;
import com.qq.model.User;
import com.qq.util.GmailServiceFactory;
import com.qq.util.LoggerUtil;
import com.qq.util.SendEmail;

// This fat controller calls your tickets out
public class TicketMaster
{
    public static final String message = "%1, your ticket has been called for the %2 queue."
            + " Please use the service terminal to accept this notification. "
            + "If you are unable to accept the notification by %3, the next ticket will be called";

    public static final String warning1 = " and you will be requeued on the end of the queue.";

    public static final String warning2 = " and you will be removed from the queue as you have already been requeued.";

    public static void notifyUser( User user, Ticket ticket, Queue queue )
    {
        String userMessage = message.replace( "%1", user.getUserName() )
            .replace( "%2", queue.getQueueName() ).replace( "%3", LocalDateTime.now()
                .plusSeconds( queue.getTicketExpiryDuration() ).toString() );

        switch ( user.getNotificationType() )
        {
            case UserConstants.NOTIFICATION_TYPE_EMAIL:
                LoggerUtil.getLogger().info( "Sending email to: " + user.getEmail() );
                Gmail gmail = GmailServiceFactory.getInstance().getGmailService();
                try
                {
                    SendEmail.sendMessage( gmail, "me", SendEmail.createEmail(
                        user.getEmail(), "QuickQueue", "Your ticket", userMessage ) );
                }
                catch ( MessagingException | IOException e )
                {
                    throw new RuntimeException(
                            "Something done fuckedup during email notification.", e );
                }
                LoggerUtil.getLogger().info( "Email sent to: " + user.getEmail() );
                break;
            default:
                throw new RuntimeException(
                    "Unsupported notification type selected." );
        }
    }
}
