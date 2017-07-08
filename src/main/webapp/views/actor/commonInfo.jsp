 <%--
 * edit.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
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

	
<h2><spring:message code="actor.account.info"/></h2>

<acme:textbox code="actor.username" path="userAccount.username"/>

<acme:password code="actor.password" path="userAccount.password"/>

<div>
	<form:label path="confirmPassword">
		<spring:message code="actor.confirm.password" />
	</form:label>
	<form:password path="confirmPassword"/>
	<form:errors path="valid" cssClass="error" />
</div>
   
<h2><spring:message code="actor.info"/></h2>

   <acme:textbox code="actor.name" path="name"/>

<acme:textbox code="actor.surname" path="surname"/>

<acme:textbox code="actor.email" path="email"/>

<acme:textbox code="actor.phone" path="phone"/>
	
