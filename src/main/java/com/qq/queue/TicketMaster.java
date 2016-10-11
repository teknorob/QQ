package com.qq.queue;

import java.time.LocalDateTime;

import com.qq.config.QQConfig;
import com.qq.constants.UserConstants;
import com.qq.model.Queue;
import com.qq.model.Ticket;
import com.qq.model.User;
import com.qq.util.LoggerUtil;
import com.qq.util.email.MailUtility;

// This fat controller calls your tickets out
public class TicketMaster
{
    private static QQConfig qqConfig = QQConfig.build();
    
    private static final String message = "%1, your ticket has been called for the '%2' queue."
            + " Please use the service terminal to accept this notification. "
            + "If you are unable to accept the notification by %3, the next ticket will be called";

    private static final String warning1 = " and you will be requeued on the end of the queue.";

    private static final String warning2 = " and you will be removed from the queue as you have already been requeued.";

    public static void notifyUser( User user, Ticket ticket, Queue queue )
    {
        String userMessage = message.replace( "%1", user.getUserName() )
            .replace( "%2", queue.getQueueName() )
            .replace( "%3", LocalDateTime.now()
                .plusSeconds( queue.getTicketExpiryDuration() ).toString() )
                + ( ticket.isOffender() ? warning2 : warning1 );

        switch ( user.getNotificationType() )
        {
            case UserConstants.NOTIFICATION_TYPE_EMAIL:
                LoggerUtil.getLogger()
                    .info( "Sending email to: " + user.getEmail() );
                try
                {
                    MailUtility.sendMail( qqConfig.getSmtpHost(), qqConfig.getSmtpPort(), false,
                        null, null, user.getEmail(), qqConfig.getFromAddress(),
                        userMessage, "QuickQueue ticket #" + ticket.getTicketId() );
                    LoggerUtil.getLogger()
                        .info( "Email sent to: " + user.getEmail() );
                }
                catch ( Exception e )
                {
                    LoggerUtil.getLogger()
                        .info( "Exception occurred during email send to "
                                + user.getEmail() );
                }

                break;
            default:
                throw new RuntimeException(
                    "Unsupported notification type selected." );
        }
    }

    public static void notifyUserOfTicketRevocation( User user, Ticket ticket,
                                                     Queue queue )
    {
        String userMessage = user.getUserName() + ", your ticket #"
                + ticket.getTicketId() + " has been removed from queue '"
                + queue.getQueueName() + "'";

        switch ( user.getNotificationType() )
        {
            case UserConstants.NOTIFICATION_TYPE_EMAIL:
                LoggerUtil.getLogger()
                    .info( "Sending email to: " + user.getEmail() );
                try
                {
                    MailUtility.sendMail( qqConfig.getSmtpHost(), qqConfig.getSmtpPort(),  false,
                        null, null, user.getEmail(), qqConfig.getFromAddress(),
                        userMessage, "QuickQueue ticket #" + ticket.getTicketId() );
                    LoggerUtil.getLogger()
                        .info( "Email sent to: " + user.getEmail() );
                }
                catch ( Exception e )
                {
                    LoggerUtil.getLogger()
                        .info( "Exception occurred during email send to"
                                + user.getEmail() );
                }

                break;
            default:
                throw new RuntimeException(
                    "Unsupported notification type selected." );
        }
    }
}
