import { Component, inject, Input, Output, OnInit, TemplateRef, ViewChild, EventEmitter } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { TicktsService } from '../../service/tickts/tickts.service';
import { Tickts } from '../../models/tickts/tickts';
import { SubTipoProblemaService } from '../../service/SubTipoProblema/sub-tipo-problema.service';
import { ImageUploadService } from '../../service/image-service.service';
import { environment } from '../../../environments/environment';

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
  styleUrls: ['./ticketshow.component.scss']
})
export class TicketshowComponent implements OnInit {
  @Input() TicketList!: Tickts;
  @Input() tipoDeUsuario!: string;
  @Input() login!: string;
  @Output() retorno = new EventEmitter<any>();

  //link para o servidor
  servidor = environment.SERVIDOR;

  selectedFile: File | null = null;
  modalService = inject(MdbModalService);
  @ViewChild('modalTicketShow') modalTicketShow!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;
  SubTipoProblemalist: any[] = [];
  selectedImage: File | null = null;
  showModal: boolean = false; // Variável para controlar o modal
  Modalsair: boolean = false; // Variável para controlar o modal
  alert!: string;

  constructor(
    private ticktsService: TicktsService,
    private subTipoProblemaService: SubTipoProblemaService,
    private imageUploadService: ImageUploadService
  ) { }

  ngOnInit(): void {

  }

  edit(ticket: Tickts) {
    console.log('Editar ticket:', ticket);
    // Lógica para edição do ticket
  }

  onError(event: any) {
    event.target.src = this.servidor + '/image/download/Untitled.png';
  }

  finalizarTicket(ticket: Tickts) {
    console.log('Finalizar ticket:', ticket);
    this.ticktsService.finalizar(ticket.id).subscribe({
      next: (response) => {
        console.log('Ticket finalizado com sucesso:', response);
        this.alert = 'Ticket finalizado com sucesso!';
        this.showModal = true;
        this.Modalsair = true;
      },
      error: (error) => {
        console.error('Erro ao finalizar ticket:', error);
        this.alert = 'Erro ao finalizar ticket! ' + (error.error?.message || error.message);
        this.showModal = true;
      }
    });
  }

  cancelarTicket(ticket: Tickts) {
    console.log('Cancelar ticket:', ticket);
    this.ticktsService.cancelar(ticket.id).subscribe({
      next: (response) => {
        console.log('Ticket cancelado com sucesso:', response);
        this.alert = 'Ticket cancelado com sucesso!';
        this.showModal = false;
        this.Modalsair = true;
      },
      error: (error) => {
        console.error('Erro ao cancelar ticket:', error);
        this.alert = 'Erro ao cancelar ticket! ' + (error.error?.message || error.message);
        this.showModal = true;
      }
    });
  }

  reabrirTicket(ticket: Tickts) {
    console.log('Reabrir ticket:', ticket);
    this.ticktsService.reabrir(ticket.id).subscribe({
      next: (response) => {
        console.log('Ticket reaberto com sucesso:', response);
        this.alert = 'Ticket reaberto com sucesso!';
        this.showModal = true;
        this.Modalsair = true;
      },
      error: (error) => {
        console.error('Erro ao reabrir ticket:', error);
        this.alert = 'Erro ao reabrir ticket! ' + (error.error?.message || error.message);
        this.showModal = true;
      }
    });
  }

  pegarTicket(ticket: Tickts) {
    console.log('Pegar ticket:', ticket);
    this.ticktsService.iniciar(ticket.id, this.login).subscribe({
      next: (response) => {
        console.log('Ticket pego com sucesso:', response);
        this.alert = 'Ticket pego com sucesso!';
        this.showModal = true;
        this.Modalsair = true;
      },
      error: (error) => {
        console.error('Erro ao pegar ticket:', error);
        this.alert = 'Erro ao pegar ticket! ' + (error.error?.message || error.message);
      }
    });
  }

  deletar(ticket: Tickts) {
    console.log('Excluir ticket:', ticket);
    this.ticktsService.deletar(ticket.id).subscribe({
      next: (response) => {
        console.log('Exclusão bem-sucedida:', response);
        this.alert = 'Ticket excluído com sucesso!';
        this.showModal = true;
        this.Modalsair = true;
      },
      error: (error) => {
        console.error('Erro na exclusão:', error);
        this.alert = 'Erro ao excluir ticket! ' + (error.error?.message || error.message);
        this.showModal = true;
      }
    });
  }

  onClose() {
    this.modalRef.close();
  }

  // Fecha o modal
  closeModal(): void {
    this.showModal = false;
    if (this.Modalsair == true) {
      this.retorno.emit();
    }
  }
}
