package com.qq.facade;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.persist.facade.ModelFacade;
import com.qq.model.Role;

public class RoleFacade extends ModelFacade
{

    private Dao<Role, String> myRoleDao;

    public RoleFacade( ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
        myRoleDao = DaoManager.createDao( getConnectionSource(), Role.class );
    }

    public List<Role> getAllRoles() throws SQLException
    {
        return myRoleDao.queryForAll();
    }

    public Role getRoleFromCode( String code ) throws SQLException
    {
        Map<String, Object> filterValues = new HashMap<>();
        filterValues.put( "cd_role", code );
        List<Role> roles = myRoleDao.queryForFieldValues( filterValues );
        if ( roles.size() != 1 )
        {
            return null;
        }
        return roles.get( 0 );
    }

    public Role getRoleById( String id ) throws SQLException
    {
        return myRoleDao.queryForId( id );
    }
}
