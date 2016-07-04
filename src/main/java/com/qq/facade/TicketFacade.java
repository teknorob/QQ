package com.qq.facade;

import java.sql.SQLException;
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
        
        myTicketDao.create( ticket );
        return ticket;
    }
}
