import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'cadastro-pessoas';

  ngOnInit() {
    // Remove o hash "#iss=..." da URL caso esteja presente
    if (window.location.hash.includes('iss=')) {
      window.history.replaceState(null, '', window.location.pathname);
    }
  }
}
