package com.qq.facade;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.persist.facade.ModelFacade;
import com.qq.model.Queue;
import com.qq.model.Ticket;
import com.qq.model.User;

public class TicketFacade extends ModelFacade
{

    private Dao<Ticket, String> myTicketDao;

    public TicketFacade( ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
        myTicketDao = DaoManager.createDao( getConnectionSource(), Ticket.class );
    }

    public List<Ticket> getAllTickets() throws SQLException
    {
        return myTicketDao.queryForAll();
    }

    public List<Ticket> getTicketsForQueue( String queueId ) throws SQLException
    {
        Map<String, Object> filterValues = new HashMap<>();
        filterValues.put( "id_queue", queueId );
        return myTicketDao.queryForFieldValues( filterValues );
    }

    public Ticket getTicketById( String id ) throws SQLException
    {
        return myTicketDao.queryForId( id );
    }

    public Ticket createTicket( Queue queue, User user ) throws SQLException
    {
        Ticket ticket = new Ticket();
        ticket.setQueueId( queue.getQueueId() + "" );
        ticket.setUserId( user.getUserId() + "" );
        ticket.setLastUpdated( new Timestamp( System.currentTimeMillis() ) );

        myTicketDao.create( ticket );
        return ticket;
    }

    public void deleteAllTicketsForQueue( String id ) throws SQLException
    {
        myTicketDao.delete( getTicketsForQueue( id ) );
    }

    public void deleteTicketById( String id ) throws SQLException
    {
        myTicketDao.deleteById( id );
    }

    public List<Ticket> getOrderedTicketsForQueue( String queueId ) throws SQLException
    {
        List<Ticket> tickets = getTicketsForQueue( queueId );

        tickets.sort( ( ticket0, ticket1 ) -> {
            return ticket0.getLastUpdated().compareTo( ticket1.getLastUpdated() );
        } );

        return tickets;
    }

    public Ticket getNextTicketForQueue( String queueId ) throws SQLException
    {
        List<Ticket> tickets = getOrderedTicketsForQueue( queueId );
        return tickets.isEmpty() ? null : tickets.get( 0 );
    }

    public void updateTicket( Ticket ticket ) throws SQLException
    {
        myTicketDao.update( ticket );
    }

}
