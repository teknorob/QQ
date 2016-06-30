package com.qq.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "god")
public class God
{

    @DatabaseField(columnName = "id_god", generatedId = true)
    private int godId;

    @DatabaseField(columnName = "id_steam")
    private String steamId;

    public int getGodId()
    {
        return godId;
    }

    public void setGodId( int godId )
    {
        this.godId = godId;
    }

    public String getSteamId()
    {
        return steamId;
    }

    public void setSteamId( String steamId )
    {
        this.steamId = steamId;
    }

}
