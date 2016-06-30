package com.qq.route.rest;

import static spark.Spark.get;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.RegistrableRoute;
import com.qq.facade.RoleFacade;
import com.qq.model.Role;

public class RolesRestRoute extends RegistrableRoute
{
    RoleFacade rolesFacade;

    public RolesRestRoute( final ConnectionSource connectionSource ) throws SQLException
    {
        super( connectionSource );
        rolesFacade = new RoleFacade( getConnectionSource() );
    }

    @Override
    public void register()
    {
        get( "/roles", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            try
            {
                final List<Role> role = rolesFacade.getAllRoles();
                page.put( "roles", role );
            }
            catch ( Exception e )
            {
                throw new RuntimeException( e );
            }
            return page;
        }, getJsonTransformer() );
        get( "/roles/:rolesId", "application/json", ( request, response ) -> {
            Map<String, Object> page = getNewPageModel( request );
            try
            {
                final Role role = rolesFacade
                    .getRoleById( request.params( ":rolesId" ) );
                page.put( "roles", role );
            }
            catch ( Exception e )
            {
                throw new RuntimeException( e );
            }
            return page;
        }, getJsonTransformer() );
    }
}
