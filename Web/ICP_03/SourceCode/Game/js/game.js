const CHOICES = ['Rock','Paper','Scissors'];
const result_status = document.getElementById("message");

/**
 * Gets the computer score by random
 * @returns {string}
 */
function getComputerChoice(){
 var choice = Math.floor(Math.random() * 3);
 return CHOICES[choice];
}


/**
 * Check if the user wins
 */
function win(myChoice, computerChoice){
    result_status.innerHTML = `${myChoice}  beats  ${computerChoice} . You won !!`;
}

/**
 * Check if the user loses
 * @param myChoice
 * @param computerChoice
 */
function lose(myChoice, computerChoice){
    result_status.innerHTML = `${computerChoice} beats ${myChoice}. You lost !!`;
}


/**
 * Check if the is a draw
 * @param myChoice
 * @param computerChoice
 */
function draw(myChoice, computerChoice){
    result_status.innerHTML = `${(computerChoice)} ties ${(myChoice)}. It is a draw !!`;
}


/**
 * Decides is the game is win,lose, or draw
 */
function play(myChoice){
    console.log("myChoice is " + myChoice)
    var computerChoice = getComputerChoice();
    document.getElementById("user").innerHTML="User"+  "<br>" + myChoice;
    document.getElementById("computer").innerHTML="Computer" + "<br>" + computerChoice;
    result_status.innerHTML="";
    switch (myChoice + computerChoice) {
        case "RockScissors":
        case "PaperRock":
        case "ScissorsPaper":
            win(myChoice, computerChoice);
            break;
        case "RockRock":
        case "PaperPaper":
        case "ScissorsScissors":
            draw(myChoice, computerChoice);
            break;
        case "RockPaper":
        case "PaperScissors":
        case "ScissorsRock":
            lose(myChoice, computerChoice);
    }
}


/**
 *  Main function which is triggered once the script is called
 */
function main()
{
    result_status.innerHTML="";
    document.getElementById("user").innerHTML="User ";
    document.getElementById("computer").innerHTML="Computer ";
    // the event listener for a click on the rock button
    document.getElementById('rock').addEventListener('click', function () {
        console.log("rock");
        play("Rock");
    })

    // the event listener for a click on the paper button
    document.getElementById('paper').addEventListener('click', function () {
        console.log("paper");
        play("Paper");
    })

    // the event listener for a click on the scissors button
    document.getElementById('scissors').addEventListener('click', function () {
        console.log("scissors");
        play("Scissors");
    })
}



main();