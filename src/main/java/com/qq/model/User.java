package com.qq.model;

import javax.persistence.Column;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User
{

    @Column
    @DatabaseField(columnName = "id_user", generatedId = true)
    private int userId;

    @Column
    @DatabaseField(columnName = "nm_user")
    private String userName;

    @Column
    @DatabaseField(columnName = "id_google")
    private String steamId;

    @Column
    @DatabaseField(columnName = "id_role")
    private int roleId;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId( int userId )
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getSteamId()
    {
        return steamId;
    }

    public void setSteamId( String steamId )
    {
        this.steamId = steamId;
    }

    public int getRoleId()
    {
        return roleId;
    }

    public void setRoleId( int roleId )
    {
        this.roleId = roleId;
    }

}
