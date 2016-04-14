package com.search.lucene.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.search.lucene.bean.Data;

@Repository
public class DataDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ArrayList<Data> getList(String startDate, String endDate) {
		String sql = "SELECT id,title,format_content,sourcetype,release_date FROM `data`";
		if (startDate != null && endDate != null) {
			sql += " WHERE release_date >= '" + startDate + "' AND release_date <='" + endDate + "'";
		}
		return (ArrayList<Data>) jdbcTemplate.query(sql, new RowMapper<Data>() {
			@Override
			public Data mapRow(ResultSet rs, int i) throws SQLException {
				Data data = new Data();
				data.setId(rs.getInt("id"));
				data.setTitle(rs.getString("title"));
				data.setFormatContent(rs.getString("format_content"));
				data.setReleaseDate(rs.getDate("release_date"));
				data.setSourceType(rs.getInt("sourcetype"));
				return data;
			}
		});
	}

	public List<Map<String, Object>> getListByIds(List<String> ids) {
		if (ids.size() > 0) {
			String sql = "SELECT id,title,format_content,sourcetype,release_date FROM `data` WHERE id IN("
					+ StringUtils.join(ids, ",") + ")";
			return jdbcTemplate.queryForList(sql);
		}
		return new ArrayList<Map<String, Object>>();
	}
}
