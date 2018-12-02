import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Pattern } from '../pattern/pattern';
import { PatternLambdaService } from '../services/pattern-lambda.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchFormControl = new FormControl('');

  constructor(private patternLambdaService: PatternLambdaService) { }

  ngOnInit() {
  }

  searchButtonClick() {
    const query = this.searchFormControl.value;
    console.log("Search button was clicked with [" + query + "]!");
    this.patternLambdaService.searchPattern(query);
  }

}
