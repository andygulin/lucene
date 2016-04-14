package com.search.lucene;

import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Configuration {

	private static final String LUCENE_BASE_DIR = FileUtils.getTempDirectoryPath();

	private String idxPath;
	private String cachePath;
	private String startDate;
	private String endDate;
	private Map<Integer, String> sourceTypes;

	public String getIdxPath() {
		return LUCENE_BASE_DIR + IOUtils.DIR_SEPARATOR + idxPath;
	}

	public void setIdxPath(String idxPath) {
		this.idxPath = idxPath;
	}

	public String getCachePath() {
		return LUCENE_BASE_DIR + IOUtils.DIR_SEPARATOR + cachePath;
	}

	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
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

	public Map<Integer, String> getSourceTypes() {
		return sourceTypes;
	}

	public void setSourceTypes(Map<Integer, String> sourceTypes) {
		this.sourceTypes = sourceTypes;
	}

}
