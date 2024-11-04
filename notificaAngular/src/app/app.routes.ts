
import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { PrincipalComponent } from './components/principal/principal.component';
import path from 'path';
import { Component } from '@angular/core';
import { AdminlistComponent } from './components/admin/adminlist/adminlist.component';
import { AdmindetalhesComponent } from './components/admin/admindetalhes/admindetalhes.component';


export const routes: Routes = [
    {path:"", redirectTo:"login", pathMatch:"full"},
    {path:"login", component:LoginComponent},
    {path:"admin", component:PrincipalComponent, children:[
        {path:"principal", component:AdminlistComponent},
        {path:"principal/cancelados", component:AdminlistComponent},
        {path:"principal/pendentes", component:AdminlistComponent},
        {path:"principal/andamento", component:AdminlistComponent},
        {path:"principal/concluidos", component:AdminlistComponent},
    ]},
    {path:"aluno", component:PrincipalComponent, children:[
        {path:"principal", component:AdminlistComponent},
        {path:"principal/pendentes", component:AdminlistComponent},
        {path:"principal/andamento", component:AdminlistComponent},
        {path:"principal/concluidos", component:AdminlistComponent},
    ]},

];
