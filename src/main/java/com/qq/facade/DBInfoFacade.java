package com.qq.facade;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.persist.facade.ModelFacade;
import com.qq.model.DBInfo;

public class DBInfoFacade extends ModelFacade
{

    private Dao<DBInfo, String> myDBInfoDao;

    public DBInfoFacade( ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
        myDBInfoDao = DaoManager.createDao( getConnectionSource(), DBInfo.class );
    }

    public List<DBInfo> getAllDBInfo() throws SQLException
    {
        return myDBInfoDao.queryForAll();
    }
}
