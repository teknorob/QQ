package com.qq.model.google;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "response" })
public class GoogleUser
{

    @JsonProperty("response")
    private Response response;

    /**
     * @return The response
     */
    @JsonProperty("response")
    public Response getResponse()
    {
        return response;
    }

    /**
     * @param response
     *            The response
     */
    @JsonProperty("response")
    public void setResponse( Response response )
    {
        this.response = response;
    }

}
