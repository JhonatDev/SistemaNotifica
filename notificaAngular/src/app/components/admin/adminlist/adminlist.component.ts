import { Component, inject, Input, OnInit, TemplateRef, TrackByFunction, viewChild, ViewChild } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { TicktsService } from '../../../service/tickts/tickts.service';
import { Tickts } from '../../../models/tickts/tickts';
import { AdmindetalhesComponent } from '../admindetalhes/admindetalhes.component';
import { SharedService } from '../../../service/shared.service';


@Component({
  selector: 'app-adminlist',
  standalone: true,
  imports: [RouterLink, CommonModule, MdbModalModule, AdmindetalhesComponent	],
  templateUrl: './adminlist.component.html',	
  styleUrls: ['./adminlist.component.css']
})
export class AdminlistComponent implements OnInit {
  // Serviço de modal injetado
  modalService = inject(MdbModalService);

  // Referência do template de modal
  @ViewChild('modalTicketDetails') modalTicketDetails!: TemplateRef<any>;


  // Referência para manipular a modal aberta
  modalRef!: MdbModalRef<any>;

  // Função de rastreamento para ngFor, rastreando itens por ID
  trackById: TrackByFunction<Tickts> = (_, item) => item.id;

  // Lista de tickets
  ticketsList: Tickts[] = [];

  // Objeto para edição de ticket
  ticketEdit: Tickts = new Tickts();

  funcao!: any;
  constructor(
    private ticktsService: TicktsService,
    private SharedService: SharedService,
    private router: Router
  ) {}

  // Login do usuário e tipo de usuário
  login!: string;
  tipoDeUsuario!: string;
  tipoSite!: string;
  
  

  ngOnInit(): void {
    this.login = this.SharedService.ultimoLogin;
    this.tipoDeUsuario = this.SharedService.tipoUsuario;

    // Captura o URL atual
    const url = window.location.href;

    // Define o tipo de site com base no URL
    switch (true) {
      case url.includes('principal/cancelados'):
        this.tipoSite = 'cancelados';
        break;
      case url.includes('principal/pendentes'):
        this.tipoSite = 'pendentes';
        break;
      case url.includes('principal/andamento'):
        this.tipoSite = 'andamento';
        break;
      case url.includes('principal/concluidos'):
        this.tipoSite = 'concluidos';
        break;
      default:
        this.tipoSite = 'principal';
    }

    // Carrega a lista de tickets após definir o tipo de site
    this.onListar();
    console.log(this.ticketsList); // Verificação de dados
}



  // Listar tickets
  onListar() {
    let tipoDeLista;

    // Função auxiliar para determinar o status
    const getStatusByTipoSite = (tipoSite: string) => {
        switch (tipoSite) {
            case 'cancelados':
                return 'CANCELADO';
            case 'pendentes':
                return 'ABERTO';
            case 'andamento':
                return 'EM_ANDAMENTO';
            case 'concluidos':
                return 'SOLUCIONADO';
            default:
                return null; // ou um valor padrão se necessário
        }
    };

    // Escolhe o método de listagem com base no tipo de site
    const status = getStatusByTipoSite(this.tipoSite);

    if (this.tipoDeUsuario === 'ADMIN') {
        if (status) {
            tipoDeLista = this.ticktsService.listarPorStatus(status);
            console.log(`Listando tickets ${status} para ${this.tipoDeUsuario}`);
        } else {
            tipoDeLista = this.ticktsService.listar();
            console.log('Listando todos os tickets para Admin');
        }
    } else if (this.tipoDeUsuario === 'Usuarios') { // Corrigido para fora do bloco ADMIN
        if (status) {
            tipoDeLista = this.ticktsService.listarPorUsuarioStatus(this.login, status);
            console.log(`Listando tickets ${status} para ${this.tipoDeUsuario}`);
        } else {
            tipoDeLista = this.ticktsService.listarPorUsuario(this.login);
            console.log('Listando todos os tickets para Usuários');
        }
    } else {
        console.log('Tipo de usuário desconhecido, sem ações específicas.');
        return; // Sai da função se o tipo de usuário não for reconhecido
    }

    // Realiza a assinatura e manipula o retorno da listagem
    tipoDeLista?.subscribe({
        next: (response) => {
            console.log('Listagem bem-sucedida:', response);
            this.ticketsList = response;
        },
        error: (error) => {
            console.error('Erro na listagem:', error);
        }
    });
}



  

  // Abrir modal para criação de um novo ticket
  onCriar() {
    this.ticketEdit = new Tickts(); // Resetar o objeto de edição
    this.funcao = 'Criar';
    this.modalRef = this.modalService.open(this.modalTicketDetails);

  }

  // Fechar modal
  onClose() {
    if (this.modalRef) {
      this.modalRef.close();
    }
    this.onListar();
  }

  // Atualizar lista ao retornar da criação ou edição
  retornar(event: any) {
    this.onListar();
    this.onClose();
    this.funcao = '';
  }

  // Editar um ticket
  edit(ticket: Tickts) {
    console.log('Editar ticket:', ticket);
    this.ticketEdit = { ...ticket }; // Clonar o ticket para evitar mutação
    this.funcao = 'Atualizar';
    this.modalRef = this.modalService.open(this.modalTicketDetails); // Abrir modal com ticket para edição
  }

  deletar(ticket: Tickts) {
    console.log('Excluir ticket:', ticket);
    this.ticktsService.deletar(ticket.id).subscribe({
        next: (response) => {
            console.log('Exclusão bem-sucedida:', response);
            alert('Ticket excluído com sucesso!');
            this.onListar(); // Atualizar lista após exclusão
        },
        error: (error) => {
            console.error('Erro na exclusão:', error);
            alert('Erro ao excluir ticket! ' + (error.error?.message || error.message));
        }
    });
  }

  // pegar ticket
  pegarTicket(ticket: Tickts) {
    console.log('Pegar ticket:', ticket);
    this.ticktsService.iniciar(ticket.id, this.login).subscribe({
        next: (response) => {
            console.log('Ticket pego com sucesso:', response);
            alert('Ticket pego com sucesso!');
            this.onListar(); // Atualizar lista após pegar ticket
        },
        error: (error) => {
            console.error('Erro ao pegar ticket:', error);
            alert('Erro ao pegar ticket! ' + (error.error?.message || error.message));
        }
    });
  }

  // finalizar ticket
  finalizarTicket(ticket: Tickts) {
    console.log('Finalizar ticket:', ticket);
    this.ticktsService.finalizar(ticket.id).subscribe({
        next: (response) => {
            console.log('Ticket finalizado com sucesso:', response);
            alert('Ticket finalizado com sucesso!');
            this.onListar(); // Atualizar lista após finalizar ticket
        },
        error: (error) => {
            console.error('Erro ao finalizar ticket:', error);
            alert('Erro ao finalizar ticket! ' + (error.error?.message || error.message));
        }
    });
  }

  // reabrir Ticket 
  reabrirTicket(ticket: Tickts) {
    console.log('Sair ticket:', ticket);
    this.ticktsService.reabrir(ticket.id).subscribe({
        next: (response) => {
            console.log('Ticket saiu com sucesso:', response);
            alert('Ticket saiu com sucesso!');
            this.onListar(); // Atualizar lista após sair ticket
        },
        error: (error) => {
            console.error('Erro ao sair ticket:', error);
            alert('Erro ao sair ticket! ' + (error.error?.message || error.message));
        }
    });

  }

  //cancelar ticket
  cancelarTicket(ticket: Tickts) {
    console.log('Cancelar ticket:', ticket);
    this.ticktsService.cancelar(ticket.id).subscribe({
        next: (response) => {
            console.log('Ticket cancelado com sucesso:', response);
            alert('Ticket cancelado com sucesso!');
            this.onListar(); // Atualizar lista após cancelar ticket
        },
        error: (error) => {
            console.error('Erro ao cancelar ticket:', error);
            alert('Erro ao cancelar ticket! ' + (error.error?.message || error.message));
        }
    });
  }
  
}
