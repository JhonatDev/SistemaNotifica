import { Component, inject, Input, Output, OnInit, TemplateRef, TrackByFunction, ViewChild, EventEmitter } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { TicktsService } from '../../../service/tickts/tickts.service';
import { Tickts } from '../../../models/tickts/tickts';
import { SubTipoProblemaService } from '../../../service/SubTipoProblema/sub-tipo-problema.service';
import { SubTipoProblema } from '../../../models/SubTipoProblema/subtipoproblema';
import { SharedService } from '../../../service/shared.service';
import { ImageUploadService } from '../../../service/image-service.service';

@Component({
  selector: 'app-admindetalhes',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule,
    MdbModalModule,
    FormsModule
  ],
  templateUrl: './admindetalhes.component.html',
  styleUrls: ['./admindetalhes.component.css']
})

export class AdmindetalhesComponent {
  // Objeto do novo ticket
  @Input() TicketList!: Tickts;
  @Input() funcao!: any;
  @Output() retorno = new EventEmitter<any>();

  selectedFile: File | null = null;

  // Referência ao serviço de modal
  modalService = inject(MdbModalService);

  // Referência do template da modal no HTML
  @ViewChild('Admindetalhes') Admindetalhes!: TemplateRef<any>;

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

  onError(event: any) {
    event.target.src = 'http://localhost:8080/image/download/Untitled.png';
  }


  ngOnInit(): void {
    this.listarSubTipoProblema();
    /*document.documentElement.style.setProperty('--mdb-body-bg', '#242424');//muda a cor do fundo*/
    document.documentElement.style.setProperty('--mdb-modal-bg', '#242424');//muda a cor do fundo
    if (this.TicketList.caminhoFoto == null || this.TicketList.caminhoFoto == '') {
      this.TicketList.caminhoFoto = 'selecionar.png';
    }
  }


  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }


    this.uploadImage();
  }

  uploadImage(): void {
    if (this.selectedFile) {
      this.imageUploadService.uploadImage(this.selectedFile).subscribe({
        next: (response) => {
          console.log('Upload bem-sucedido:', response);
        },
        error: (error) => {
          console.error('Erro no upload:', error);
          return;
        }
      });
    }
    //esperar 3 segundo para a imagem ser carregada
    setTimeout(() => {
      // colocar o nome do arquivo no campo caminhoFoto
      this.TicketList.caminhoFoto = this.selectedFile?.name ?? '';
      console.log('Caminho da foto:', this.TicketList.caminhoFoto);
    }, 2000);
  }

  deledarImagen(): void {
    this.imageUploadService.deleteImage(this.TicketList.caminhoFoto).subscribe({
      next: (response) => {
        console.log('Deleção bem-sucedida:', response);
      },
      error: (error) => {
        console.error('Erro na deleção:', error);
      }
    });
  }

  //criar um novo ticket
  criarTicket() {
    this.TicketList.raAluno = this.SharedService.ultimoLogin;
    console.log('Novo ticket:', this.TicketList);
    this.ticktsService.criar(this.TicketList).subscribe({
      next: (response) => {
        console.log('Criação bem-sucedida:', response);
        alert('Ticket criado com sucesso!');
        this.TicketList = new Tickts(); // Limpar o formulário
        //retornar para a lista de tickets
        this.retorno.emit();
      },
      error: (error) => {
        console.error('Erro na criação:', error);
        alert('Erro ao criar ticket! ' + error.message);
      }
    });
  }

  // atualizar um ticket
  atualizarTicket() {
    this.TicketList.caminhoFoto = this.selectedFile?.name ?? '';
    console.log('Atualizar ticket:', this.TicketList);
    this.ticktsService.atualizar(this.TicketList.id, this.TicketList).subscribe({
      next: (response) => {
        console.log('Atualização bem-sucedida:', response);
        alert('Ticket atualizado com sucesso!');
        this.TicketList = new Tickts(); // Limpar o formulário
        //retornar para a lista de tickets
        this.retorno.emit();
      },
      error: (error) => {
        console.error('Erro na atualização:', error);
        alert('Erro ao atualizar ticket! ' + error.message);
      }
    });
  }

  // deletar um ticket
  deletarTicket() {
    console.log('Deletar ticket:', this.TicketList);
    this.ticktsService.deletar(this.TicketList.id).subscribe({
      next: (response) => {
        console.log('Deleção bem-sucedida:', response);
        alert('Ticket deletado com sucesso!');
        this.TicketList = new Tickts(); // Limpar o formulário
      },
      error: (error) => {
        console.error('Erro na deleção:', error);
        alert('Erro ao deletar ticket! ' + error.message);
      }
    });
  }

  // listar subtipos de problemas
  listarSubTipoProblema() {
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

