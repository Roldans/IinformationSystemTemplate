
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="now" class="java.util.Date" />

<fieldset>
	<form:form action="searchEngine/user/search.do" modelAttribute="search">
		<form:hidden path="id" />
		<form:hidden path="user" />
		
		
		
    
		
		
		<acme:textbox code="searchEngine.name" path="name"/>
		   
		
		
		<acme:submit name="save" code="searchEngine.search"/>
	</form:form>
</fieldset>

<br/>

<jstl:if test="${!results.isEmpty()}">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="search.results" requestURI="${requestURI}" id="row">
	
	
	<!-- Attributes -->
	<spring:message code="searchEngine.name" var="codeName" />
	<display:column  title="${codeName}">
		<a href="user/view.do?userId=${row.id}">
				<acme:mask text="${row.name}"/>
		</a>
	</display:column>
	
	<acme:maskedColumn sorteable="false" code="searchEngine.surname" text="${row.surname}" highlight="${style}" />
	<acme:maskedColumn sorteable="false" code="searchEngine.address" text="${row.address}" highlight="${style}" />
	<acme:maskedColumn sorteable="false" code="searchEngine.email" text="${row.email}" highlight="${style}" />
</display:table>

</jstl:if>