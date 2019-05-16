function init() {
    backgroundPictureMovementOnKeyPress();
    setInterval(getNewQuote, 5000);
    initColors();
}

function initColors() {
    const squares = document.getElementsByClassName("square");
    for (let square of squares) {
        if (square.innerText === '2') {
            square.style.backgroundColor = '#fff4ba';
        }
    }
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
        quote.innerText = JSON.parse(Http.responseText).quote;
    };
}

////////////////////// BACKGROUND IMAGE //////////////////////

function backgroundPictureMovementOnKeyPress() {
    document.addEventListener("keydown", backgroundChangerEventListener)
}

function backgroundChangerEventListener(e) {
    if (e.key !== "ArrowLeft" && e.key !== "ArrowRight" && e.key !== "ArrowUp" && e.key !== "ArrowDown") {
        return;
    }
    document.removeEventListener("keydown", backgroundChangerEventListener);
    handleBackgroundChange(e.key);
}

function handleBackgroundChange(keyDown) {
    //Get HTML elements we're working on
    let pictureElements = getPictureElements();
    let outSlidingImage = pictureElements.outSlidingImage;
    let inSlidingImage = pictureElements.inSlidingImage;
    let updatingImage = pictureElements.updatingImage;

    updateImage(updatingImage);

    slidePictures(outSlidingImage, inSlidingImage, keyDown);
}

function setInSlidingImagePosition(inSlidingImage, inSlidingImagePosition) {
    inSlidingImage.style.left = inSlidingImagePosition.left + "%";
    inSlidingImage.style.right = inSlidingImagePosition.right + "%";
    inSlidingImage.style.top = inSlidingImagePosition.top + "%";
}

function slidePictures(outSlidingImage, inSlidingImage, keyDown) {
    let positions = getSlidingPicturePositions(keyDown);
    let inSlidingImageStarterPosition = positions.inSlidingImageStarterPosition;
    let outSlidingImageFinishingPosition = positions.outSlidingImageFinishingPosition;

    let transitionLength = 100;
    let transitionSteps = 10;
    let canStop = {canStop: false};

    inSlidingImage.classList.replace("inactive-picture", "active-picture");
    setInSlidingImagePosition(inSlidingImage, inSlidingImageStarterPosition);


    let movementInterval = setInterval(function () {
        if (!canStop.canStop) {
            timeStep(inSlidingImage, inSlidingImageStarterPosition, outSlidingImage, outSlidingImageFinishingPosition, transitionLength, transitionSteps, canStop);
        } else {
            clearInterval(movementInterval);
            outSlidingImage.classList.replace("active-picture", "inactive-picture");
            setInSlidingImagePosition(inSlidingImage, {left: 0, right:0, top: 0});
            document.addEventListener("keydown", backgroundChangerEventListener);
        }

    }, transitionLength/transitionSteps);
}

function timeStep(inSlidingImage, inSlidingImageStarterPosition, outSlidingImage, outSlidingImageFinishingPosition, transitionLength, transitionSteps, canStop) {
    let q = transitionLength/transitionSteps;

    console.log(inSlidingImage.style.left);
    console.log(inSlidingImage.style.right);
    console.log(inSlidingImage.style.top);

    if(inSlidingImageStarterPosition.left !== 0) {
        inSlidingImage.style.left = inSlidingImageStarterPosition.left > 0 ?
            (Number.parseInt(inSlidingImage.style.left)-100/q + "%") :
            (Number.parseInt(inSlidingImage.style.left)+100/q + "%");
    }
    if(inSlidingImageStarterPosition.right !== 0){
        inSlidingImage.style.right = inSlidingImageStarterPosition.right > 0 ?
            (Number.parseInt(inSlidingImage.style.right)-100/q + "%") :
            (Number.parseInt(inSlidingImage.style.right)+100/q + "%");
    }
    if(inSlidingImageStarterPosition.top !== 0){
        inSlidingImage.style.top = inSlidingImageStarterPosition.top > 0 ?
            (Number.parseInt(inSlidingImage.style.top)-100/q + "%") :
            (Number.parseInt(inSlidingImage.style.top)+100/q + "%");
    }

    if(outSlidingImageFinishingPosition.left !== 0){
        outSlidingImage.style.left = outSlidingImageFinishingPosition.left > 0 ?
            (Number.parseInt(outSlidingImage.style.left)+100/q + "%") :
            (Number.parseInt(outSlidingImage.style.left)-100/q + "%");
    }
    if(outSlidingImageFinishingPosition.right !== 0) {
        outSlidingImage.style.right = outSlidingImageFinishingPosition.right > 0 ?
            (Number.parseInt(outSlidingImage.style.right)+100/q + "%") :
            (Number.parseInt(outSlidingImage.style.right)-100/q + "%");
    }
    if(outSlidingImageFinishingPosition.top !== 0){
        outSlidingImage.style.top = outSlidingImageFinishingPosition.top > 0 ?
            (Number.parseInt(outSlidingImage.style.top)+100/q + "%") :
            (Number.parseInt(outSlidingImage.style.top)-100/q + "%");
    }

    if (Number.parseInt(inSlidingImage.style.top) === 0 && inSlidingImageStarterPosition.top !== 0) canStop.canStop = true;
    if (Number.parseInt(inSlidingImage.style.left) === 0 && inSlidingImageStarterPosition.left !== 0) canStop.canStop = true;
    if (Number.parseInt(inSlidingImage.style.right) === 0 && inSlidingImageStarterPosition.right !== 0) canStop.canStop = true;
}

function getSlidingPicturePositions(keyDown) {
    let inSlidingImageStarterPosition = {
        left: 0,
        right: 0,
        top: 0
    };

    let outSlidingImageFinishingPosition = {
        left: 0,
        right: 0,
        top: 0
    };

    if (keyDown === "ArrowLeft") {

        inSlidingImageStarterPosition.left = 100;
        inSlidingImageStarterPosition.right = -100;

        outSlidingImageFinishingPosition.left = -100;
        outSlidingImageFinishingPosition.right = 100;

    } else if (keyDown === "ArrowRight") {

        inSlidingImageStarterPosition.left = -100;
        inSlidingImageStarterPosition.right = 100;

        outSlidingImageFinishingPosition.left = 100;
        outSlidingImageFinishingPosition.right = -100;

    } else if (keyDown === "ArrowDown") {

        inSlidingImageStarterPosition.top = -100;

        outSlidingImageFinishingPosition.top = 100;

    } else if (keyDown === "ArrowUp") {

        inSlidingImageStarterPosition.top = 100;

        outSlidingImageFinishingPosition.top = -100;

    }

    return {
        inSlidingImageStarterPosition: inSlidingImageStarterPosition,
        outSlidingImageFinishingPosition: outSlidingImageFinishingPosition
    }
}

function getPictureElements() {
    let outSlidingImage = document.getElementsByClassName("active-picture")[0];
    let inSlidingImage;
    let updatingImage;

    if (outSlidingImage === document.getElementById("background-pictures").firstElementChild ||
        outSlidingImage === document.getElementById("background-pictures").lastElementChild) {

        inSlidingImage = document.getElementsByClassName("inactive-picture")[0];
        updatingImage = document.getElementsByClassName("inactive-picture")[1];
    } else {

        inSlidingImage = document.getElementsByClassName("inactive-picture")[1];
        updatingImage = document.getElementsByClassName("inactive-picture")[0];
    }

    return {
        outSlidingImage: outSlidingImage,
        inSlidingImage: inSlidingImage,
        updatingImage: updatingImage
    }
}

function updateImage(imageToUpdate) {
    const Http = new XMLHttpRequest();
    const url='http://localhost:60002/picture';
    Http.open("GET", url);
    Http.setRequestHeader("Access-Control-Allow-Origin", "http:localhost:60002");
    Http.send();
    Http.onreadystatechange=(e)=>{
        console.log(Http.responseText);
        imageToUpdate.src = Http.responseText;
    };
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
        changeGameState(JSON.parse(Http.responseText));
    }
}

function changeGameState(newGameState) {

    const squares = document.getElementsByClassName("square");

    for (let i = 0; i < squares.length; i++) {
        if (newGameState.boardSetup[i] === 0) {
            squares[i].innerText = "\xa0";
            changeColors(squares[i], 0);
        } else {
            squares[i].innerText = newGameState.boardSetup[i];
            changeColors(squares[i], newGameState.boardSetup[i]);
        }
    }
    document.getElementById("score-text").innerText = newGameState.score;
}

function changeColors(square, value) {
    if (value >= 64) {
        square.style.backgroundColor = '#ff4d09';
    } else if (value < 64 && value >= 32) {
        square.style.backgroundColor = '#ff9c23';
    } else if (value < 32 && value >= 16) {
        square.style.backgroundColor = '#ffc33b';
    } else if (value < 16 && value >= 8) {
        square.style.backgroundColor = '#ffd65b';
    } else if (value < 8 && value >= 4) {
        square.style.backgroundColor = '#fff180';
    } else if (value < 4 && value >= 2) {
        square.style.backgroundColor = '#fff4ba';
    } else {
        square.style.backgroundColor = '#fff';
    }
}

init();