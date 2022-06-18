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
                            <h5 class="card-title d-inline-block" style="width: calc(35% - .25rem);">Game lobby</h5>
                            <h5 id="time_left" class="card-title d-inline-block" style="text-align: right; width: calc(65% - .25rem);">Remaining voting time: 2s</h5>
                            <div id="choices" class="d-flex" style="height: calc(100% - 2.5rem);">
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
                            <div id="my_cards" class="d-flex" style="height: calc(100% - 2.5rem);">
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
                let htmlText =  '<div class="card mt-3" style="width: 23%;">' +
                   '<div class="card-body">' +
                   '<h5 class="card-title" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">Round condition</h5>' +
                   '<img class="card-img-bottom" src="imgs/'+condition+'" id="condition" style="height: calc(100% - 2.5rem);"></div></div>';

                for (i = 1; i <= cards.length; i++) {
                    if (cards[i - 1] == 'null') {
                        continue;
                    }
                    text = cards? JSON.parse(cards[i - 1])['resource'] : 'text'
                    card_id = cards? JSON.parse(cards[i - 1])['card_id'] : 0
                    color = JSON.parse(cards[i - 1])['user_id'] == leader_id? 'text-white bg-warning' : '';
                    htmlText += '<div class="card mt-3 ms-3 '+color+'" id="card_'+i+'" style="width: 23%;">'
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
                    htmlText += '<div class="card mt-3 '+margin+'" id="card_'+i+'" style="width: 23%;">'
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
                // remaining_time = ((new Date() - new Date(JSON.parse(response['game_session'])['event_start_time'])) / 1000).toFixed(1)
                remaining_time = (JSON.parse(response['game_session'])['time_left'] / 1000).toFixed(1);
                document.getElementById('time_left').innerText = 'Remaining ' + (event_id == 2? 'voting' : 'choosing') + ' time: ' + remaining_time + 's';
                _stage = JSON.parse(response['game_session'])['stage']
                leader_id = JSON.parse(response['game_session'])['leader_id']
                printMyCard(response['user_cards'], event_id, leader_id);
                printChoices(JSON.parse(response['condition'])['resource'], response['user_choices'], event_id, leader_id);
                _response = response
            }

            interval = setInterval(checkGameStatus, 1000);
            checkGameStatus();

            //RESPONSE = JSON.parse('{"condition":"{\\"card_id\\": 1, \\"type\\": \\"image\\", \\"resource\\": \\"1.png\\"}","user_choices":["null","null"],"user_cards":["{\\"card_id\\": 7, \\"type\\": \\"text\\", \\"resource\\": \\"TEXT description for card number 4\\"}","{\\"card_id\\": 8, \\"type\\": \\"text\\", \\"resource\\": \\"TEXT description for card number 5\\"}","{\\"card_id\\": 9, \\"type\\": \\"text\\", \\"resource\\": \\"TEXT description for card number 6\\"}"],"game_session":"{\\"session_id\\": 112, \\"stage\\": 1, \\"leader_id\\": 17, \\"condition_id\\": 1, \\"event_start_time\\": \\"2022-06-18 20:40:13.193\\", \\"event_id\\": 1}"}');
            //printMyCard(RESPONSE["user_cards"], RESPONSE["game_session"]["event_id"], RESPONSE["game_session"]["leader_id"])
            //printChoices(JSON.parse(RESPONSE["condition"])["resource"], RESPONSE["user_choices"], RESPONSE["game_session"]["event_id"], RESPONSE["game_session"]["leader_id"]);
        </script>
    </body>
</html>
