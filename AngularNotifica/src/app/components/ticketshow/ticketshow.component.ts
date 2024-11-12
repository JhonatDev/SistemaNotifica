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
  // Objeto do novo ticket
  @Input() TicketList!: Tickts;
  @Input() tipoDeUsuario!: any;
  @Input() login!: string;
  @Output() retorno = new EventEmitter<any>();

  selectedFile: File | null = null;

  // Referência ao serviço de modal
  modalService = inject(MdbModalService);

  // Referência do template da modal no HTML
  @ViewChild('modalTicketShow') modalTicketShow!: TemplateRef<any>;

  // Referência da modal para conseguirmos fechar
  modalRef!: MdbModalRef<any>;

  SubTipoProblemalist: any[] = [];
  selectedImage: File | null = null;

  constructor(
    private ticktsService: TicktsService,
    private subTipoProblemaService: SubTipoProblemaService,
    private SharedService: SharedService,
    private imageUploadService: ImageUploadService,
  ) { }

  ngOnInit(): void {
    document.documentElement.style.setProperty('--mdb-body-bg', '#242424');//muda a cor do fundo
  }


 // Método para editar o ticket
 edit(ticket: Tickts) {
  // Lógica para edição (abrir modal com detalhes do ticket para edição)
  console.log('Editar ticket:', ticket);
}

onError(event: any) {
  event.target.src = 'http://localhost:8080/image/download/Untitled.png';
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
  this.ticktsService.iniciar(ticket.id,this.login).subscribe({
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

//atualizar ticket


// Fechar modal
onClose() {
  this.modalRef.close();
}
}

