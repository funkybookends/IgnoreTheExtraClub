import { Component } from '@angular/core';
import { PatternComponent } from './pattern/pattern.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [PatternComponent]
})
export class AppComponent {

}
