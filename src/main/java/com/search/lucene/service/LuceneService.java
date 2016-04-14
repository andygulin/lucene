package com.search.lucene.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.search.lucene.Configuration;
import com.search.lucene.bean.Data;
import com.search.lucene.bean.SearchParam;
import com.search.lucene.ex.QueryParserEx;
import com.search.lucene.helper.Fields;

@Service
public class LuceneService {

	@Autowired
	private Configuration configuration;
	@Autowired
	private DataService dataSevice;

	public void index() {
		Directory directory = null;
		IndexWriter indexWriter = null;
		try {
			directory = FSDirectory.open(new File(configuration.getIdxPath()));
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_36, new IKAnalyzer());
			indexWriterConfig.setOpenMode(OpenMode.CREATE);
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			String startDate = configuration.getStartDate();
			String endDate = configuration.getEndDate();
			ArrayList<Data> list = dataSevice.getList(startDate, endDate);
			if (list != null && list.size() > 0) {
				Document document = null;
				for (Data data : list) {
					document = new Document();
					document.add(Fields.newInt("id", data.getId(), Store.YES, true));
					document.add(Fields.newString("title", data.getTitle(), Store.NO, Index.ANALYZED));
					document.add(Fields.newString("format_content", data.getFormatContent(), Store.NO, Index.ANALYZED));
					document.add(Fields.newInt("sourcetype", data.getSourceType(), Store.NO, true));
					document.add(Fields.newLong("release_date", data.getReleaseDate(), Store.NO, true));
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

	public Map<String, Object> search(SearchParam param, int pageIndex, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (param.isEmpty()) {
			result.put("ids", new LinkedList<String>());
			result.put("count", 0);
			return result;
		}
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
			fields.put("sourcetype", Integer.class);
			fields.put("release_date", Long.class);

			queryParser = new QueryParserEx(Version.LUCENE_36, "", new IKAnalyzer(), fields);
			String parse = parse(param);
			System.out.println(parse);
			Query query = queryParser.parse(parse);
			Sort sort = new Sort();
			SortField sortField = new SortField("release_date", SortField.LONG, true);
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
			result.put("ids", ids);
			result.put("count", scoreDocs.length);
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
		return result;
	}

	public String parse(SearchParam param) {
		if (param != null) {
			List<String> parse = new ArrayList<String>();
			if (param.getTitle().length() > 0) {
				parse.add("+title:" + param.getTitle());
			}
			if (param.getFormatContent().length() > 0) {
				parse.add("+format_content:" + param.getFormatContent());
			}
			String sDate = param.getStartDate();
			String eDate = param.getEndDate();
			if (sDate.length() > 0 && eDate.length() > 0) {
				Date endDate = null;
				Date startDate = null;
				try {
					startDate = DateUtils.parseDate(sDate, "yyyy-MM-dd HH:mm:ss");
					endDate = DateUtils.parseDate(eDate, "yyyy-MM-dd HH:mm:ss");
					parse.add("+release_date:[" + startDate.getTime() + " TO " + endDate.getTime() + "]");
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
			if (param.getSourceType() != null && param.getSourceType() != -1) {
				parse.add("+sourcetype:[" + param.getSourceType() + " TO " + param.getSourceType() + "]");
			}
			if (param.getId() != null) {
				parse.add("+id:[" + param.getId() + " TO " + param.getId() + "]");
			}
			return StringUtils.join(parse, " AND ");
		}
		return "";
	}
}
