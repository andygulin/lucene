package com.search.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.util.Map;

public class Configuration {

    private static final String LUCENE_BASE_DIR = FileUtils.getTempDirectoryPath();

    private String idxPath;
    private Map<Integer, String> sourceTypes;

    public String getIdxPath() {
        return LUCENE_BASE_DIR + IOUtils.DIR_SEPARATOR + idxPath;
    }

    public void setIdxPath(String idxPath) {
        this.idxPath = idxPath;
    }

    public Map<Integer, String> getSourceTypes() {
        return sourceTypes;
    }

    public void setSourceTypes(Map<Integer, String> sourceTypes) {
        this.sourceTypes = sourceTypes;
    }
}