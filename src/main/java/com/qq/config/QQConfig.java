package com.qq.config;

import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.qq.core.model.QQModel;

public class QQConfig implements QQModel
{
    private String googleClientId;

    private String httpProxyHost;

    private String httpProxyPort;

    private String serviceAccountPassword;
    
    private boolean debugMode;
    
    private String fromAddress;
    
    private String smtpHost;
    
    private int smtpPort;

    private QQConfig()
    {

    }

    public String getGoogleClientId()
    {
        return googleClientId;
    }

    public void setGoogleClientId( String googleClientId )
    {
        this.googleClientId = googleClientId;
    }

    public String getHttpProxyHost()
    {
        return httpProxyHost;
    }

    public void setHttpProxyHost( String httpProxyHost )
    {
        this.httpProxyHost = httpProxyHost;
    }

    public String getHttpProxyPort()
    {
        return httpProxyPort;
    }

    public void setHttpProxyPort( String httpProxyPort )
    {
        this.httpProxyPort = httpProxyPort;
    }

    public String getServiceAccountPassword()
    {
        return serviceAccountPassword;
    }

    public void setServiceAccountPassword( String serviceAccountPassword )
    {
        this.serviceAccountPassword = serviceAccountPassword;
    }

    public boolean isDebugMode()
    {
        return debugMode;
    }

    public void setDebugMode( boolean debugMode )
    {
        this.debugMode = debugMode;
    }

    public String getFromAddress()
    {
        return fromAddress;
    }

    public void setFromAddress( String fromAddress )
    {
        this.fromAddress = fromAddress;
    }

    public String getSmtpHost()
    {
        return smtpHost;
    }

    public void setSmtpHost( String smtpHost )
    {
        this.smtpHost = smtpHost;
    }

    public int getSmtpPort()
    {
        return smtpPort;
    }

    public void setSmtpPort( int smtpPort )
    {
        this.smtpPort = smtpPort;
    }

    public static final QQConfig build()
    {
        return new Gson().fromJson(
            new InputStreamReader( QQConfig.class
                .getResourceAsStream( "/com/qq/config/qq_config.json" ) ),
            QQConfig.class );
    }

}
