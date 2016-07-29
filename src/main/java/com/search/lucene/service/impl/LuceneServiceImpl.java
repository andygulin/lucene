package com.search.lucene.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.lucene.Configuration;
import com.search.lucene.Constants;
import com.search.lucene.bean.Data;
import com.search.lucene.bean.SearchParam;
import com.search.lucene.bean.SearchResult;
import com.search.lucene.ex.QueryParserEx;
import com.search.lucene.helper.Fields;
import com.search.lucene.service.DataService;
import com.search.lucene.service.LuceneService;

@Service
public class LuceneServiceImpl implements LuceneService {

	private static final Logger logger = Logger.getLogger(LuceneServiceImpl.class);

	@Autowired
	private Configuration configuration;
	@Autowired
	private DataService dataSevice;

	@Override
	public void index() {
		Directory directory = null;
		IndexWriter indexWriter = null;
		File path = new File(configuration.getIdxPath());
		logger.info("index path : " + path);
		try {
			directory = FSDirectory.open(path);
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Constants.LUCENE_VERSION,
					new StandardAnalyzer(Constants.LUCENE_VERSION));
			indexWriterConfig.setOpenMode(OpenMode.CREATE);
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			List<Data> list = dataSevice.getList();
			if (CollectionUtils.isNotEmpty(list)) {
				Document document = null;
				for (Data data : list) {
					document = new Document();
					document.add(Fields.newInt("id", data.getId(), Store.YES, true));
					document.add(Fields.newString("title", data.getTitle(), Store.NO, Index.ANALYZED));
					document.add(Fields.newString("content", data.getContent(), Store.NO, Index.ANALYZED));
					document.add(Fields.newInt("sourceType", data.getSourceType(), Store.NO, true));
					document.add(Fields.newLong("createdAt", data.getCreatedAt(), Store.NO, true));
					indexWriter.addDocument(document);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexWriter.commit();
				indexWriter.close();
				directory.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public SearchResult search(SearchParam param, int pageIndex, int pageSize) {
		if (param.isEmpty()) {
			return new SearchResult(new LinkedList<String>(), 0);
		}
		SearchResult searchResult = new SearchResult();
		List<String> ids = new LinkedList<String>();
		Directory directory = null;
		IndexReader indexReader = null;
		IndexSearcher indexSearcher = null;
		QueryParser queryParser = null;
		try {
			directory = FSDirectory.open(new File(configuration.getIdxPath()));
			indexReader = IndexReader.open(directory);
			indexSearcher = new IndexSearcher(indexReader);

			Map<String, Class<?>> fields = new HashMap<String, Class<?>>();
			fields.put("id", Integer.class);
			fields.put("sourceType", Integer.class);
			fields.put("createdAt", Long.class);

			queryParser = new QueryParserEx(Constants.LUCENE_VERSION, "",
					new StandardAnalyzer(Constants.LUCENE_VERSION), fields);
			String parse = parse(param);
			logger.info(parse);
			if (StringUtils.isEmpty(parse)) {
				return new SearchResult(new LinkedList<String>(), 0);
			}

			Query query = queryParser.parse(parse);
			Sort sort = new Sort();
			SortField sortField = new SortField("createdAt", SortField.LONG, true);
			sort.setSort(sortField);
			TopDocs topDocs = indexSearcher.search(query, 100, sort);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			int begin = pageSize * (pageIndex - 1);
			int end = Math.min(begin + pageSize, scoreDocs.length);
			for (int i = begin; i < end; i++) {
				int docID = scoreDocs[i].doc;
				Document document = indexSearcher.doc(docID);
				ids.add(document.get("id"));
			}
			searchResult.setIds(ids);
			searchResult.setCount(scoreDocs.length);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearcher.close();
				indexReader.close();
				directory.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return searchResult;
	}

	private String parse(SearchParam param) {
		if (param != null) {
			List<String> parse = new ArrayList<String>();
			if (StringUtils.isNotEmpty(param.getTitle())) {
				parse.add("+title:" + param.getTitle());
			}
			if (StringUtils.isNotEmpty(param.getContent())) {
				parse.add("+content:" + param.getContent());
			}
			String sDate = param.getStartDate();
			String eDate = param.getEndDate();
			if (StringUtils.isNotEmpty(sDate) && StringUtils.isNotEmpty(eDate)) {
				Date endDate = null;
				Date startDate = null;
				try {
					startDate = DateUtils.parseDate(sDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
					endDate = DateUtils.parseDate(eDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
					parse.add("+createdAt:[" + startDate.getTime() + " TO " + endDate.getTime() + "]");
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
			if (param.getSourceType() != null && param.getSourceType() != -1) {
				parse.add("+sourceType:[" + param.getSourceType() + " TO " + param.getSourceType() + "]");
			}
			if (param.getId() != null && param.getId() > 0) {
				parse.add("+id:[" + param.getId() + " TO " + param.getId() + "]");
			}
			return StringUtils.join(parse, " AND ");
		}
		return StringUtils.EMPTY;
	}
}
