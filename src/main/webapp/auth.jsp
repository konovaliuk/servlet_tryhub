<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <c:if test='${command == "register"}'><title>Sign Up</title></c:if>
        <c:if test='${command == "login"}'><title>Sign In</title></c:if>
        <%@ include file="bootstrap.html" %>
        <link rel="stylesheet" href="<c:url value='css/auth.css'/>">
    <head>
    <body class="text-center">
        <div class="form-signin">
            <c:if test='${command == "register"}'><form action="<c:url value='/register'/>" method="POST"></c:if>
            <c:if test='${command == "login"}'><form action="<c:url value='/login'/>" method="POST"></c:if>
                <div class="form-floating">
                    <input type="text" class="form-control" name="login" placeholder="Username" value=${param.login}>
                    <label for="login">Username</label>
                </div>
                <div class="form-floating">
                    <input type="password" class="form-control" name="password" placeholder="Password" value=${param.password}>
                    <label for="password">Password</label>
                </div>
                <c:if test="${error != null}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>
                <c:if test='${command == "register"}'>
                    <button class="w-100 btn btn-lg btn-primary" type="submit">Sign up</button>
                    <p class="mt-2 text-muted">Already have an account? <a href="<c:url value='/login'/>">Sign in</a></p>
                </c:if>
                <c:if test='${command == "login"}'>
                    <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
                    <p class="mt-2 text-muted">Don&apos;t have an account? <a href="<c:url value='/register'/>">Sign up</a></p>
                </c:if>
            </form>
        </div>
    </body>
</html>