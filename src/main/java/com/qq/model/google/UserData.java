package com.qq.model.google;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "steamid", "communityvisibilitystate", "profilestate",
        "personaname", "lastlogoff", "profileurl", "avatar", "avatarmedium",
        "avatarfull", "personastate", "realname", "primaryclanid", "timecreated",
        "personastateflags", "loccountrycode", "locstatecode", "loccityid" })
public class UserData
{

    @JsonProperty("steamid")
    private String steamid;

    @JsonProperty("communityvisibilitystate")
    private Integer communityvisibilitystate;

    @JsonProperty("profilestate")
    private Integer profilestate;

    @JsonProperty("personaname")
    private String personaname;

    @JsonProperty("lastlogoff")
    private Integer lastlogoff;

    @JsonProperty("profileurl")
    private String profileurl;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("avatarmedium")
    private String avatarmedium;

    @JsonProperty("avatarfull")
    private String avatarfull;

    @JsonProperty("personastate")
    private Integer personastate;

    @JsonProperty("realname")
    private String realname;

    @JsonProperty("primaryclanid")
    private String primaryclanid;

    @JsonProperty("timecreated")
    private Integer timecreated;

    @JsonProperty("personastateflags")
    private Integer personastateflags;

    @JsonProperty("loccountrycode")
    private String loccountrycode;

    @JsonProperty("locstatecode")
    private String locstatecode;

    @JsonProperty("loccityid")
    private Integer loccityid;

    /**
     * @return The steamid
     */
    @JsonProperty("steamid")
    public String getSteamid()
    {
        return steamid;
    }

    /**
     * @param steamid
     *            The steamid
     */
    @JsonProperty("steamid")
    public void setSteamid( String steamid )
    {
        this.steamid = steamid;
    }

    /**
     * @return The communityvisibilitystate
     */
    @JsonProperty("communityvisibilitystate")
    public Integer getCommunityvisibilitystate()
    {
        return communityvisibilitystate;
    }

    /**
     * @param communityvisibilitystate
     *            The communityvisibilitystate
     */
    @JsonProperty("communityvisibilitystate")
    public void setCommunityvisibilitystate( Integer communityvisibilitystate )
    {
        this.communityvisibilitystate = communityvisibilitystate;
    }

    /**
     * @return The profilestate
     */
    @JsonProperty("profilestate")
    public Integer getProfilestate()
    {
        return profilestate;
    }

    /**
     * @param profilestate
     *            The profilestate
     */
    @JsonProperty("profilestate")
    public void setProfilestate( Integer profilestate )
    {
        this.profilestate = profilestate;
    }

    /**
     * @return The personaname
     */
    @JsonProperty("personaname")
    public String getPersonaname()
    {
        return personaname;
    }

    /**
     * @param personaname
     *            The personaname
     */
    @JsonProperty("personaname")
    public void setPersonaname( String personaname )
    {
        this.personaname = personaname;
    }

    /**
     * @return The lastlogoff
     */
    @JsonProperty("lastlogoff")
    public Integer getLastlogoff()
    {
        return lastlogoff;
    }

    /**
     * @param lastlogoff
     *            The lastlogoff
     */
    @JsonProperty("lastlogoff")
    public void setLastlogoff( Integer lastlogoff )
    {
        this.lastlogoff = lastlogoff;
    }

    /**
     * @return The profileurl
     */
    @JsonProperty("profileurl")
    public String getProfileurl()
    {
        return profileurl;
    }

    /**
     * @param profileurl
     *            The profileurl
     */
    @JsonProperty("profileurl")
    public void setProfileurl( String profileurl )
    {
        this.profileurl = profileurl;
    }

    /**
     * @return The avatar
     */
    @JsonProperty("avatar")
    public String getAvatar()
    {
        return avatar;
    }

    /**
     * @param avatar
     *            The avatar
     */
    @JsonProperty("avatar")
    public void setAvatar( String avatar )
    {
        this.avatar = avatar;
    }

    /**
     * @return The avatarmedium
     */
    @JsonProperty("avatarmedium")
    public String getAvatarmedium()
    {
        return avatarmedium;
    }

    /**
     * @param avatarmedium
     *            The avatarmedium
     */
    @JsonProperty("avatarmedium")
    public void setAvatarmedium( String avatarmedium )
    {
        this.avatarmedium = avatarmedium;
    }

    /**
     * @return The avatarfull
     */
    @JsonProperty("avatarfull")
    public String getAvatarfull()
    {
        return avatarfull;
    }

    /**
     * @param avatarfull
     *            The avatarfull
     */
    @JsonProperty("avatarfull")
    public void setAvatarfull( String avatarfull )
    {
        this.avatarfull = avatarfull;
    }

    /**
     * @return The personastate
     */
    @JsonProperty("personastate")
    public Integer getPersonastate()
    {
        return personastate;
    }

    /**
     * @param personastate
     *            The personastate
     */
    @JsonProperty("personastate")
    public void setPersonastate( Integer personastate )
    {
        this.personastate = personastate;
    }

    /**
     * @return The realname
     */
    @JsonProperty("realname")
    public String getRealname()
    {
        return realname;
    }

    /**
     * @param realname
     *            The realname
     */
    @JsonProperty("realname")
    public void setRealname( String realname )
    {
        this.realname = realname;
    }

    /**
     * @return The primaryclanid
     */
    @JsonProperty("primaryclanid")
    public String getPrimaryclanid()
    {
        return primaryclanid;
    }

    /**
     * @param primaryclanid
     *            The primaryclanid
     */
    @JsonProperty("primaryclanid")
    public void setPrimaryclanid( String primaryclanid )
    {
        this.primaryclanid = primaryclanid;
    }

    /**
     * @return The timecreated
     */
    @JsonProperty("timecreated")
    public Integer getTimecreated()
    {
        return timecreated;
    }

    /**
     * @param timecreated
     *            The timecreated
     */
    @JsonProperty("timecreated")
    public void setTimecreated( Integer timecreated )
    {
        this.timecreated = timecreated;
    }

    /**
     * @return The personastateflags
     */
    @JsonProperty("personastateflags")
    public Integer getPersonastateflags()
    {
        return personastateflags;
    }

    /**
     * @param personastateflags
     *            The personastateflags
     */
    @JsonProperty("personastateflags")
    public void setPersonastateflags( Integer personastateflags )
    {
        this.personastateflags = personastateflags;
    }

    /**
     * @return The loccountrycode
     */
    @JsonProperty("loccountrycode")
    public String getLoccountrycode()
    {
        return loccountrycode;
    }

    /**
     * @param loccountrycode
     *            The loccountrycode
     */
    @JsonProperty("loccountrycode")
    public void setLoccountrycode( String loccountrycode )
    {
        this.loccountrycode = loccountrycode;
    }

    /**
     * @return The locstatecode
     */
    @JsonProperty("locstatecode")
    public String getLocstatecode()
    {
        return locstatecode;
    }

    /**
     * @param locstatecode
     *            The locstatecode
     */
    @JsonProperty("locstatecode")
    public void setLocstatecode( String locstatecode )
    {
        this.locstatecode = locstatecode;
    }

    /**
     * @return The loccityid
     */
    @JsonProperty("loccityid")
    public Integer getLoccityid()
    {
        return loccityid;
    }

    /**
     * @param loccityid
     *            The loccityid
     */
    @JsonProperty("loccityid")
    public void setLoccityid( Integer loccityid )
    {
        this.loccityid = loccityid;
    }

}
