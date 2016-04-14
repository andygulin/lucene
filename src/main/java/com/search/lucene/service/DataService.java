package com.search.lucene.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.lucene.Configuration;
import com.search.lucene.bean.Data;
import com.search.lucene.dao.DataDao;

@Service
public class DataService {

	@Autowired
	private DataDao dataDao;
	@Autowired
	private Configuration configuration;

	@SuppressWarnings("unchecked")
	public ArrayList<Data> getList(String startDate, String endDate) {
		ArrayList<Data> list = null;
		byte[] bytes = null;
		String cachePath = configuration.getCachePath();
		String cacheFile = cachePath + DigestUtils.md5Hex((startDate + endDate).trim());
		File file = new File(cacheFile);
		try {
			if (file.exists()) {
				bytes = FileUtils.readFileToByteArray(file);
				list = (ArrayList<Data>) SerializationUtils.deserialize(bytes);
			} else {
				list = dataDao.getList(startDate, endDate);
				bytes = SerializationUtils.serialize(list);
				FileUtils.writeByteArrayToFile(file, bytes, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> getListByIds(List<String> ids) {
		return dataDao.getListByIds(ids);
	}
}
