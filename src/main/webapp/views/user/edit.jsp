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

<form:form action="${requestURI}" modelAttribute="userForm">
	
	

	<%@include file="../actor/commonInfo.jsp" %>
	
	<form:label path="genre">
		<spring:message code="user.genre" />
	</form:label>
	<form:select id="genre" name="genre" path="genre">
    	<form:option value="male"><spring:message code="user.male" /></form:option>
    	<form:option value="female"><spring:message code="user.female" /></form:option>
    </form:select>
	<br/>
	
	<acme:textbox code="user.address" path="address"/>
	
	<acme:textbox code="user.picture" path="picture"/>

	<%@include file="../actor/footForm.jsp" %>
	
</form:form>