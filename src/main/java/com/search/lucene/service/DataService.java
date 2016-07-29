package com.search.lucene.service;

import java.util.List;
import java.util.Map;

import com.search.lucene.bean.Data;

public interface DataService {

	List<Data> getList();

	List<Map<String, Object>> getListByIds(List<String> ids);
}