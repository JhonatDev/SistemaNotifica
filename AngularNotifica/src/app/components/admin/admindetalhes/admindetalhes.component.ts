import { Component, inject, Input, Output, OnInit, TemplateRef, TrackByFunction, ViewChild, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { TicktsService } from '../../../service/tickts/tickts.service';
import { Tickts } from '../../../models/tickts/tickts';
import { SubTipoProblemaService } from '../../../service/SubTipoProblema/sub-tipo-problema.service';
import { SubTipoProblema } from '../../../models/SubTipoProblema/subtipoproblema';
import { ImageUploadService } from '../../../service/image-service.service';
import { LoginService } from '../../../service/login-service.service';
import { environment } from '../../../../environments/environment';

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

  // Variáveis
  showModal: boolean = false; // Variável para controlar o modal
  Modalsair: boolean = false; // Variável para controlar o modal
  alert!: string;

  // servidor
  servidor = environment.SERVIDOR;

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
    private imageUploadService: ImageUploadService,
    private loginService: LoginService,
    private cdr: ChangeDetectorRef,
  ) { }

  onError(event: any) {
    event.target.src = `${environment.SERVIDOR}/image/download/Untitled.png`;
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
      const file = input.files[0];
      const reader = new FileReader();

      reader.onload = () => {
        const img = new Image();
        img.onload = () => {
          const canvas = document.createElement('canvas');
          const ctx = canvas.getContext('2d');

          if (ctx) {
            // Define o tamanho desejado para a imagem redimensionada
            const targetWidth = 300; // Largura desejada
            const targetHeight = 300; // Altura desejada

            // Ajusta o canvas para o tamanho desejado
            canvas.width = targetWidth;
            canvas.height = targetHeight;

            // Redimensiona e desenha a imagem no canvas
            ctx.drawImage(img, 0, 0, targetWidth, targetHeight);

            // Converte o canvas para um blob PNG
            canvas.toBlob((blob) => {
              if (blob) {
                // Mantém o nome original do arquivo, mas altera a extensão para .png
                const originalName = file.name.split('.').slice(0, -1).join('.');
                const newFileName = `${originalName}.png`;

                // Cria um novo arquivo a partir do blob
                const transformedFile = new File([blob], newFileName, {
                  type: 'image/png',
                });

                this.selectedFile = transformedFile;

                // Força a detecção de mudanças para garantir que a imagem seja atualizada
                this.cdr.detectChanges();

                this.uploadImage(); // Faça o upload aqui
              }
            }, 'image/png');
          }
        };

        if (reader.result) {
          img.src = reader.result as string;
        }
      };

      reader.readAsDataURL(file);
    }
  }


  uploadImage(): void {
    this.TicketList.caminhoFoto = 'loading-image.gif'; // Indicador de carregamento
    this.cdr.detectChanges(); // Forçar a detecção de mudanças

    if (this.selectedFile) {
      this.imageUploadService.uploadImage(this.selectedFile).subscribe({
        next: (response) => {
          console.log('Upload bem-sucedido:', response);
          this.TicketList.caminhoFoto = this.selectedFile?.name ?? '';
        },
        error: (error) => {
          console.error('Erro no upload:', error);
          this.TicketList.caminhoFoto = 'error-image.png'; // Mostra imagem de erro
        }
      });
    }
  }

  triggerFileInput(): void {
    document.getElementById('FotoInput')?.click();
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
    this.TicketList.raAluno = this.loginService.jwtDecode()?.username || '';
    console.log('Novo ticket:', this.TicketList);
    this.ticktsService.criar(this.TicketList).subscribe({
      next: (response) => {
        console.log('Criação bem-sucedida:', response);
        this.alert = 'Ticket criado com sucesso!';
        this.showModal = true;
        this.TicketList = new Tickts(); // Limpar o formulário
        //retornar para a lista de tickets
        this.Modalsair = true;
      },
      error: (error) => {
        console.error('Erro na criação:', error);
        this.alert = 'Erro ao criar ticket! ' + error.message;
        this.showModal = true;
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
        this.alert = 'Ticket atualizado com sucesso!';
        this.showModal = true;
        this.TicketList = new Tickts(); // Limpar o formulário
        //retornar para a lista de tickets
        this.Modalsair = true;
      },
      error: (error) => {
        console.error('Erro na atualização:', error);
        this.alert = 'Erro ao atualizar ticket! ' + error.message;
      }
    });
  }

  // deletar um ticket
  deletarTicket() {
    console.log('Deletar ticket:', this.TicketList);
    this.ticktsService.deletar(this.TicketList.id).subscribe({
      next: (response) => {
        console.log('Deleção bem-sucedida:', response);
        this.alert = 'Ticket deletado com sucesso!';
        this.showModal = true;
        this.TicketList = new Tickts(); // Limpar o formulário
      },
      error: (error) => {
        console.error('Erro na deleção:', error);
        this.alert = 'Erro ao deletar ticket! ' + error.message;
        this.showModal = true;
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

  // Fecha o modal
  closeModal(): void {
    this.showModal = false;
    if (this.Modalsair == true) {
      this.retorno.emit();

    }
  }
}

