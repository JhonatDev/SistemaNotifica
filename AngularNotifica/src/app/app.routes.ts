import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { PrincipalComponent } from './components/principal/principal.component';
import { AdminlistComponent } from './components/admin/adminlist/adminlist.component';
import { AdmindetalhesComponent } from './components/admin/admindetalhes/admindetalhes.component';
import { TicketshowComponent } from './components/ticketshow/ticketshow.component';
import { tick } from '@angular/core/testing';
import { authRouteGuardGuard } from './guard/auth-route-guard.guard';


export const routes: Routes = [
    {path:"", redirectTo:"login", pathMatch:"full"},
    {path:"login", component:LoginComponent},
    {path:"teste", component:TicketshowComponent},
    {path:"admin", component:PrincipalComponent, canActivate: [authRouteGuardGuard], children:[
        {path:"principal", component:AdminlistComponent},
        {path:"principal/cancelados", component:AdminlistComponent},
        {path:"principal/pendentes", component:AdminlistComponent},
        {path:"principal/andamento", component:AdminlistComponent},
        {path:"principal/concluidos", component:AdminlistComponent},
    ]},
    {path:"aluno", component:PrincipalComponent, canActivate: [authRouteGuardGuard], children:[
        {path:"principal", component:AdminlistComponent},
        {path:"principal/pendentes", component:AdminlistComponent},
        {path:"principal/andamento", component:AdminlistComponent},
        {path:"principal/concluidos", component:AdminlistComponent},
    ]},

];
