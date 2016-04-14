package com.search.lucene.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Data implements Serializable {

	private static final long serialVersionUID = 7816835466005401466L;

	private Integer id;
	private String title;
	private String formatContent;
	private Integer sourceType;
	private Date releaseDate;

	public Data() {
		super();
	}

	public Data(Integer id, String title, String formatContent,
			Integer sourceType, Date releaseDate) {
		super();
		this.id = id;
		this.title = title;
		this.formatContent = formatContent;
		this.sourceType = sourceType;
		this.releaseDate = releaseDate;
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

	public String getFormatContent() {
		return formatContent;
	}

	public void setFormatContent(String formatContent) {
		this.formatContent = formatContent;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
