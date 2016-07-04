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
    @DatabaseField(columnName = "ur_avatar")
    private String avatarURL;
    
    @Column
    @DatabaseField(columnName = "id_google")
    private String googleId;

    @Column
    @DatabaseField(columnName = "id_role")
    private int roleId;
    
    @Column
    @DatabaseField(columnName = "tx_phone_number")
    private int phoneNumber;

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
    
    public String getAvatarURL()
    {
        return avatarURL;
    }

    public void setAvatarURL( String avatarURL )
    {
        this.avatarURL = avatarURL;
    }

    public String getGoogleId()
    {
        return googleId;
    }

    public void setGoogleId( String googleId )
    {
        this.googleId = googleId;
    }

    public int getRoleId()
    {
        return roleId;
    }

    public void setRoleId( int roleId )
    {
        this.roleId = roleId;
    }

    public int getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber( int phoneNumber )
    {
        this.phoneNumber = phoneNumber;
    }

}
