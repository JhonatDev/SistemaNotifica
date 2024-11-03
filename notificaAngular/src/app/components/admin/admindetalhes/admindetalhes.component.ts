import { Component, inject, Input, Output, OnInit, TemplateRef, TrackByFunction, ViewChild, EventEmitter } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { TicktsService } from '../../../service/tickts/tickts.service';
import { Tickts } from '../../../models/tickts/tickts';
import { SubTipoProblemaService } from '../../../service/SubTipoProblema/sub-tipo-problema.service';
import { SubTipoProblema } from '../../../models/SubTipoProblema/subtipoproblema';

@Component({
  selector: 'app-admindetalhes',
  standalone: true,
  imports: [RouterLink, CommonModule, MdbModalModule, FormsModule],
  templateUrl: './admindetalhes.component.html',
  styleUrls: ['./admindetalhes.component.css']
})
export class AdmindetalhesComponent {
  // Objeto do novo ticket
  @Input() TicketList!: Tickts;
  @Input() funcao!: any;
  @Output() retorno = new EventEmitter<any>();

  // Referência ao serviço de modal
  modalService = inject(MdbModalService);

  // Referência do template da modal no HTML
  @ViewChild('Admindetalhes') Admindetalhes!: TemplateRef<any>;

  // Referência da modal para conseguirmos fechar
  modalRef!: MdbModalRef<any>;

  SubTipoProblemalist: any[] = [];

  constructor(private ticktsService: TicktsService, private subTipoProblemaService: SubTipoProblemaService) { }

  onFileChange(event: any) {

    //salvar o imagem no banco em VARBINARY

  }

  ngOnInit(): void {
    this.listarSubTipoProblema();
  }

  //criar um novo ticket
  criarTicket() {
    console.log('Novo ticket:', this.TicketList);
    this.ticktsService.criar(this.TicketList).subscribe({
      next: (response) => {
        console.log('Criação bem-sucedida:', response);
        alert('Ticket criado com sucesso!');
        this.TicketList = new Tickts(); // Limpar o formulário
      },
      error: (error) => {
        console.error('Erro na criação:', error);
        alert('Erro ao criar ticket! ' + error.message);
      }
    });
  }

  // atualizar um ticket
  atualizarTicket() {
    console.log('Atualizar ticket:', this.TicketList);
    this.ticktsService.atualizar(this.TicketList.id, this.TicketList).subscribe({
      next: (response) => {
        console.log('Atualização bem-sucedida:', response);
        alert('Ticket atualizado com sucesso!');
        this.TicketList = new Tickts(); // Limpar o formulário
      },
      error: (error) => {
        console.error('Erro na atualização:', error);
        alert('Erro ao atualizar ticket! ' + error.message);
      }
    });
  }

  // listar subtipos de problemas
  listarSubTipoProblema() {
    alert('Tipo de problema: ' + this.TicketList.tipoProblema);
    if (this.TicketList.tipoProblema == 'OUTRO') {
      return;
    }

    this.subTipoProblemaService.listarporsubtipo(this.TicketList.tipoProblema).subscribe({
      next: (response) => {
        console.log('Subtipo de problema:', response);
        this.SubTipoProblemalist = response;
      },
      error: (error) => {
        console.error('Erro na listagem:', error);
      }
    });
  }
}

