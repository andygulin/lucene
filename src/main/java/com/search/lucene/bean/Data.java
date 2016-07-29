package com.search.lucene.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Data implements Serializable {

	private static final long serialVersionUID = 7816835466005401466L;

	private Integer id;
	private String title;
	private String content;
	private Integer sourceType;
	private Date createdAt;

	public Data() {
		super();
	}

	public Data(Integer id, String title, String content, Integer sourceType, Date createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.sourceType = sourceType;
		this.createdAt = createdAt;
	}

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}