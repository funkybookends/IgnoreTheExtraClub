import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
    searchFormControl = new FormControl('');

  constructor() { }

  ngOnInit() {
  }

  searchButtonClick() {
    const query = this.searchFormControl.value;
    console.log("Search button was clicked with [" + query + "]!");
  }

}
