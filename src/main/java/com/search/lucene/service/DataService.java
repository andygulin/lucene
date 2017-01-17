package com.search.lucene.service;

import com.search.lucene.bean.Data;

import java.util.List;
import java.util.Map;

public interface DataService {

    List<Data> getList();

    List<Map<String, Object>> getListByIds(List<String> ids);
}