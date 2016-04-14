<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span1">
		</div>
		<div class="span11">
			<legend><h2><a href="${ctx }">舆情浏览</a></h2></legend>
		    <form class="form-search">
		   	 <table id="contentTable" class="table table-bordered table_font">
				<tbody>
					<tr>
						<td>id：</td><td colspan="6"><input type="text" name="id" class="input-xlarge search-query" value="${param.id }" /></td>
					</tr>
					<tr>
						<td>标题：</td><td colspan="6"><input type="text" name="title" class="input-xxlarge search-query" value="${param.title }" /></td>
					</tr>
					<tr>
						<td>正文：</td><td colspan="6"><input type="text" name="formatContent" class="input-xxlarge search-query" value="${param.formatContent }" /></td>
					</tr>
					<tr>
						<td>类别：</td><td colspan="6">
						<select name="sourceType">
						<option value="-1" <c:if test="${empty param.sourceType or param.sourceType == -1}">selected="selected"</c:if>>全部</option>
						<c:forEach var="st" items="${sourceTypes }">
							<option value="${st.key }" <c:if test="${st.key == param.sourceType }">selected="selected"</c:if>>${st.value }</option>
						</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td>发布日期：</td>
						<td><input type="text" name="startDate" class="input-medium search-query" maxLength="15" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${param.startDate }" />
						~
						<input type="text" name="endDate" class="input-medium search-query" maxLength="15" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${param.endDate }" /></td>
					</tr>
					<tr><td colspan="7"><button type="submit" class="btn btn-primary">搜索</button></td></tr>
				</tbody>
			</table>
		    </form>
			<h4>共找到&nbsp;<b>${count }</b>&nbsp;条数据</h4>
			<c:if test="${fn:length(list)>0 }">
			<table id="contentTable"
				class="table table-striped table-hover table-bordered table_font">
				<thead>
					<td>id</td>
					<td>标题</td>
					<td>类别</td>
					<td>发布日期</td>
				</thead>
				<tbody>
					<c:forEach var="data" items="${list }">
					<tr id="tr_message_${data.id }">
						<td>${data.id }</td>
						<td>
						<c:if test="${fn:length(data.title)>30 }">${fn:substring(data.title,0,30) }...</c:if>
						<c:if test="${fn:length(data.title)<30 }">${data.title }</c:if>
						</td>
						<td>${sourceTypes[data.sourceType] }</td>
						<td><fmt:formatDate value="${data.release_date }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		${pageList }
		</c:if>
	</div>


<%@ include file="/WEB-INF/views/common/footer.jsp"%>