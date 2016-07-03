package com.qq.facade;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.constants.APIConstants;
import com.qq.constants.RolesConstants;
import com.qq.core.persist.facade.ModelFacade;
import com.qq.model.God;
import com.qq.model.Role;
import com.qq.model.User;
import com.qq.model.google.GoogleUser;
import com.qq.model.google.UserData;

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

    private void setGodRole( User user ) throws SQLException
    {
        Dao<God, String> myGodDao = DaoManager.createDao( getConnectionSource(),
            God.class );
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put( "id_google", user.getGoogleId() );

        List<God> gods = myGodDao.queryForFieldValues( fieldValues );
        if ( gods.size() == 1 )
        {
            user.setRoleId( new RoleFacade( getConnectionSource() )
                .getRoleFromCode( RolesConstants.GOD ).getRoleId() );

            myUserDao.update( user );
        }

    }

    public int createNewUser( String userName, String googleId,
                                String avatarURL ) throws SQLException
    {
        RoleFacade roleFacade = new RoleFacade( getConnectionSource() );
        Role role = roleFacade.getRoleFromCode( RolesConstants.USER );

        User user = new User();
        user.setUserName( userName );
        user.setGoogleId( googleId );
        user.setRoleId( role.getRoleId() );
        myUserDao.create( user );

        setGodRole( user );
        return user.getUserId();
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

    public int registerNewUserByGoogleId( String googleId ) throws IOException,
                                                            SQLException
    {
        ObjectMapper mapper = new ObjectMapper();

        try (CloseableHttpClient httpclient = HttpClients.createDefault();)
        {

            HttpHost target = new HttpHost( "api.googlepowered.com", 80, "http" );
            HttpGet httprequest = new HttpGet(
                "/IGoogleUser/GetUserSummaries/v0002/?key=" + APIConstants.API_KEY
                        + "&googleids=" + googleId );

            if ( System.getProperty( "http.proxyHost" ) != null
                    && System.getProperty( "http.proxyPort" ) != null )
            {
                HttpHost proxy = new HttpHost(
                    System.getProperty( "http.proxyHost" ),
                    Integer.parseInt( System.getProperty( "http.proxyPort" ) ),
                    "http" );

                RequestConfig config = RequestConfig.custom().setProxy( proxy )
                    .build();
                httprequest.setConfig( config );

                System.out
                    .println( "Executing request " + httprequest.getRequestLine()
                            + " to " + target + " via " + proxy );
            }
            else
            {
                System.out
                    .println( "Executing request " + httprequest.getRequestLine()
                            + " to " + target + " with no proxy" );
            }

            CloseableHttpResponse httpresponse = httpclient.execute( target,
                httprequest );
            System.out.println( "----------------------------------------" );
            System.out.println( httpresponse.getStatusLine() );
            String json = EntityUtils.toString( httpresponse.getEntity() );
            System.out.println( json );

            GoogleUser googleUser = mapper.readValue( json, GoogleUser.class );
            UserData userdata = googleUser.getResponse().getUsers().get( 0 );

            System.out.println( "Persona name of logged in user: " );
            System.out.println( userdata.getPersonaname() );

            return createNewUser( userdata.getPersonaname(),
                userdata.getGoogleid(), userdata.getAvatar() );
        }
    }

    public boolean isUserAdmin( User user ) throws SQLException
    {
        String[] adminCodes = { RolesConstants.ADMIN, RolesConstants.GOD };
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

    public boolean isUserGod( User user ) throws SQLException
    {
        if ( new RoleFacade( getConnectionSource() )
            .getRoleFromCode( RolesConstants.GOD )
            .getRoleId() == user.getRoleId() )
        {
            return true;
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
