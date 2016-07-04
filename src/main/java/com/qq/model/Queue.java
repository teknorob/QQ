package com.qq.model;

import java.sql.Time;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "queue")
public class Queue
{

    @DatabaseField(columnName = "id_queue", generatedId = true)
    private Integer queueId;

    @DatabaseField(columnName = "nm_queue")
    private String queueName;
    
    @DatabaseField(columnName = "no_ticket_expiry_duration")
    private Integer ticketExpiryDuration;
    
    @DatabaseField(columnName = "ti_open_time")
    private Date openTime;   

    @DatabaseField(columnName = "ti_close_time")
    private Date closeTime;
    
    public int getQueueId()
    {
        return queueId;
    }

    public void setQueueId( int queueId )
    {
        this.queueId = queueId;
    }

    public String getQueueName()
    {
        return queueName;
    }

    public void setQueueName( String queueName )
    {
        this.queueName = queueName;
    }

    public Integer getTicketExpiryDuration()
    {
        return ticketExpiryDuration;
    }

    public void setTicketExpiryDuration( Integer ticketExpiryDuration )
    {
        this.ticketExpiryDuration = ticketExpiryDuration;
    }

    public Date getOpenTime()
    {
        return openTime;
    }

    public void setOpenTime( Date openTime )
    {
        this.openTime = openTime;
    }

    public Date getCloseTime()
    {
        return closeTime;
    }

    public void setCloseTime( Date closeTime )
    {
        this.closeTime = closeTime;
    }

}
