import { Component, inject, Input, Output, OnInit, TemplateRef, TrackByFunction, ViewChild, EventEmitter } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { TicktsService } from '../../service/tickts/tickts.service';
import { Tickts } from '../../models/tickts/tickts';
import { SubTipoProblemaService } from '../../service/SubTipoProblema/sub-tipo-problema.service';
import { SubTipoProblema } from '../../models/SubTipoProblema/subtipoproblema';
import { SharedService } from '../../service/shared.service';
import { ImageUploadService } from '../../service/image-service.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ticketshow',
  standalone: true,
  templateUrl: './ticketshow.component.html',
  imports: [
    RouterLink,
    CommonModule,
    MdbModalModule,
    FormsModule
  ],
  styleUrls: ['./ticketshow.component.css']
})
export class TicketshowComponent {
  @Input() TicketList!: Tickts;
  @Input() funcao!: any;
  @Output() retorno = new EventEmitter<any>();

  modalService = inject(MdbModalService);
  @ViewChild('Ticketshow') Ticketshow!: TemplateRef<any>;

  modalRef!: MdbModalRef<any>;

  constructor(
    private readonly ticktsService: TicktsService,
  ) { }

  // Método para editar o ticket
  edit(ticket: Tickts) {
    // Lógica para edição (abrir modal com detalhes do ticket para edição)
    console.log('Editar ticket:', ticket);
  }

  // Método para finalizar o ticket
  finalizarTicket(ticket: Tickts) {
    console.log('Finalizar ticket:', ticket);
    this.ticktsService.finalizar(ticket.id).subscribe({
      next: (response) => {
        console.log('Ticket finalizado com sucesso:', response);
        alert('Ticket finalizado com sucesso!');
        this.retorno.emit();
      },
      error: (error) => {
        console.error('Erro ao finalizar ticket:', error);
        alert('Erro ao finalizar ticket! ' + (error.error?.message || error.message));
      }
    });
  }

  // Método para cancelar o ticket
  cancelarTicket(ticket: Tickts) {
    console.log('Cancelar ticket:', ticket);
    this.ticktsService.cancelar(ticket.id).subscribe({
      next: (response) => {
        console.log('Ticket cancelado com sucesso:', response);
        alert('Ticket cancelado com sucesso!');
        this.retorno.emit();
      },
      error: (error) => {
        console.error('Erro ao cancelar ticket:', error);
        alert('Erro ao cancelar ticket! ' + (error.error?.message || error.message));
      }
    });
  }

  // Método para reabrir o ticket
  reabrirTicket(ticket: Tickts) {
    console.log('Reabrir ticket:', ticket);
    this.ticktsService.reabrir(ticket.id).subscribe({
      next: (response) => {
        console.log('Ticket reaberto com sucesso:', response);
        alert('Ticket reaberto com sucesso!');
        this.retorno.emit();
      },
      error: (error) => {
        console.error('Erro ao reabrir ticket:', error);
        alert('Erro ao reabrir ticket! ' + (error.error?.message || error.message));
      }
    });
  }

  // Método para pegar o ticket
  pegarTicket(ticket: Tickts) {
    console.log('Pegar ticket:', ticket);
    this.ticktsService.iniciar(ticket.id, 'funcionarioResponsavel').subscribe({
      next: (response) => {
        console.log('Ticket pego com sucesso:', response);
        alert('Ticket pego com sucesso!');
        this.retorno.emit();
      },
      error: (error) => {
        console.error('Erro ao pegar ticket:', error);
        alert('Erro ao pegar ticket! ' + (error.error?.message || error.message));
      }
    });
  }

  // Método para excluir o ticket
  deletar(ticket: Tickts) {
    console.log('Excluir ticket:', ticket);
    this.ticktsService.deletar(ticket.id).subscribe({
      next: (response) => {
        console.log('Exclusão bem-sucedida:', response);
        alert('Ticket excluído com sucesso!');
        this.retorno.emit();
      },
      error: (error) => {
        console.error('Erro na exclusão:', error);
        alert('Erro ao excluir ticket! ' + (error.error?.message || error.message));
      }
    });
  }

  // Fechar modal
  onClose() {
    this.modalRef.close();
  }
}
