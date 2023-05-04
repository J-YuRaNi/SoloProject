<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%-- form액션태그 commandName=이름(DTO객체이름)
       <form:errors element="div"/> div태그형태로 에러메세지 출력하겠다(설정)
       <form:password path="name이름">  <input type="password" name="pwd">
--%>

<form:form commandName="command">
	<form:errors element="div"/>
	<spring:message code="write.form.pwd"/>
	<form:password path="pwd"/>
	<form:errors path="pwd"/><br>
	
	<input type="submit" value="<spring:message code="write.form.submit"/>">
	<input type="button" value="<spring:message code="list.content.title"/>" 
	                               onclick="location.href='list.do'">
</form:form>