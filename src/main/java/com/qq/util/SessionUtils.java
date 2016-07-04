package com.qq.util;

import java.sql.SQLException;

import spark.Request;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.facade.UserFacade;
import com.qq.model.User;

public class SessionUtils
{

    public static boolean isAdminSession( Request request,
                                          ConnectionSource connectionSource ) throws SQLException
    {
        User user = (User)request.session().attribute( "user" );
        if ( user != null )
        {
            return ( new UserFacade( connectionSource ).isUserAdmin( user ) );
        }
        return false;
    }
    
    public static boolean isLoggedIn( Request request) throws SQLException
    {
        return (User)request.session().attribute( "user" ) != null;
    }

    public static User getCurrentUser( Request request )
    {
        return request.session().attribute( "user" );
    }
}
