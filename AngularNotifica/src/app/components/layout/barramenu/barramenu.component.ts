import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { SharedService } from '../../../service/shared.service';
@Component({
  selector: 'app-barramenu',
  standalone: true,
  imports: [MdbCollapseModule, RouterLink, CommonModule],
  templateUrl: './barramenu.component.html',
  styleUrl: './barramenu.component.scss'
})
export class BarramenuComponent {
  tipoDeUsuario!: string;

  constructor(private SharedService: SharedService) {
    this.tipoDeUsuario = this.SharedService.tipoUsuario;
  }




  

}
