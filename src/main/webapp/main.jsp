<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Main page</title>
        <%@ include file="bootstrap.html" %>
    <head>
    <body class="vh-100">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid w-75">
                <a class="navbar-brand mb-0 h1" href="#">${user.getLogin()}</a>
                <!-- <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>
                </ul> -->
                <a type="button" class="btn btn-outline-danger" href="/logout">Logout</a>
            </div>
        </nav>
        <div class="container-fluid mt-3 mb-3 w-75" style="height: calc(100% - 5.5rem);">
            <div class="row h-100">
                <div class="col-8 h-100">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Game lobby</h5>
                        </div>
                    </div>
                </div>
                <div class="col-4 h-100">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Users online</h5>
                            <ul class="list-group mt-3">
                                <c:forEach items="${users}" var="user">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        ${user.getLogin()}
                                        <span class="badge bg-primary rounded-pill">${user.getRate()}</span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>