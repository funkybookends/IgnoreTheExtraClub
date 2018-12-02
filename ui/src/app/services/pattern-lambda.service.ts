import { Injectable } from '@angular/core';
import { Pattern } from '../pattern/pattern';

@Injectable({
  providedIn: 'root'
})
export class PatternLambdaService {

  constructor() { }

  searchPattern(pattern: String) {
    console.log("pattern Service searching for ["+pattern+"]");
  }
}
