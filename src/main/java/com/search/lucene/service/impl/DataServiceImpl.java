package com.search.lucene.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.search.lucene.bean.Data;
import com.search.lucene.service.DataService;

@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Data> getList() {
		String sql = "SELECT id,title,content,sourcetype,createdAt FROM `data`";
		return jdbcTemplate.query(sql, new RowMapper<Data>() {
			@Override
			public Data mapRow(ResultSet rs, int i) throws SQLException {
				Data data = new Data();
				data.setId(rs.getInt("id"));
				data.setTitle(rs.getString("title"));
				data.setContent(rs.getString("content"));
				data.setCreatedAt(rs.getDate("createdAt"));
				data.setSourceType(rs.getInt("sourcetype"));
				return data;
			}
		});
	}

	public List<Map<String, Object>> getListByIds(List<String> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			String sql = "SELECT id,title,content,sourcetype,createdAt FROM `data` WHERE id IN("
					+ StringUtils.join(ids, ",") + ")";
			return jdbcTemplate.queryForList(sql);
		}
		return new ArrayList<Map<String, Object>>();
	}
}