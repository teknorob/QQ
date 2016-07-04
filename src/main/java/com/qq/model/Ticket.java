package com.qq.model;

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

}
