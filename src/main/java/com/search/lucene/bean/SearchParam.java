package com.search.lucene.bean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class SearchParam {

    private Integer id;
    private String title = "";
    private String content = "";
    private Integer sourceType;
    private String startDate = "";
    private String endDate = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isEmpty() {
        if (this.id == null && this.title.length() == 0 && this.content.length() == 0 && this.startDate.length() == 0
                && this.endDate.length() == 0 && this.sourceType == null) {
            return true;
        }
        return false;
    }

    public String buildUrl() {
        List<String> url = new ArrayList<String>();
        if (this.id != null) {
            url.add("id=" + this.id);
        }
        if (this.startDate != null) {
            url.add("startDate=" + startDate);
        }
        if (this.endDate != null) {
            url.add("endDate=" + endDate);
        }
        if (this.title != null) {
            url.add("title=" + this.title);
        }
        if (this.content != null) {
            url.add("content=" + this.content);
        }
        if (this.sourceType != null) {
            url.add("sourceType=" + this.sourceType);
        }
        return "?" + StringUtils.join(url, "&");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}