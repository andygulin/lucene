package com.search.lucene.lstener;

import com.search.lucene.service.LuceneService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupIndexListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(sce.getServletContext());
        LuceneService luceneService = context.getBean(LuceneService.class);
        luceneService.index();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}