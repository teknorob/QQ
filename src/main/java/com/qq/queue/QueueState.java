package com.qq.queue;

import com.qq.model.Ticket;

public class QueueState
{
    Ticket currentTicket;

    String state;

    boolean update;

    public QueueState( Ticket currentTicket, String state, boolean update )
    {
        this.currentTicket = currentTicket;
        this.state = state;
        this.update = update;
    }

    public Ticket getCurrentTicket()
    {
        return currentTicket;
    }

    public void setCurrentTicket( Ticket currentTicket )
    {
        this.currentTicket = currentTicket;
    }

    public String getState()
    {
        return state;
    }

    public void setState( String state )
    {
        this.state = state;
    }

    public boolean isUpdate()
    {
        return update;
    }

    public void setUpdate( boolean update )
    {
        this.update = update;
    }
}
