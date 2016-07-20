package com.qq.model;

import com.qq.core.model.QQModel;

public class QQConfig implements QQModel
{
    
    private String googleClientId;
    private String httpProxyHost;
    private String httpProxyPort;
    private String serviceAccountPassword;
    
    public QQConfig()
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

}
