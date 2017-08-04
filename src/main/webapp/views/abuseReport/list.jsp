
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="abuseReports" requestURI="${requestURI}" id="row">
	
	<spring:message code="abuseReport.reported" var="reported"/>
	<display:column title="${reported}">
		<a href="user/view.do?userId=${row.reported.id}">
			<jstl:out value="${row.reported.userAccount.username}" />
		</a>
	</display:column>
	
	<spring:message code="abuseReport.reporter" var="reporter"/>
	<display:column title="${reporter}">
		<jstl:choose>
			<jstl:when test="${row.reporter==null}">
				<spring:message code="abuseReport.deletedReporter" />
			</jstl:when>
			<jstl:otherwise>
				<a href="user/view.do?userId=${row.reporter.id}">
			<jstl:out value="${row.reporter.userAccount.username}" />
				</a>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<acme:column sorteable="false" code="abuseReport.description" path="description"/>
	
	<acme:column sorteable="false" code="abuseReport.reportDate" path="reportDate"/>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<jstl:choose>
		    <jstl:when test="${row.reported.banned == true}">
		        <display:column>
					<a href="user/administrator/unban.do?userId=${row.reported.id}&reported=1">
						<spring:message code="abuseReport.unban"/>
					</a>
				</display:column>
		    </jstl:when>    
		    <jstl:otherwise>
		        <display:column>
					<a href="user/administrator/ban.do?userId=${row.reported.id}&reported=1">
						<spring:message code="abuseReport.ban"/>
					</a>
				</display:column>
		    </jstl:otherwise>
		</jstl:choose>
	</security:authorize>


<security:authorize access="hasRole('ADMINISTRATOR')">
		 <display:column>
		    <jstl:if test="${row.reported.banned == true}">
		    
		    <jstl:set var="reported" value="${row.reported.id}"/>
		       <form method="get" action="user/administrator/delete.do">
    				<button type="submit" name="userId" value="${reported}" class="btn btn-primary" onclick="return confirm('<spring:message code="confirm.delete" />')">
						<spring:message code="user.delete" />
					</button>
				</form>
		    </jstl:if>    
		   </display:column>
	</security:authorize>
</display:table>
