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

public class QueueFacade extends ModelFacade
{

    private Dao<Queue, String> myQueueDao;

    public QueueFacade( ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
        myQueueDao = DaoManager.createDao( getConnectionSource(), Queue.class );
    }

    public List<Queue> getAllQueues() throws SQLException
    {
        return myQueueDao.queryForAll();
    }

    public Queue getQueueById( String id ) throws SQLException
    {
        return myQueueDao.queryForId( id );
    }

    public Queue createQueue( Queue queue ) throws SQLException
    {
        return myQueueDao.create( queue ) == 1? queue : null;
    }

    public void deleteQueueById( String id ) throws SQLException
    {
        TicketFacade ticketFacade = new TicketFacade(getConnectionSource());
        ticketFacade.deleteAllTicketsForQueue(id);
        myQueueDao.deleteById( id );
        
    }
    
}
