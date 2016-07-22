package com.qq;

import static spark.Spark.webSocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.slf4j.Logger;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.qq.core.route.management.RouteManager;
import com.qq.persist.init.DatabaseInitialiser;
import com.qq.queue.QueueManager;
import com.qq.route.service.InteractionRoute;
import com.qq.util.LoggerUtil;

public class Main
{
    private static Logger logger = LoggerUtil.getLogger();

    public static void main( final String[] args ) throws InstantiationException,
                                                   IllegalAccessException
    {
        try
        {
            final URI uri = Main.class.getClassLoader()
                .getResource( "com/qq/Main.class" ).toURI();
            if ( uri.getScheme().equals( "jar" ) )
            {
                final String uriString = uri.toString();
                final String jarUriString = uriString.split( "!" )[0];
                final URI jarUri = new URI( jarUriString );
                final Map<String, String> env = new HashMap<>();
                env.put( "create", "true" );
                FileSystems.newFileSystem( jarUri, env );
                logger.info( "Loading static resources from jar( " + jarUri + " )" );
            }
            else
            {
                logger.info( "Loading static resources from filesystem" );
            }
        }
        catch ( final URISyntaxException | IOException e )
        {
            throw new RuntimeException( e );
        }

        final String sqlUsername = "qq";
        final String filePassword = "qq";
        final String sqlPassword = "qq";

        final String connectionPassword = filePassword + " " + sqlPassword;
        final JdbcConnectionPool connectionPool = JdbcConnectionPool.create(
            "jdbc:h2:./qq;CIPHER=AES;mode=mysql", sqlUsername, connectionPassword );

        // Initialize database if required
        try (final Connection connection = connectionPool.getConnection())
        {
            if ( DatabaseInitialiser.needsInitialising( connection ) )
            {
                logger.info( "DB needs initialising" );
                DatabaseInitialiser.initialise( connection );
                logger.info( "DB initialised" );
            }
            else
            {
                logger.info( "Pre-existing DB found" );
            }
        }
        catch ( final SQLException e )
        {
            throw new RuntimeException( e );
        }

        ConnectionSource connectionSource;

        try
        {
            connectionSource = new JdbcConnectionSource(
                "jdbc:h2:./qq;CIPHER=AES;mode=mysql", sqlUsername,
                connectionPassword );

        }
        catch ( final SQLException e )
        {
            throw new RuntimeException( e );
        }

        try
        {
            final Server server = Server.createWebServer( "-webPort", "4568" );
            logger.info( "Starting DB web server..." );
            server.start();
            logger.info( "DB web server started on port " + server.getPort() );
        }
        catch ( final SQLException e )
        {
            throw new RuntimeException( e );
        }

        try
        {
            final Server server = Server.createTcpServer( "-tcpPort", "4569" );
            logger.info( "Starting DB tcp server..." );
            server.start();
            logger.info( "DB tcp server started on port " + server.getPort() );
        }
        catch ( final SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        QueueManager.init( connectionSource );
        RouteManager.insertWebsockets( connectionSource );
        RouteManager.insertRoutes( connectionSource );

    }
}