function init() {
    setInterval(getNewQuote, 5000);
}
////////////////////// QUOTE //////////////////////
function getNewQuote() {
    let quote = document.getElementById("quote-text");
    const Http = new XMLHttpRequest();
    const url='http://localhost:8095/quote';
    Http.open("GET", url);
    Http.setRequestHeader("Access-Control-Allow-Origin", "http:localhost:8095");
    Http.send();
    Http.onreadystatechange=(e)=>{
        console.log(Http.responseText);
        quote.innerText = JSON.parse(Http.responseText).quote;
    };
}
////////////////////// BACKGROUND IMAGE //////////////////////
function getBackgroundImage() {
    const Http = new XMLHttpRequest();
    const url='http://localhost:60002/picture';
    Http.open("GET", url);
    Http.setRequestHeader("Access-Control-Allow-Origin", "http:localhost:60002");
    Http.send();
    Http.onreadystatechange=(e)=>{
        console.log(Http.responseText);
        changeBackgroundImage(Http.responseText);
    };
}

function changeBackgroundImage(image) {
    //const currentImage = document.getElementsByClassName("active")[0].previousSibling;

}

////////////////////// GAME LOGIC //////////////////////
document.onkeydown = function(e) {
    switch (e.keyCode) {
        case 37:
            sendGameState('left');
            break;
        case 38:
            sendGameState('up');
            break;
        case 39:
            sendGameState('right');
            break;
        case 40:
            sendGameState('down');
            break;
    }
};
function sendGameState(keypress) {
    const squares = document.getElementsByClassName("square");
    const score = document.getElementById("score-text").innerText;
    let gameState = '';
    for(const square of squares) {
        if (square.innerText === '\xa0') {
            gameState += ',' + '0';
        } else {
            gameState += ',' + square.innerText
        }
    }
    const Http = new XMLHttpRequest();
    const url='http://localhost:60003/new-state?direction=' + keypress + '&boardSetup=' + gameState.substring(1) + '&score=' + score;
    Http.open("GET", url);
    Http.setRequestHeader("Access-Control-Allow-Origin", "http:localhost:60003");
    Http.send();
    Http.onreadystatechange=(e)=>{
        console.log(Http.responseText);
        console.log(JSON.parse(Http.responseText));
        changeGameState(JSON.parse(Http.responseText));
    }
}

function changeGameState(newGameState) {

    const squares = document.getElementsByClassName("square");

    for (let i = 0; i < squares.length; i++) {
        if (newGameState.boardSetup[i] === 0) {
            squares[i].innerText = "\xa0";
        } else {
            squares[i].innerText = newGameState.boardSetup[i];
        }
    }

    document.getElementById("score-text").innerText = newGameState.score;


}
init();