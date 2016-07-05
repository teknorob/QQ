package com.qq.model;

public class Sms
{
    private String toNumber;

    private String body;

    public Sms()
    {

    }

    public String getToNumber()
    {
        return toNumber;
    }

    public void setToNumber( String toNumber )
    {
        this.toNumber = toNumber;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody( String body )
    {
        this.body = body;
    }
}
