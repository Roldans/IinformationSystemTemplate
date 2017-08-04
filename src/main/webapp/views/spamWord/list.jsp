
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
	name="spamWords" requestURI="spamword/administrator/list.do" id="row">
	
	<!-- Action links -->

		<display:column>
		
			<a href="spamword/administrator/delete.do?spamWordId=${row.id}">
				<spring:message	code="spamWord.delete" />
			</a>
		
		</display:column>	
			
	<!-- Attributes -->
	
	
	<acme:maskedColumn code="spamWord.word" text="${row.word}" sorteable="false"/>
	</display:table>

<a href="spamword/administrator/edit.do">
	<spring:message	code="spamWord.create" />
</a>
