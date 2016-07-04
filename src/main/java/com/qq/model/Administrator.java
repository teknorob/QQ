package com.qq.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "administrator")
public class Administrator
{

    @DatabaseField(columnName = "id_administrator", generatedId = true)
    private int administratorId;

    @DatabaseField(columnName = "id_google")
    private String googleId;

    public int getAdministratorId()
    {
        return administratorId;
    }

    public void setAdministratorId( int administratorId )
    {
        this.administratorId = administratorId;
    }

    public String getGoogleId()
    {
        return googleId;
    }

    public void setGoogleId( String googleId )
    {
        this.googleId = googleId;
    }

}
