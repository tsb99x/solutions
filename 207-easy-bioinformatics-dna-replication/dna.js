'use strict';

var readline = require('readline'),
    fs = require('fs');

var rl = readline.createInterface(process.stdin, process.stdout);

var DNACodonDictionary = JSON.parse(fs.readFileSync('codon_dictionary.json', 'utf8'));

var useFunction = function(funcName) {
	return function(object) {
		return object[funcName]();
	};
};

var getComplementaryBase = function(base) { // move to dictionary?
	if (base === 'A')
		return 'T';
	if (base === 'T')
		return 'A';
	if (base === 'C')
		return 'G';
	if (base === 'G')
		return 'C';
	throw new Error('Unknown base "' + base + '" supplied!');
};

var getProteinSequence = function(DNAStrand, result) {
	if (DNAStrand.length < 3) {
		if (DNAStrand.length !== 0)
			console.log('=== WARNING! === DNA strand last codon is not completed!');
		return result;
	} else {
		var codon = DNAStrand.slice(0, 3).join('');
		return getProteinSequence(DNAStrand.slice(3), result.concat(DNACodonDictionary[codon]));
	}
};

var complementStrand = function(inputLine) {
	var inputtedStrand = inputLine // TODO move to function
		.replace(/\s+/g, '') // remove any space characters
		.split('') // split all characters
		.map(useFunction('toUpperCase')); // upper-case all
	
	var complementaryStrand = inputtedStrand.map(getComplementaryBase);

	console.log('\n=== RESULT ===')
	console.log('source strand        > ' + inputtedStrand.join(' '));
	console.log('complementary strand > ' + complementaryStrand.join(' '));
	console.log('');

	process.exit(0);
};

var convertStrandToProteins = function(inputLine) {
	var inputtedStrand = inputLine // TODO move to function
		.replace(/\s+/g, '') // remove any space characters
		.split('') // split all characters
		.map(useFunction('toUpperCase')); // upper-case all

	console.log('\n=== RESULT ===')
	console.log('source strand    > ' + inputtedStrand.join(' '));
	console.log('protein sequence > ' + getProteinSequence(inputtedStrand, []).join(' '));
	console.log('');

	process.exit(0);
};

var enzymes = [ ];

var inputEnzymes = function(inputLine) {
	if (inputLine.length === 0) {		
		rl.question('input DNA strand > ', inputStrand);
		return;
	}

	var input = inputLine.split(' ');

	enzymes.push({
		name: input[0],
		type: input[1],
		sequence: input[2].toUpperCase() // ensure upper-casing!
	});

	rl.question('input enzyme > ', inputEnzymes);
};

var strand = '';

var inputStrand = function(inputLine) {
	if (inputLine.length === 0) {
		var DNAStrand = strand
			.replace(/\d/g, '') // remove numberic characters
			.replace(/\s+/g, '') // remove any spaces
			.toUpperCase(); // ensure upper-casing!
		
		findEnzymes(DNAStrand, enzymes);
		return;
	}
	
	strand += inputLine;

	rl.question('input DNA strand > ', inputStrand);
};

var findEnzymes = function(DNAStrand, enzymes) {
	enzymes.forEach(function(enzyme) {
		var cutPosition = enzyme.sequence.indexOf('^'); // TODO move to creation and check for -1!
		var enzymeString = enzyme.sequence.replace('^', '');
		
		var enzymeStartPosition = DNAStrand.indexOf(enzymeString);
		
		while (enzymeStartPosition !== -1) {
			var enzymeEndPosition = enzymeStartPosition + enzymeString.length;
			var enzymeCutIndex = enzymeStartPosition + cutPosition;
			var previousContextNucleobases = enzymeStartPosition >= 3 ? DNAStrand.slice(enzymeStartPosition - 3, enzymeStartPosition) : '';
			var nextContextNucleobases = DNAStrand.length - enzymeEndPosition >= 3 ? DNAStrand.slice(enzymeEndPosition, enzymeEndPosition + 3) : '';

			console.log(
				enzyme.name + ' '
				+ enzymeCutIndex + ' '
				+ previousContextNucleobases + ' '
				+ '[' + enzyme.sequence.replace('^', ' ') + ']' + ' '
				+ nextContextNucleobases
			);
			enzymeStartPosition = DNAStrand.indexOf(enzymeString, enzymeEndPosition);
		}
	});
	
	process.exit(0);
};

var inputStrandForSearchingEnzymes = function(inputLine) {
	if (inputLine.length === 0) {
		var DNAStrand = strand
			.replace(/\d/g, '') // remove numberic characters
			.replace(/\s+/g, '') // remove any spaces
			.toUpperCase(); // ensure upper-casing!
		
		findPotentialEnzymes(DNAStrand);
		return;
	}
	
	strand += inputLine;

	rl.question('input DNA strand > ', inputStrandForSearchingEnzymes);
};

var findPotentialEnzymes = function(DNAStrand) { // find palindromes by length of 4 to 10
	var complementaryStrand = DNAStrand
		.split('') // split string to get array
		.map(getComplementaryBase) // map array to complementary bases
		.join(''); // and get the string back
	
	for (var i = 2; i < DNAStrand.length - 2; i++) { // no need to include two first and two last characters, as no symmetry could be found there
		var j = 2;

		while(true) {
			var substringStartIndex = i - j;
			var substringEndIndex = i + j;
			
			if (substringStartIndex < 0)
				break;
			if (substringEndIndex > DNAStrand.length - 1)
				break;
			if (j > 5)
				break;
			
			var sourceSubstring = DNAStrand.substring(substringStartIndex, substringEndIndex);
			var complementarySubstring = complementaryStrand.substring(substringStartIndex, substringEndIndex).split('').reverse().join(''); // TODO create string reverse method!
			
			/*console.log(sourceSubstring);
			console.log(complementarySubstring);*/
			
			if (sourceSubstring === complementarySubstring) {
				console.log(substringStartIndex + ' ' + sourceSubstring + ' ' + complementarySubstring);
			} else {
				break; // if it is not palindrome, then it will not be in future iterations
			}

			j++;
		}
	}
	
	process.exit(0);
};

if (process.argv.length === 2) { // TODO show help right at start!
	throw new Error('Use "--help" to see all available arguments.');
};

if (process.argv.slice(2).forEach(function(arg) {	
	if (arg === '--help') {

		console.log('');
		console.log('===============');
		console.log('===== DNA =====');
		console.log('===============');
		console.log('');
		console.log('Available modes:');
		console.log('"--comp" - build complementary strand for inputted one');
		console.log('"--prot" - convert strand codons to proteins sequence.');
		console.log('"--enz"  - find suggested enzymes in strand.');
		console.log('"--aenz" - find potential enzymes occurances in strand.');
		console.log('');
		console.log('===============');
		console.log('');
		
		process.exit(0);

	} else

	if (arg === '--comp') {

		console.log('\n================================================='); // TODO move to func
		console.log('Enter DNA strand in form of abbriviatures of DNA-bases:');
		console.log(' - [A]denine;');
		console.log(' - [T]hymin;');
		console.log(' - [C]ytosine;');
		console.log(' - [G]uanine.');
		console.log('Example: "A T G T T T C G A G G C T A A"');
		console.log('Example: "atg ttt cga ggc taa"');
		console.log('Example: "AtgTtt CgaGgc Taa"');
		console.log('=================================================\n');

		rl.question('input source DNA strand > ', complementStrand);

	} else
	
	if (arg === '--prot') {
		
		console.log('\n================================================='); // TODO move to func
		console.log('Enter DNA strand in form of abbriviatures of DNA-bases:');
		console.log(' - [A]denine;');
		console.log(' - [T]hymin;');
		console.log(' - [C]ytosine;');
		console.log(' - [G]uanine.');
		console.log('Example: "A T G T T T C G A G G C T A A"');
		console.log('Example: "atg ttt cga ggc taa"');
		console.log('Example: "AtgTtt CgaGgc Taa"');
		console.log('=================================================\n');
		
		rl.question('input source DNA strand > ', convertStrandToProteins);

	} else
	
	if (arg === '--enz') {
		
		console.log('Enter enzymes in form of "NAME TYPE SEQUENCE"');
		console.log('Where NAME is name of enzyme,');
		console.log('TYPE is blunt/sticky cut type of enzyme,');
		console.log('SEQUENCE is recognition sequence with "^" symbol as cut position.');
		console.log('End input of enzymes with blank line.')
		
		rl.question('input enzyme > ', inputEnzymes);
		
	} else
	
	if (arg === '--aenz') {
		
		console.log('Enter enzymes in form of "NAME TYPE SEQUENCE"');
		console.log('Where NAME is name of enzyme,');
		console.log('TYPE is blunt/sticky cut type of enzyme,');
		console.log('SEQUENCE is recognition sequence with "^" symbol as cut position.');
		console.log('End input of enzymes with blank line.')
		
		rl.question('input source DNA strand > ', inputStrandForSearchingEnzymes);
		
	} else

	{
		throw new Error('Unknown argument "' + arg + '"!');
	}
}));
