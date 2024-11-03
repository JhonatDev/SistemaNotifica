
import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { PrincipalComponent } from './components/principal/principal.component';
import path from 'path';
import { Component } from '@angular/core';
import { PessoaslistComponent } from './components/pessoas/pessoaslist/pessoaslist.component';
import { PessoasdetailsComponent } from './components/pessoas/pessoasdetails/pessoasdetails.component';
import { AdminlistComponent } from './components/admin/adminlist/adminlist.component';
import { AdmindetalhesComponent } from './components/admin/admindetalhes/admindetalhes.component';


export const routes: Routes = [
    {path:"", redirectTo:"login", pathMatch:"full"},
    {path:"login", component:LoginComponent},
    {path:"admin", component:PrincipalComponent, children:[
        {path:"pessoas", component:PessoaslistComponent},
        {path:"pessoas/new", component:PessoasdetailsComponent},
        {path:"pessoas/edit/:id", component:PessoasdetailsComponent},
        {path:"principal", component:AdminlistComponent},
        {path:"principal/criar", component:AdmindetalhesComponent},
        {path:"principal/editar/:id", component:AdmindetalhesComponent}
    ]},

];
