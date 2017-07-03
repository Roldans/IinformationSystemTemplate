
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

<spring:message	code="folder.name" />: &nbsp; <jstl:out value="${folder.name}" />
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="messages" requestURI="${requestURI}" id="row" excludedParams="*">

	<acme:maskedColumn sorteable="true" code="message.subject" text="${row.subject}"/>
	
	<acme:maskedColumn sorteable="true" code="message.sender" text="${row.senderName}"/>
	
	<acme:maskedColumn sorteable="true" code="message.recipient" text="${row.recipientName}"/>
	
	<display:column>
		<a href="message/view.do?messageId=${row.id}">
			<spring:message code="message.view"/>
		</a>
	</display:column>

</display:table>

<a href="message/write.do">
	<spring:message code="message.write"/>
</a>

