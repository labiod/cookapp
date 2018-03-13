package com.kgb.cooapp.service.endpoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserContent {

    /**
     * Parameter that doesn't used
     "thermomixes" : [ {
     "links" : [ {
     "rel" : "self",
     "href" : "https://cookidoo.pl/vorwerkApiV2/apiv2/thermomix/8796093193690"
     } ],
     "name" : "TM5"
     } ],
     "collectionFavoriteCount" : 1,
     "collectionCount" : 2,
     "advisorNumber" : "",
     "recipeFavoriteCount" : 0,
     "firstLogin" : false,
     "phone2" : "+48",
     "phone1" : "+48884781872",
     "newsletterAccepted" : false,
     "alreadyChangedInitialMarket" : false,
     "vorwerkRole" : "Customer",
     "recipeCount" : 219,
     "initialMarket" : "pl",
     */

    @SerializedName("links")
    @Expose
    private List<Link> links = null;

    @SerializedName("dataSaved")
    @Expose
    private boolean dataSaved;
    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("dateModified")
    @Expose
    private String dateModified;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("email")
    @Expose
    private String email;

    public void setDataSaved(boolean dataSaved) {
        this.dataSaved = dataSaved;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public boolean isDataSaved() {
        return dataSaved;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getBirthday() {
        return birthday;
    }
}
