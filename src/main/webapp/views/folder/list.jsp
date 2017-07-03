
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="folders" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

		<display:column>
		<jstl:if test="${not(row.readOnly)}">
			<a href="folder/edit.do?folderId=${row.id}">
				<spring:message	code="folder.edit" />
			</a>
		</jstl:if>
		</display:column>	
		<display:column>
			<a href="folder/view.do?folderId=${row.id}">
				<spring:message	code="folder.view" />
			</a>
		</display:column>	
	
	<!-- Attributes -->
	
	
	<acme:maskedColumn code="folder.name" text="${row.name}" sorteable="false"/>
	
	<acme:maskedColumn code="folder.readOnly" text="${row.readOnly}" sorteable="false"/>
</display:table>

<a href="folder/edit.do?folderId=">
	<spring:message	code="folder.create" />
</a>

|<a href="message/write.do">
	<spring:message code="message.write"/>
</a>
