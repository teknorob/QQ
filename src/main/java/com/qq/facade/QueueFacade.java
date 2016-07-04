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

    public Queue getQueueFromCode( String code ) throws SQLException
    {
        Map<String, Object> filterValues = new HashMap<>();
        filterValues.put( "cd_queue", code );
        List<Queue> queues = myQueueDao.queryForFieldValues( filterValues );
        if ( queues.size() != 1 )
        {
            return null;
        }
        return queues.get( 0 );
    }

    public Queue getQueueById( String id ) throws SQLException
    {
        return myQueueDao.queryForId( id );
    }
    
}
