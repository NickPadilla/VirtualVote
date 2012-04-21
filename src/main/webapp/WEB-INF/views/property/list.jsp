<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>System Properties</title>
</head>
<body>
	<div align="center">
		<br /> 
		<a id="home" class="textLink" 	href="<c:url value="/j_spring_security_logout" />"><spring:message code="label.logout" /></a> 
		<br /> 
		<a id="home" class="textLink" 	href="<c:url value="/" />"><spring:message code="label.home" /></a> 
		<br />
		<br />
		<br /> 
		<br />
		<h2><spring:message code="${currentCategory.categoryLabel}" /></h2>
		<br /> 
		<br />
		<div style="width: 50%; display: inline-block;">
   			<h3><span  style="float: left; text-align: center;">Property</span></h3>
   			<h3><span  style="float: right; text-align: center;">Property Value</span></h3>
   		</div>
		<br /> 
		<c:forEach items="${properties}" var="property">
				<div style="width: 60%; display: inline-block;">
	    			<span  style="float: left;"><spring:message code="${property.propertyMeta.type.label}" /></span>
	    			<span  style="float: right;">${property.propertyMeta.propertyValue}</span>
	    		</div>
			<br /> 
			<br />
		</c:forEach>
	</div>
</body>
</html>