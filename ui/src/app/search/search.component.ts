import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Pattern } from '../pattern/pattern';
import { PatternLambdaService } from '../services/pattern-lambda.service';
import { PatternComponent } from '../pattern/pattern.component';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchFormControl = new FormControl('');

  constructor(private patternLambdaService: PatternLambdaService,
    private patternComponent: PatternComponent) { }

  ngOnInit() {
  }

  searchButtonClick() {
    const query = this.searchFormControl.value;
    const response = this.patternLambdaService.searchPattern(query);
    response.subscribe((pattern: Pattern)  => this.patternComponent.setPattern(pattern));
  }

}
