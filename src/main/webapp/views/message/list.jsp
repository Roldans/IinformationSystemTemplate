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

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="message" requestURI="${requestURI}" id="row" excludedParams="*">

	<acme:maskedColumn sorteable="true" code="message.subject" text="${row.subject}"/>
	
	<acme:maskedColumn sorteable="true" code="message.from" text="${ row.senderName}"/>
	
	<acme:maskedColumn  sorteable="true" code="message.for" text="${row.recipientName}"/>
	
	<display:column>
		<a href="message/actor/view.do?messageId=${row.id}">
			<spring:message code="message.view"/>
		</a>
	</display:column>

</display:table>

<a href="message/actor/write.do">
	<spring:message code="message.write"/>
</a>
