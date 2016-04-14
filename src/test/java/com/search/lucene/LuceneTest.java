package com.search.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.search.lucene.bean.Data;
import com.search.lucene.bean.SearchParam;
import com.search.lucene.service.DataService;
import com.search.lucene.service.LuceneService;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class LuceneTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private DataService dataService;
	@Autowired
	private LuceneService luceneService;
	@Autowired
	private Configuration configuration;

	@Test
	public void getListTest() throws IOException {
		ArrayList<Data> list = dataService.getList(configuration.getStartDate(), configuration.getEndDate());
		System.out.println(list.size());
	}

	@Test
	public void luceneIndex() {
		luceneService.index();
	}

	@Test
	public void parseTest() {
		SearchParam param = new SearchParam();
		param.setTitle("郎溪");
		param.setFormatContent("学生");
		param.setStartDate("2014-04-01 00:00:00");
		param.setEndDate("2014-04-01 23:59:59");
		param.setSourceType(1);
		param.setId(2292136);
		String parse = luceneService.parse(param);
		System.out.println(parse);
	}

	@Test
	public void searchTest() {
		SearchParam param = new SearchParam();
		param.setTitle("郎溪");
		param.setFormatContent("学生");
		param.setStartDate("2014-04-01 00:00:00");
		param.setEndDate("2014-04-01 23:59:59");
		// param.setSourceType(1);
		// param.setId(2292136);
		Map<String, Object> result1 = luceneService.search(param, 1, 4);
		System.out.println(result1.get("count"));
		System.out.println(result1.get("ids"));
		System.out.println();
		Map<String, Object> result2 = luceneService.search(param, 2, 4);
		System.out.println(result2.get("count"));
		System.out.println(result2.get("ids"));
		// System.out.println();
		// Map<String,Object> result3 = luceneService.search(param,3,2);
		// System.out.println(result3.get("count"));
		// System.out.println(result3.get("ids"));
	}

}
