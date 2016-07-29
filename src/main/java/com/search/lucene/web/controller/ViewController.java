package com.search.lucene.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.search.lucene.Configuration;
import com.search.lucene.Constants;
import com.search.lucene.bean.Page;
import com.search.lucene.bean.SearchParam;
import com.search.lucene.bean.SearchResult;
import com.search.lucene.service.DataService;
import com.search.lucene.service.LuceneService;

@Controller
public class ViewController {

	@Autowired
	private DataService dataService;
	@Autowired
	private LuceneService luceneService;
	@Autowired
	private Configuration configuration;

	@GetMapping("/view")
	public String welcome(@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model,
			HttpServletRequest request, SearchParam param) {
		Page page = new Page();
		page.setUrl(request.getRequestURI() + param.buildUrl());
		page.setPageNo(pageNumber);
		SearchResult result = luceneService.search(param, pageNumber, Constants.DEFAULT_PAGE_SIZE);
		int count = result.getCount();
		List<String> ids = result.getIds();
		page.setCount((long) count);
		page.setRowPerPage(Constants.DEFAULT_PAGE_SIZE);
		page.setShowNum(20);

		List<Map<String, Object>> list = dataService.getListByIds(ids);
		model.addAttribute("list", list);
		model.addAttribute("title", "舆情浏览");
		model.addAttribute("pageList", page.pageList());
		model.addAttribute("sourceTypes", configuration.getSourceTypes());
		model.addAttribute("count", count);
		return "view";
	}
}