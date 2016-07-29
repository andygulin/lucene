package com.search.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.search.lucene.bean.Data;
import com.search.lucene.bean.SearchParam;
import com.search.lucene.bean.SearchResult;
import com.search.lucene.service.DataService;
import com.search.lucene.service.LuceneService;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class LuceneTest extends AbstractJUnit4SpringContextTests {

	private static final Logger logger = Logger.getLogger(LuceneTest.class);

	@Autowired
	private DataService dataService;
	@Autowired
	private LuceneService luceneService;
	@Autowired
	private Configuration configuration;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void initData() {
		String sql = "INSERT INTO `data` VALUES(NULL,?,?,?,?)";
		final int BATCH_TIME = 10;
		final int BATCH_SIZE = 100;
		for (int time = 1; time <= BATCH_TIME; time++) {
			List<Object[]> batchArgs = new ArrayList<>(BATCH_SIZE);
			for (int i = 0; i < BATCH_SIZE; i++) {
				batchArgs.add(new Object[] { getTitle(), getContent(), getSourceType(), getCreatedAt() });
			}
			jdbcTemplate.batchUpdate(sql, batchArgs);
			logger.info("total time : " + BATCH_TIME + " , current time : " + time + " , insert num : " + BATCH_SIZE);
		}
		logger.info("finish , total : " + (BATCH_SIZE * BATCH_TIME));
	}

	private static final String str = "abcdefghijklmnopqrstuvwxyz";

	private String getTitle() {
		List<String> words = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			words.add(RandomStringUtils.random(5, str));
		}
		return StringUtils.join(words, " ");
	}

	private String getContent() {
		List<String> words = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			words.add(RandomStringUtils.random(5, str));
		}
		return StringUtils.join(words, " ");
	}

	private int getSourceType() {
		Set<Integer> keys = configuration.getSourceTypes().keySet();
		Integer[] arr = keys.toArray(new Integer[keys.size()]);
		return arr[RandomUtils.nextInt(arr.length)];
	}

	private Date getCreatedAt() {
		int day = RandomUtils.nextInt(100);
		return DateUtils.addDays(new Date(), -day);
	}

	@Test
	public void getListTest() throws IOException {
		List<Data> list = dataService.getList();
		logger.info(list.size());
	}

	@Test
	public void luceneIndex() {
		luceneService.index();
	}

	@Test
	public void searchDate() {
		SearchParam param = new SearchParam();
		param.setStartDate("2016-07-28 00:00:00");
		param.setEndDate("2016-07-28 23:59:59");
		SearchResult result = luceneService.search(param, 1, 10);
		logger.info(result.getCount());
		logger.info(result.getIds());
	}

	@Test
	public void searchTitle() {
		SearchParam param = new SearchParam();
		param.setTitle("dgpuc");
		SearchResult result = luceneService.search(param, 1, 10);
		logger.info(result.getCount());
		logger.info(result.getIds());
	}

	@Test
	public void searchContent() {
		SearchParam param = new SearchParam();
		param.setContent("rzyqv");
		SearchResult result = luceneService.search(param, 1, 10);
		logger.info(result.getCount());
		logger.info(result.getIds());
	}

	@Test
	public void search() {
		SearchParam param = new SearchParam();
		param.setStartDate("2016-07-28 00:00:00");
		param.setEndDate("2016-07-28 23:59:59");
		param.setSourceType(2);
		SearchResult result = luceneService.search(param, 1, 10);
		logger.info(result.getCount());
		logger.info(result.getIds());
	}
}