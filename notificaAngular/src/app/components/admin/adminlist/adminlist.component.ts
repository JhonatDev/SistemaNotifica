import { Component, inject, Input, OnInit, TemplateRef, TrackByFunction, viewChild, ViewChild } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { TicktsService } from '../../../service/tickts/tickts.service';
import { Tickts } from '../../../models/tickts/tickts';
import { AdmindetalhesComponent } from '../admindetalhes/admindetalhes.component';


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
    private router: Router
  ) {}

  ngOnInit(): void {
    this.onListar(); // Carregar lista de tickets ao inicializar o componente
    console.log(this.ticketsList); // Verificação de dados
  }

  // Listar tickets
  onListar() {
    this.ticktsService.listar().subscribe({
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

  // Deletar um ticket
  deletar(ticket: Tickts) {
    console.log('Excluir ticket:', ticket);
    /*this.ticktsService.deletar(ticket.id).subscribe({
      next: () => {
        console.log('Ticket excluído com sucesso');
        this.onListar(); // Atualizar lista após exclusão
      },
      error: (error) => {
        console.error('Erro ao excluir ticket:', error);
      }
    });*/
  }
}
