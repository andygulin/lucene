package com.search.lucene.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SearchResult implements Serializable {

	private static final long serialVersionUID = 6222492301521060987L;

	private List<String> ids;
	private int count;

	public SearchResult() {
		super();
	}

	public SearchResult(List<String> ids, int count) {
		super();
		this.ids = ids;
		this.count = count;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}