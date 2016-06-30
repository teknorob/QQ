package com.qq.model.google;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "users" })
public class Response
{

    @JsonProperty("users")
    private List<UserData> users = new ArrayList<UserData>();

    /**
     * @return The users
     */
    @JsonProperty("users")
    public List<UserData> getUsers()
    {
        return users;
    }

    /**
     * @param users
     *            The users
     */
    @JsonProperty("users")
    public void setUsers( List<UserData> users )
    {
        this.users = users;
    }

}
