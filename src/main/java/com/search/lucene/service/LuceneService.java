package com.search.lucene.service;

import com.search.lucene.bean.SearchParam;
import com.search.lucene.bean.SearchResult;

public interface LuceneService {

    void index();

    SearchResult search(SearchParam param, int pageIndex, int pageSize);
}