package com.kgb.cooapp.service.endpoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserEndPoint {
    @SerializedName("links")
    @Expose
    private List<Link> links = null;
    @SerializedName("content")
    @Expose
    private List<UserContent> content = null;
    @SerializedName("page")
    @Expose
    private Page page;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<UserContent> getContent() {
        return content;
    }

    public void setContent(List<UserContent> content) {
        this.content = content;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
