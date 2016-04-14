<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="//cdn.bootcss.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<link href="//cdn.bootcss.com/jqPlot/1.0.8/jquery.jqplot.min.css" rel="stylesheet">
<script src="//cdn.bootcss.com/jqPlot/1.0.8/jquery.jqplot.min.js"></script>
<script src="//cdn.bootcss.com/jqPlot/1.0.8/plugins/jqplot.pieRenderer.min.js"></script>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="${ctx}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<style type="text/css">
body {padding-top: 60px;padding-bottom: 40px;}
.sidebar-nav {padding: 9px 0;}
@media (max-width: 980px) {.navbar-text.pull-right {float: none;padding-left: 5px;padding-right: 5px;}}
</style>
<title>${title }</title>
</head>
<body>