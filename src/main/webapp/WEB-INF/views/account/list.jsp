<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<head>
    <title>List of Accounts</title>
</head>
<body>
	<div align="center">
		    <br/>
		    <a id="home" class="textLink" href="<c:url value="/j_spring_security_logout" />"><spring:message code="label.logout"/></a>
		    <a id="home" class="textLink" href="<c:url value="/" />"><spring:message code="label.home"/></a>
			<c:forEach items="${accounts}" var="account">
	    		Last Login : <spring:eval expression="account.login.lastLoggedIn" />   Account Info : ${account.login.username}/${account.login.password} - ${account.person.firstName} ${account.person.middleName} ${account.person.lastName} 
	    	<br/>
		</c:forEach>
	</div>
</body>
</html>