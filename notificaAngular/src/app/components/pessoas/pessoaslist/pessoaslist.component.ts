import { Component, inject, Input, TemplateRef, ViewChild } from '@angular/core';
import { Pessoa } from '../../../models/pessoa';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { PessoasdetailsComponent } from "../pessoasdetails/pessoasdetails.component";

@Component({
  selector: 'app-pessoaslist',
  standalone: true,
  imports: [RouterLink, CommonModule, MdbModalModule, PessoasdetailsComponent],
  templateUrl: './pessoaslist.component.html',
  styleUrl: './pessoaslist.component.scss'
})
export class PessoaslistComponent {

  //para abrir a modal
  modalService = inject(MdbModalService);

  //referencia do template da modal no HTML
  @ViewChild('modalPessoaDetails') modalPessoaDetails!: TemplateRef<any>;

  //referência da modal para conseguirmos fechar
  modalRef!:MdbModalRef<any>;

  constructor(){

    const navigation = this.router.getCurrentNavigation();

    const pessoaNova = navigation?.extras.state?.['pessoaNova'];

    const pessoaEditada = navigation?.extras.state?.['pessoaEditada'];

    let pessoa1:Pessoa = new Pessoa()
    pessoa1.id=1
    pessoa1.nome = "willian"
    pessoa1.idade = 33
    pessoa1.documento= "asb"

    let pessoa2:Pessoa = new Pessoa()
    pessoa2.id=2
    pessoa2.nome = "Bogler"
    pessoa2.idade = 32
    pessoa2.documento= "aasdasb"

    let pessoa3:Pessoa = new Pessoa()
    pessoa3.id=3
    pessoa3.nome = "Silva"
    pessoa3.idade = 31
    pessoa3.documento= "asd9898"

    this.pessoas.push(pessoa1)
    this.pessoas.push(pessoa2)
    this.pessoas.push(pessoa3)

    if(pessoaEditada){
      let indice = this.pessoas.findIndex(
        x => {return x.id == pessoaEditada.id}
      )
      this.pessoas[indice] = pessoaEditada

    }else if(pessoaNova){

      pessoaNova.id = this.pessoas.length + 1
      this.pessoas.push(pessoaNova)
      
    }
  }

  tebleHead:string[] = ["id","nome","idade","documento"]
  pessoas:Pessoa[] = []

  router = inject(Router)

  pessoaEdit:Pessoa = new Pessoa()

  novo(){
    this.pessoaEdit = new Pessoa()
    this.modalRef = this.modalService.open(this.modalPessoaDetails)
  }

  edit(pessoa:Pessoa){
    this.pessoaEdit = Object.assign({}, pessoa)
    this.modalRef = this.modalService.open(this.modalPessoaDetails)
  }

  deletar(pessoa:Pessoa){
    if(confirm(`Excluir ${pessoa.nome}?`)){
      let indice = this.pessoas.findIndex(
        x => {return x.id == pessoa.id}
      )
  
      this.pessoas.splice(indice,1)
    }

  }

  retornoDetalhe(pessoa:Pessoa){
    let indiceExiste = this.pessoas.findIndex(
      x => {return x.id == pessoa.id}
    )

    if(indiceExiste >= 0){
      this.pessoas[indiceExiste] = pessoa
    }else{
      this.pessoas.push(pessoa)
    }

    this.modalRef.close()
  }

}
