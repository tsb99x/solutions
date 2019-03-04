'use strict';

/* === IMPORTS === */

let readline = require ('readline');
let fs = require ('fs');

/* === SUPPORT === */

Array .prototype .chooseRandom = function ( ) {
	return this [ Math .floor (Math .random ( ) * this .length) ];
};

let toUpperCase = (word) => {
	word .toUpperCase ( );
};

let selectWords = (wordCount, words) => {
	let result = [ ];
	for (let i = 0; i < wordCount; i++)
		result .push (words .chooseRandom ( )); // todo check for really different words!
	return result;
};

let inputListener = (difficultyLevel) => { // TODO check if str is number in 1-5!
	let wordLengthByDifficulty = [
		[ 4, 5 ], // very easy, 0
		[ 6, 7, 8 ], // easy, 1
		[ 9, 10 ], // average, 2
		[ 11, 12, 13 ], // hard, 3
		[ 14, 15 ] // very hard, 4
	];
	
	let wordCountByDifficulty = [
		[ 5, 6 ], // very easy, 0
		[ 7, 8 ], // easy, 1
		[ 9, 10, 11 ], // average, 2
		[ 12, 13 ], // hard, 3
		[ 14, 15 ] // very hard, 4
	];
	
	let wordLength = wordLengthByDifficulty [ difficultyLevel - 1 ] .chooseRandom ( );
	let wordCount = wordCountByDifficulty [ difficultyLevel - 1 ] .chooseRandom ( );
	
	let words = fs .readFileSync ('enable1.txt', 'utf-8')
	                    .split ('\r\n')
						.filter ((word) => word .length === wordLength);

	let selectedWords = selectWords (wordCount, words) .map ((word) => word .toUpperCase ( ));
	let answer = selectedWords .chooseRandom ( );
	let tries = 4;
	
	selectedWords .forEach ((word) => console .log (word));
	
	rl .question (`Guess (${tries} left) > `, guesser (answer, tries));
};

let guesser = (answer, tries) => (str) => {
	let goal = answer .split ('');
	let input = str .toUpperCase ( ) .split ('');
	let score = input .map ((letter, index) => letter === goal [ index ] ? 1 : 0)
	                  .reduce ((sum, letterScore) => sum + letterScore);

	console .log (`${score}/${goal .length} correct`);
	if (score === goal .length) {
		console .log ('You win!');
		rl .close ( );
	} else {
		if (tries - 1 === 0) {
			console .log ('You lose!');
			rl .close ( );
		} else {
			rl .question (`Guess (${tries - 1} left) > `, guesser (answer, tries - 1));
		}
	}
};

/* === PROGRAM === */

let rl = readline .createInterface (process .stdin, process .stdout);

rl .question ('Difficulty (1 - 5) > ', inputListener);
