<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${!isAdmin}">
<spring:message code="message.from"/>:
<acme:mask text="${res.senderName}"/><br/>
</jstl:if>

<jstl:if test="${isAdmin}">

<div style="color: green; font-weight: bold" >
<spring:message code="message.from"/>:
<jstl:out value="${res.senderName}" /><br/>

</div>
</jstl:if>
<spring:message code="message.for"/>:
<acme:mask text="${res.recipientName}"/><br/>

<spring:message code="message.sendingMoment"/>:
<acme:mask text="${res.sendingMoment}"/><br/>

<spring:message code="message.subject"/>:
<acme:mask text="${res.subject}"/><br/>

<fieldset>
	<legend><spring:message code="message.text"/></legend>
	<acme:mask text="${res.text}"/>
</fieldset><br/>

<jstl:if test="${attachments.size()!=0}">
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="attachments" requestURI="${requestURI}" id="row" excludedParams="*">
		
		<spring:message code="message.attachments" var="varAttachments" />
		<display:column title="${varAttachments}" sortable="false">
			<a href="${row.url}">
				<jstl:choose>
					<jstl:when test="${row.name.equals('')}">
						${row.url}
					</jstl:when>
					<jstl:otherwise>
						<acme:mask text="${row.name}"/>
					</jstl:otherwise>
				</jstl:choose>
			</a>
		</display:column>
		
	</display:table>
</jstl:if>
<form:form action="message/move.do" modelAttribute="res">
		<form:hidden path="Id"/>
		<form:label path="folder">
			<spring:message code="message.folder" />
		</form:label>
		<form:select path="folder">
			<jstl:forEach items="${folders}" var="folder">
				<form:option value="${folder.id}">
					<jstl:out
						value="${folder.name}" />
				</form:option>
			</jstl:forEach>
		</form:select>
		<form:errors path="folder" cssClass="error" />
		<acme:submit code="message.move" name="save" />
</form:form>
		
<br>		
<jstl:if test="${res.folder!=null}">
	<a href="message/reply.do?messageId=${res.id}">
		<spring:message code="message.reply"/>
	</a> | 
</jstl:if>

<a href="message/forward.do?messageId=${res.id}">
	<spring:message code="message.forward"/>
</a> | 

<a href="message/delete.do?messageId=${res.id}" onclick="return confirm('<spring:message code="confirm.delete" />')">
	<spring:message code="message.delete"/>
</a>