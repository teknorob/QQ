package com.qq.route.rest;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;
import com.qq.facade.QueueFacade;
import com.qq.facade.TicketFacade;
import com.qq.model.Queue;
import com.qq.model.Ticket;
import com.qq.model.User;
import com.qq.util.SessionUtils;

public class TicketsRestRoute extends RegistrableRoute
{
    TicketFacade ticketsFacade;

    public TicketsRestRoute( final ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
        ticketsFacade = new TicketFacade( getConnectionSource() );
    }

    @Override
    public void register()
    {
        get( "/tickets", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            try
            {
                String queueId = request.queryParams( "queueId" );

                final List<Ticket> ticket = ticketsFacade
                    .getTicketsForQueue( queueId );
                page.put( "tickets", ticket );
            }
            catch ( Exception e )
            {
                throw new RuntimeException( e );
            }
            return page;
        }, getJsonTransformer() );
        get( "/tickets/:ticketId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            try
            {
                final Ticket ticket = ticketsFacade
                    .getTicketById( request.params( ":ticketId" ) );
                page.put( "tickets", ticket );
            }
            catch ( Exception e )
            {
                throw new RuntimeException( e );
            }
            return page;
        }, getJsonTransformer() );
        post( "/tickets", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            Queue queue = new ObjectMapper()
                .setDateFormat(
                    new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" ) )
                .readValue( request.body(), Queue.class );

            if ( SessionUtils.isLoggedIn( request ) )
            {
                QueueFacade queueFacade = new QueueFacade( getConnectionSource() );
                queue = queueFacade
                    .getQueueById( Integer.toString( queue.getQueueId() ) );
                User user = SessionUtils.getCurrentUser( request );

                TicketFacade ticketFacade = new TicketFacade(
                    getConnectionSource() );
                page.put( "tickets", ticketFacade.createTicket( queue, user ) );
            }
            return page;
        }, getJsonTransformer() );
        delete( "/tickets/:ticketId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            TicketFacade ticketFacade = new TicketFacade( getConnectionSource() );
            Ticket ticket = ticketFacade
                .getTicketById( request.queryParams( ":ticketId" ) );
            if ( SessionUtils.isAdminSession( request, getConnectionSource() )
                    || ( SessionUtils.getCurrentUser( request ).getUserId() + "" )
                        .equals( ticket.getUserId() ) )
            {
                ticketFacade.deleteTicketById( request.params( ":ticketId" ) );
            }
            return page;
        }, getJsonTransformer() );
    }
}
