<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Main page</title>
        <%@ include file="bootstrap.html" %>
        <script src="./js/main.js"></script>
    <head>
    <body class="vh-100">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid w-75" style="height: 2.5rem;">
                <a class="navbar-brand mb-0 h1" href="#">${user.getLogin()}</a>
                <!-- <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>
                </ul> -->
                <a id="logout_btn" type="button" class="btn btn-outline-danger" href="/logout">Logout</a>
            </div>
        </nav>
        <div class="container-fluid mt-3 mb-3 w-75" style="height: calc(100% - 5.5rem);">
            <div class="row h-100">
                <div class="col-8 h-100">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Game lobby</h5>
                            <div class="align-items-center d-flex justify-content-center" style="height: calc(100% - 2rem);">
                                <button type="button" class="btn btn-outline-primary" id="search_btn">Start searching</button>
                                <button hidden type="button" class="btn btn-outline-danger" id="cancel_btn">Cancel searching</button>
                            </div>
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
        <script>
            let interval = null;

            const checkReadyGame = async () => {
                const response = await doRequest('/api/getSessionInfo', 'GET');
                console.log(response);
                if ('game_session' in response) {
                    document.location = '/lobby'
                }
            }

            const startSearching = () => {
                interval = setInterval(checkReadyGame, 1000);
                document.getElementById("search_btn").setAttribute("hidden", "");
                document.getElementById("logout_btn").setAttribute("hidden", "");
                document.getElementById("cancel_btn").removeAttribute("hidden");
            }

            console.log("<c:if test='${gameSearch == true}'>true</c:if>");
            console.log("<c:if test='${gameSearch != true}'>false</c:if>");
            <c:if test='${gameSearch == true}'>startSearching();</c:if>

            document.getElementById("search_btn").onclick = async () => {
                if ((await doRequest("/api/searchGame", "GET"))["result"] == true) {
                    startSearching();
                } else {
                    document.getElementById("search_btn").setAttribute("disabled", "");
                }
            }

            document.getElementById("cancel_btn").onclick = async () => {
                if ((await doRequest("/api/cancelSearch", "GET"))["result"] == true) {
                    document.location.reload();
                }
            }
        </script>
    </body>
</html>