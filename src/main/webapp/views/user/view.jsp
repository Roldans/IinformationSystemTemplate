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

<acme:image url="${user.picture}"/><br/>

<fieldset>

<b><spring:message code="actor.username"/>:</b>
<acme:mask text="${user.userAccount.username}"/><br/>

<b><spring:message code="actor.name"/>:</b>
<acme:mask text="${user.name}"/><br/>

<b><spring:message code="actor.surname"/>:</b>
<acme:mask text="${user.surname}"/><br/>

<b><spring:message code="actor.email"/>:</b>
<jstl:out value="${user.email}"/><br/>

<b><spring:message code="actor.phone"/>:</b>
<jstl:out value="${user.phone}"/><br/>

<b><spring:message code="user.genre"/>:</b>
<jstl:if test="${user.genre eq 'male'}">
	<spring:message code="user.male" />
</jstl:if>
<jstl:if test="${user.genre eq 'female'}">
	<spring:message code="user.female" />
</jstl:if><br/>

<b><spring:message code="user.address"/>:</b>
<acme:mask text="${user.address}"/><br/>

</fieldset>
	<a href="message/write.do?actorId=${user.id}">
    	<spring:message  code="actor.sendMessage" />
	</a>


<fieldset>

	<h2><spring:message  code="user.comments" />:</h2>
	<display:table pagesize="5" class="displaytag1" name="comments"
		requestURI="${requestURI}" id="row">

		<!-- Action links -->
		<spring:message code="actor.username" var="actorName" />
	    <display:column title="${actorName}">
	      <a href="user/view.do?userId=${row.user.id}">
	   	  <acme:mask text="${row.user.userAccount.username}"/>
	   	  </a>
	    </display:column>
		<!-- Attributes -->
		<acme:column sorteable="true" code="comment.postMoment" path="postMoment" />

		<acme:column sorteable="true" code="comment.title" path="title" />

		<acme:column sorteable="true" code="comment.body" path="body" />

	</display:table>
	<a href="comment/user/create.do?commentableId=${user.id}">
    	<spring:message  code="actor.comment" />
	</a>
</fieldset>

<jstl:if test="${owner}">
	<a href="user/user/edit.do?userId=${user.id}">
    	<spring:message  code="actor.edit" />
	</a>
	
	
	
	
</jstl:if>


<security:authorize access="hasRole('USER')">
	<jstl:if test="${!owner}">
	
	<a href="abuseReport/user/edit.do?reportedId=${user.id}">
    	<spring:message  code="user.report" />
	</a>
</jstl:if>
</security:authorize>

