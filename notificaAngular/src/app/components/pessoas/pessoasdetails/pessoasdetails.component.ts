import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Pessoa } from '../../../models/pessoa';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-pessoasdetails',
  standalone: true,
  imports: [MdbFormsModule,FormsModule],
  templateUrl: './pessoasdetails.component.html',
  styleUrl: './pessoasdetails.component.scss'
})
export class PessoasdetailsComponent {

  @Input('pessoa') pessoa:Pessoa = new Pessoa();

  @Output('retorno') retorno = new EventEmitter<any>();

  router = inject(ActivatedRoute)

  routerGenereic = inject(Router)

  constructor(){

    let id = this.router.snapshot.params['id']
    if(id>0){
      this.findById(id)
    }
  }

  findById(id:number){
    //busca no back-end
    this.pessoa.id = id
    this.pessoa.nome = "Willian"
    this.pessoa.documento = "dasda"
    this.pessoa.idade = 30
  }

  salvar(){

    if(this.pessoa.id>0){
      
      this.routerGenereic.navigate(['admin/pessoas'], {state:{pessoaEditada: this.pessoa}})
      alert("Editado com sucesso")

    }else{
      
      this.routerGenereic.navigate(['admin/pessoas'], {state:{pessoaNova: this.pessoa}})
      alert("Pessoa salva")

    }

    this.retorno.emit(this.pessoa)

  }
}
