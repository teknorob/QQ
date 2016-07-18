package com.qq.model;

import com.qq.core.model.QQModel;

public class TwilioConfig implements QQModel
{
    
    private String accountSid;
    private String authToken;
    private String fromNumber;
    
    public TwilioConfig()
    {

    }

    public String getAccountSid()
    {
        return accountSid;
    }

    public void setAccountSid( String accountSid )
    {
        this.accountSid = accountSid;
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken( String authToken )
    {
        this.authToken = authToken;
    }

    public String getFromNumber()
    {
        return fromNumber;
    }

    public void setFromNumber( String fromNumber )
    {
        this.fromNumber = fromNumber;
    }
    
}
