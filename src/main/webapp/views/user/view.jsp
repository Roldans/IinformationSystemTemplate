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

<acme:image url="${animaniac.picture}"/><br/>

<fieldset>

<b><spring:message code="actor.username"/>:</b>
<acme:mask text="${animaniac.userAccount.username}"/><br/>

<b><spring:message code="animaniac.rate"/>:</b>
<acme:mask text="${animaniac.rate}"/><br/>

<b><spring:message code="actor.name"/>:</b>
<acme:mask text="${animaniac.name}"/><br/>

<b><spring:message code="actor.surname"/>:</b>
<acme:mask text="${animaniac.surname}"/><br/>

<b><spring:message code="actor.email"/>:</b>
<jstl:out value="${animaniac.email}"/><br/>

<b><spring:message code="actor.phone"/>:</b>
<jstl:out value="${animaniac.phone}"/><br/>

<b><spring:message code="animaniac.genre"/>:</b>
<jstl:if test="${animaniac.genre eq 'male'}">
	<spring:message code="animaniac.male" />
</jstl:if>
<jstl:if test="${animaniac.genre eq 'female'}">
	<spring:message code="animaniac.female" />
</jstl:if><br/>

<b><spring:message code="animaniac.address"/>:</b>
<acme:mask text="${animaniac.address}"/><br/>

</fieldset>
	<a href="message/write.do?actorId=${animaniac.id}">
    	<spring:message  code="actor.sendMessage" />
	</a>
<jstl:if test="${curriculum!=null}">
	<fieldset>
		<h2><spring:message  code="animaniac.curriculum" />:</h2>
	
		<b><spring:message code="curriculum.educationSection"/>:</b><br/>
		<jstl:out value="${curriculum.educationSection}"/><br/>
		
		<b><spring:message code="curriculum.experienceSection"/>:</b><br/>
		<jstl:out value="${curriculum.experienceSection}"/><br/>
		
		<b><spring:message code="curriculum.hobbiesSection"/>:</b><br/>
		<jstl:out value="${curriculum.hobbiesSection}"/><br/>
		
	</fieldset>
	
</jstl:if>

<fieldset>

	<h2><spring:message  code="animaniac.comments" />:</h2>
	<display:table pagesize="5" class="displaytag1" name="comments"
		requestURI="${requestURI}" id="row">

		<!-- Action links -->
		<spring:message code="actor.username" var="actorName" />
	    <display:column title="${actorName}">
	      <a href="animaniac/view.do?animaniacId=${row.animaniac.id}">
	   	  <acme:mask text="${row.animaniac.userAccount.username}"/>
	   	  </a>
	    </display:column>
		<!-- Attributes -->
		<acme:column sorteable="true" code="comment.postMoment" path="postMoment" />

		<acme:column sorteable="true" code="comment.title" path="title" />

		<acme:column sorteable="true" code="comment.body" path="body" />

	</display:table>
	<a href="comment/animaniac/create.do?commentableId=${animaniac.id}">
    	<spring:message  code="actor.comment" />
	</a>
</fieldset>

<jstl:if test="${owner}">
	<a href="animaniac/animaniac/edit.do?animaniacId=${animaniac.id}">
    	<spring:message  code="actor.edit" />
	</a>
	
	|
	
	<a href="curriculum/animaniac/edit.do">
		<jstl:if test="${curriculum==null}">
    		<spring:message  code="animaniac.create.curr" />
		</jstl:if>
		<jstl:if test="${curriculum!=null}">
    		<spring:message  code="animaniac.edit.curr" />
		</jstl:if>
	</a>
	|
</jstl:if>

<a href="pet/animaniac/list.do?animaniacId=${animaniac.id}">
    	<spring:message  code="pet.list" />
	</a>

<security:authorize access="hasRole('ANIMANIAC')">
	<jstl:if test="${!owner}">
	|
	<a href="abuseReport/animaniac/edit.do?reportedId=${animaniac.id}">
    	<spring:message  code="animaniac.report" />
	</a>
</jstl:if>
</security:authorize>

