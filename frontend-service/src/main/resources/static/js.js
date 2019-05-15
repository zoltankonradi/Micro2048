function init() {
    setInterval(getNewQuote, 3500);
}
////////////////////// QUOTE //////////////////////
function getNewQuote() {
    let quote = document.getElementById("quote-text");
    // const Http = new XMLHttpRequest();
    // const url='http://localhost:8095/quote';
    // Http.open("GET", url);
    // Http.send();
    // Http.onreadystatechange=(e)=>{
    //     console.log(Http.responseText)
    // }
    quote.innerText = "Now it's a different inspirational quote";
}
////////////////////// BACKGROUND IMAGE //////////////////////
function changeBackgroundImage() {

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
    const score = document.getElementsByClassName("text-center");
    let gameState = '';
    for(const square of squares) {
        if (square.innerText === '\xa0') {
            gameState += ',' + '0';
        } else {
            gameState += ',' + square.innerText
        }
    }
    console.log(gameState.substring(1));
    const Http = new XMLHttpRequest();
    const url='http://localhost:60002/new-state?direction=' + keypress + '&boardSetup=' + gameState.substring(1) + '&score=' + score;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange=(e)=>{
        console.log(Http.responseText)
        //changeGameState(Http.responseText);
    }
}
// function changeGameState(newGameState) {
//
//     const squares = document.getElementsByClassName("square");
//     for (const square of squares) {
//         square.innerText=
//     }
//

// }
init();