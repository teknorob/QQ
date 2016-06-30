package com.qq.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "db_info")
public class DBInfo
{

    @DatabaseField(columnName = "cd_parameter")
    private int parameterCode;

    @DatabaseField(columnName = "tx_parameter")
    private int parameter;

    public int getParameterCode()
    {
        return parameterCode;
    }

    public void setParameterCode( int parameterCode )
    {
        this.parameterCode = parameterCode;
    }

    public int getParameter()
    {
        return parameter;
    }

    public void setParameterText( int parameter )
    {
        this.parameter = parameter;
    }

}
