<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="message/write.do" modelAttribute="messageForm">
		<form:hidden path="action" />

	<!-- Poder modificar titulo y texto-->
	<jstl:if test="${messageForm.action ne 2 }">
		<acme:textbox code="message.subject" path="subject" />
		<acme:textbox code="message.text" path="text" />
	</jstl:if>

	<!-- No poder modificar titulo y texto-->
	<jstl:if test="${messageForm.action eq 2 }">
		<form:hidden path="subject" />
		<form:hidden path="text" />
		<spring:message code="message.subject" />:
	<jstl:out value="${messageForm.subject}" />
		<br />

		<fieldset>
			<legend>
				<spring:message code="message.text" />
			</legend>
			<jstl:out value="${messageForm.text}" />
		</fieldset>
		<br />
	</jstl:if>

	<!--Poder modificar destinatario-->
	<jstl:if test="${messageForm.action ne 1 }">
		<form:label path="recipient">
			<spring:message code="message.for" />
		</form:label>
		<form:select path="recipient">
			<jstl:forEach items="${actors}" var="actor">
				<form:option value="${actor.id}">
					<jstl:out
						value="${actor.name} ${actor.surname} (${actor.userAccount.username})" />
				</form:option>
			</jstl:forEach>
		</form:select>
		<form:errors path="recipient" cssClass="error" />
	</jstl:if>

	<!-- No poder modificar destinatario-->
	<jstl:if test="${messageForm.action eq 1 }">
		<form:hidden path="recipient" />
		<spring:message code="message.for" />:
	<jstl:out value="${messageForm.recipient.name} ${messageForm.recipient.surname} (${messageForm.recipient.userAccount.username})" />
		<br />
	</jstl:if>


	<br />
	<jstl:forEach items="${messageForm.attachments}" var="attachment"
		varStatus="i">
		<acme:textbox code="message.attachment.name"
			path="attachments[${i.index}].name" />
		<acme:textbox code="message.attachment.url"
			path="attachments[${i.index}].url" />
	</jstl:forEach>

	<acme:submit code="message.save" name="save" />

	<acme:submit code="message.addAttachment" name="addAttachment" />

	<jstl:if test="${messageForm.attachments.size()>0}">
		<acme:submit code="message.removeAttachment" name="removeAttachment" />
	</jstl:if>

	<acme:cancel code="message.cancel" url="" />
</form:form>