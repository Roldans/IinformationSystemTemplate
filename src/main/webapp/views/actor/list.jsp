
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
	name="actors" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	<!-- Attributes -->
	
	
	<acme:maskedColumn code="actor.name" text="${row.name}" sorteable="false"/>
	<acme:maskedColumn code="actor.userAccount.username" text="${row.userAccount.username}" sorteable="false"/>
	<acme:maskedColumn code="actor.surname" text="${row.surname}" sorteable="false"/>
	<acme:maskedColumn code="actor.email" text="${row.email}" sorteable="false"/>
	<acme:maskedColumn code="actor.phone" text="${row.phone}" sorteable="false"/>
	
	
	
</display:table>
