import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BarramenuComponent } from "../layout/barramenu/barramenu.component";

@Component({
  selector: 'app-principal',
  standalone: true,
  imports: [RouterOutlet, BarramenuComponent],
  templateUrl: './principal.component.html',
  styleUrl: './principal.component.scss'
})
export class PrincipalComponent {

}
