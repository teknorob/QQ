package com.qq.facade;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.constants.RolesConstants;
import com.qq.core.persist.facade.ModelFacade;
import com.qq.model.Administrator;
import com.qq.model.Role;
import com.qq.model.User;

public class UserFacade extends ModelFacade
{

    private Dao<User, String> myUserDao;

    public UserFacade( ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
        myUserDao = DaoManager.createDao( getConnectionSource(), User.class );
    }

    public List<User> getAllUsers() throws SQLException
    {
        return myUserDao.queryForAll();
    }

    private void setAdministrativeRoleOnExpectedGoogleId( User user ) throws SQLException
    {
        Dao<Administrator, String> myAdministratorDao = DaoManager
            .createDao( getConnectionSource(), Administrator.class );
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put( "id_google", user.getGoogleId() );

        List<Administrator> administrators = myAdministratorDao
            .queryForFieldValues( fieldValues );
        if ( administrators.size() == 1 )
        {
            user.setRoleId( new RoleFacade( getConnectionSource() )
                .getRoleFromCode( RolesConstants.ADMIN ).getRoleId() );

            myUserDao.update( user );
        }

    }

    public User createNewUser( String userName, String googleId,
                               String avatarURL ) throws SQLException
    {
        RoleFacade roleFacade = new RoleFacade( getConnectionSource() );
        Role role = roleFacade.getRoleFromCode( RolesConstants.USER );

        User user = new User();
        user.setUserName( userName );
        user.setGoogleId( googleId );
        user.setRoleId( role.getRoleId() );
        myUserDao.create( user );

        setAdministrativeRoleOnExpectedGoogleId( user );
        return user;
    }

    public User getUserById( String userId ) throws SQLException
    {
        return myUserDao.queryForId( userId );
    }

    public User getUserByGoogleId( String googleId ) throws SQLException
    {
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put( "id_google", googleId );
        List<User> users = myUserDao.queryForFieldValues( fieldValues );
        if ( users.size() != 1 )
        {
            return null;
        }
        return users.get( 0 );
    }

    public boolean isUserAdmin( User user ) throws SQLException
    {
        String[] adminCodes = { RolesConstants.ADMIN };
        for ( String adminCode : adminCodes )
        {
            if ( new RoleFacade( getConnectionSource() ).getRoleFromCode( adminCode )
                .getRoleId() == user.getRoleId() )
            {
                return true;
            }
        }
        return false;
    }

    public int deleteUserById( String userId ) throws SQLException
    {
        return myUserDao.deleteById( userId );
    }

    /**
     * Obtains the roleId from the user object, and queries for the original
     * user to ensure only the role can be updated.
     * 
     * @param user
     * @throws SQLException
     */
    public User updateUserRole( User user ) throws SQLException
    {
        int roleId = user.getRoleId();
        user = getUserById( Integer.toString( user.getUserId() ) );
        user.setRoleId( roleId );
        user.setRoleId( ( new RoleFacade( getConnectionSource() )
            .getRoleFromCode( RolesConstants.USER ) ).getRoleId() );
        myUserDao.update( user );
        return user;
    }

    /**
     * Updates the user entirely
     * 
     * @param user
     * @throws SQLException
     */
    public User updateUser( User user ) throws SQLException
    {
        myUserDao.update( user );
        return user;
    }

}
