import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { PrincipalComponent } from './components/principal/principal.component';
import { TicktslistComponent } from './components/paginas/ticktslist/ticktslist.component';
import { TicktsdetalhesComponent } from './components/paginas/ticktsdetalhes/ticktsdetalhes.component';
import { TicketshowComponent } from './components/ticketshow/ticketshow.component';
import { tick } from '@angular/core/testing';
import { authRouteGuardGuard } from './guard/auth-route-guard.guard';
import path from 'node:path';


export const routes: Routes = [
    {path:"", redirectTo:"login", pathMatch:"full"},
    {path:"login", component:LoginComponent},
    {path:"teste", component:TicketshowComponent},
    {path:"admin", component:PrincipalComponent, canActivate: [authRouteGuardGuard], children:[
        {path:"principal", component:TicktslistComponent},
        {path:"principal/cancelados", component:TicktslistComponent},
        {path:"principal/pendentes", component:TicktslistComponent},
        {path:"principal/andamento", component:TicktslistComponent},
        {path:"principal/concluidos", component:TicktslistComponent},
    ]},
    {path: "funcionario", component: PrincipalComponent, canActivate: [authRouteGuardGuard], children:[
        {path:"principal", component:TicktslistComponent},
        {path:"principal/cancelados", component:TicktslistComponent},
        {path:"principal/pendentes", component:TicktslistComponent},
        {path:"principal/andamento", component:TicktslistComponent},
        {path:"principal/concluidos", component:TicktslistComponent},
    ]},
    {path:"aluno", component:PrincipalComponent, canActivate: [authRouteGuardGuard], children:[
        {path:"principal", component:TicktslistComponent},
        {path:"principal/pendentes", component:TicktslistComponent},
        {path:"principal/andamento", component:TicktslistComponent},
        {path:"principal/concluidos", component:TicktslistComponent},
    ]},

];
