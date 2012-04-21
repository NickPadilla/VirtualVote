<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>
<head>
    <title></title>
</head>
<body>
	<div align="center">
		<br/>
		<h2>Welcome! <security:authentication property="name" /></h2>
		<br/>
		<security:authorize access="!isAnonymous()">
			<br/>
			<br/>
			<a id="logout" href="<c:url value="/j_spring_security_logout" />"><spring:message code="label.logout"/></a>
		    <br/>
		    <br/>
		    <a id="accountView" href="<c:url value="/account/view" />"><spring:message code="label.viewAccount"/></a>
		    <security:authorize access="hasRole('ADMIN')">
			    <br/>
			    <br/>
			    <a id="accounts" href="<c:url value="/account/list" />"><spring:message code="label.listAccounts"/></a>
			    <br/>
			    <br/>
			    <a id="properties" href="<c:url value="/property/list" />"><spring:message code="label.properties"/></a>
			</security:authorize>
		</security:authorize>
		<security:authorize access="hasRole('GUEST')">
			<br/>
			<a id="login" href="<c:url value="/spring_security_login" />"><spring:message code="label.login"/></a>
		    <br/>
		    <br/>
		    <a id="accountCreate" href="<c:url value="/account/create" />"><spring:message code="label.createAccount"/></a>
	    </security:authorize>
	</div>
</body>
</html>