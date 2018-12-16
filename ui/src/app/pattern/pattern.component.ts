import { Component, OnInit, Input } from '@angular/core';
import { Pattern } from './pattern';

@Component({
    selector: 'app-pattern',
    templateUrl: './pattern.component.html',
    styleUrls: ['./pattern.component.css']
})
export class PatternComponent implements OnInit {

    @Input() pattern: Pattern;

    constructor() { }

    ngOnInit() {
        this.pattern = {
            title: 'Pattern Title',
            description: 'Search for a pattern'
        };
    }

    setPattern(pattern: Pattern): void {
        console.log('Updating pattern');
        this.pattern = pattern;
        console.log(this.pattern);
    }

}
