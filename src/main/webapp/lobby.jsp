<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Game page</title>
        <%@ include file="bootstrap.html" %>
        <script src="./js/main.js"></script>
    <head>
    <body class="vh-100">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid w-75" style="height: 2.5rem;">
                <a class="navbar-brand mb-0 h1" href="#">${user.getLogin()}</a>
            </div>
        </nav>
        <div class="container-fluid mt-3 mb-3 w-75" style="height: calc(100% - 5.5rem);">
            <div class="row" style="height: 49%">
                <div class="col-12 h-100">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Game lobby</h5>
                            <div id="choices" class="h-100 d-flex">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row" style="height: 49%; margin-top: 1%;">
                <div class="col-12 h-100">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Your cards</h5>
                            <div id="my_cards" class="h-100 d-flex">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            let interval = null;
            let choice = null;
            let _stage = null;

            const makeChoice = (card_id) => {
                doRequest('/api/makeChoice?card_id=' + card_id, 'GET');
                choice = _stage;
            };

            const printChoices = (condition, cards, event_id, leader_id) => {
                let htmlText =  '<div class="card mt-3" style="height: calc(100% - 2.5rem); width: 23%;">' +
                   '<div class="card-body">' +
                   '<h5 class="card-title">Round condition</h5>' +
                   '<img class="card-img-bottom" src="imgs/'+condition+'" id="condition"></div></div>';

                for (i = 1; i <= cards.length; i++) {
                    if (cards[i - 1] == 'null') {
                        continue;
                    }
                    text = cards? JSON.parse(cards[i - 1])['resource'] : 'text'
                    card_id = cards? JSON.parse(cards[i - 1])['card_id'] : 0
                    htmlText += '<div class="card mt-3 ms-3" id="card_'+i+'" style="height: calc(100% - 2.5rem); width: 23%;">'
                    + '<div class="card-body">'
                    + '<div name="card-text">'+text+'</div>'

                    if (choice != _stage && event_id == 2 && ${user.getUserId()} == leader_id) {
                        htmlText += '<button onclick="makeChoice('+card_id+')" class="btn btn-primary" style="position: absolute; bottom: 1rem; width: calc(100% - 2rem);" name="card-button">'
                        + 'Choose'
                        + '</button>'
                    }

                    htmlText += '</div></div>';
                }

                document.getElementById('choices').innerHTML = htmlText;
            }

            const printMyCard = (cards, event_id, leader_id) => {
                let htmlText = '';

                for (i = 1; i <= 3; i++) {
                    text = cards? JSON.parse(cards[i - 1])['resource'] : 'text'
                    card_id = cards? JSON.parse(cards[i - 1])['card_id'] : 0
                    margin = i == 1? '' : 'ms-3';
                    htmlText += '<div class="card mt-3 '+margin+'" id="card_'+i+'" style="height: calc(100% - 2.5rem); width: 23%;">'
                    + '<div class="card-body">'
                    + '<div name="card-text">'+text+'</div>'

                    if (choice != _stage && event_id == 1 && ${user.getUserId()} != leader_id) {
                        htmlText += '<button onclick="makeChoice('+card_id+')" class="btn btn-primary" style="position: absolute; bottom: 1rem; width: calc(100% - 2rem);" name="card-button">'
                        + 'Choose'
                        + '</button>'
                    }

                    htmlText += '</div></div>';
                }

                document.getElementById('my_cards').innerHTML = htmlText;
            }

            printMyCard();

            const checkGameStatus = async () => {
                const response = await doRequest('/api/getSessionInfo', 'GET');
                if ('result' in response) {
                    document.location = '/main';
                }
                // document.getElementById('text').innerText = JSON.stringify(response);
                console.log(response);
                event_id = JSON.parse(response['game_session'])['event_id']
                _stage = JSON.parse(response['game_session'])['stage']
                leader_id = JSON.parse(response['game_session'])['leader_id']
                printMyCard(response['user_cards'], event_id, leader_id);
                printChoices(JSON.parse(response['condition'])['resource'], response['user_choices'], event_id, leader_id);
                _response = response
            }

            interval = setInterval(checkGameStatus, 5000);
            checkGameStatus();
        </script>
    </body>
</html>