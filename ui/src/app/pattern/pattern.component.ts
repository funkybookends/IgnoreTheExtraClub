import { Component, OnInit } from '@angular/core';
import { Pattern } from './pattern';

@Component({
	selector: 'app-pattern',
	templateUrl: './pattern.component.html',
	styleUrls: ['./pattern.component.css']
})
export class PatternComponent implements OnInit {

	pattern: Pattern = {
		title: "Pattern Title",
		pattern_type: "FOUR_HANDED_SITESWAP",
		numJugglers: 2,
		numHands: 4,
		numObjects: 7,
		prime: true,
		grounded: true,
		siteswap: "975",
		names: {"en": "Holy Grail"},
		shortDescriptions: null,
		longDescriptions: {"en": "Holy grail is an awesome pattern"},
		causalDiagramSvg: null
	}

	constructor() { }

	ngOnInit() {
	}

}
