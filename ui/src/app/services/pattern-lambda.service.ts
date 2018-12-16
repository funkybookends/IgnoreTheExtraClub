import { Injectable } from '@angular/core';
import { Pattern } from '../pattern/pattern';
import { PatternLambdaResponse } from './pattern-lambda-response';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

const PATTERN_URL = 'http://localhost:8080/v1/pattern/';

@Injectable({
  providedIn: 'root'
})
export class PatternLambdaService {


  constructor(private http: HttpClient) { }

  getPattern(pattern: String) {
    const request = new Object();
    const options = new Object();
    const headers = new HttpHeaders();
    headers['x-api-key'] = 'derp';
    options['headers'] = headers;

    request['siteswap'] = pattern;

    return this.http.post<PatternLambdaResponse>(PATTERN_URL, request, options)
        .pipe(map(response => {
            console.log('Mapping response:');
            return this.mapResponseToPattern(response);
        }));
}

searchPattern(pattern: String) {
    console.log('pattern Service searching for [' + pattern + ']');
    return this.getPattern(pattern);
}

mapResponseToPattern(patternLambdaResponse) {
    const pattern = new Pattern();
    pattern.title = patternLambdaResponse.pattern.title;
    pattern.description = patternLambdaResponse.pattern.longDescriptions['en'];
    return pattern;
}
}
