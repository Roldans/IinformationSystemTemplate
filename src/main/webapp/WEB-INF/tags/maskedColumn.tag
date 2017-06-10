<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://utilities/functions" prefix="f" %>
<%-- Attributes --%> 
 
<%@ attribute name="text" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="sorteable" required="true" %>

<%@ attribute name="highlight" required="false" %>

<jstl:if test="${highlight == null}">
	<jstl:set var="highlight" value="" />
</jstl:if>

<%-- Definition --%>
	
<spring:message code="${code}" var="codeName" />
<display:column  title="${codeName}" sortable="${sorteable}" style="${highlight}">${f:replaceAll(text, 
'emailAndPhone', '****')}</display:column>
 

