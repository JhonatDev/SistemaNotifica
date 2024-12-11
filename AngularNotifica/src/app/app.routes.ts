import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { PrincipalComponent } from './components/principal/principal.component';
import { AdminlistComponent } from './components/admin/adminlist/adminlist.component';
import { AdmindetalhesComponent } from './components/admin/admindetalhes/admindetalhes.component';
import { TicketshowComponent } from './components/ticketshow/ticketshow.component';
import { CriarUsuarioComponent } from './components/criar-usuario/criar-usuario.component'; // Importando o componente Criar Usuário

export const routes: Routes = [
    { path: "", redirectTo: "login", pathMatch: "full" },
    { path: "login", component: LoginComponent },
    { path: "teste", component: TicketshowComponent },
    { path: "criar-usuario", component: CriarUsuarioComponent }, // Rota direta para Criar Usuário
    {
        path: "admin",
        component: PrincipalComponent,
        children: [
            { path: "principal", component: AdminlistComponent },
            { path: "principal/cancelados", component: AdminlistComponent },
            { path: "principal/pendentes", component: AdminlistComponent },
            { path: "principal/andamento", component: AdminlistComponent },
            { path: "principal/concluidos", component: AdminlistComponent },
            { path: "criar-usuario", component: CriarUsuarioComponent }, // Rota dentro do contexto admin
        ],
    },
    {
        path: "aluno",
        component: PrincipalComponent,
        children: [
            { path: "principal", component: AdminlistComponent },
            { path: "principal/pendentes", component: AdminlistComponent },
            { path: "principal/andamento", component: AdminlistComponent },
            { path: "principal/concluidos", component: AdminlistComponent },
        ],
    },
];
