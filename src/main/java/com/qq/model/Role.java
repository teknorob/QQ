package com.qq.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "role")
public class Role
{

    @DatabaseField(columnName = "id_role", generatedId = true)
    private int roleId;

    @DatabaseField(columnName = "cd_role")
    private String roleCode;

    public int getRoleId()
    {
        return roleId;
    }

    public void setRoleId( int roleId )
    {
        this.roleId = roleId;
    }

    public String getRoleCode()
    {
        return roleCode;
    }

    public void setRoleCode( String roleCode )
    {
        this.roleCode = roleCode;
    }

}
