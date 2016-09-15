package com.qq.model;

import java.sql.Timestamp;
import java.util.Comparator;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ticket")
public class Ticket
{

    @DatabaseField(columnName = "id_ticket", generatedId = true)
    private int ticketId;

    @DatabaseField(columnName = "id_queue")
    private String queueId;

    @DatabaseField(columnName = "id_user")
    private String userId;

    @DatabaseField(columnName = "dt_last_updated")
    private Timestamp lastUpdated;
    
    @DatabaseField(columnName = "fl_offender")
    private boolean offender = false;

    public boolean isOffender()
    {
        return offender;
    }

    public void setOffender( boolean offender )
    {
        this.offender = offender;
    }

    public int getTicketId()
    {
        return ticketId;
    }

    public void setTicketId( int ticketId )
    {
        this.ticketId = ticketId;
    }

    public String getQueueId()
    {
        return queueId;
    }

    public void setQueueId( String queueId )
    {
        this.queueId = queueId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    public Timestamp getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated( Timestamp lastUpdated )
    {
        this.lastUpdated = lastUpdated;
    }

}
