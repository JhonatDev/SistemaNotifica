import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PessoasdetailsComponent } from './pessoasdetails.component';

describe('PessoasdetailsComponent', () => {
  let component: PessoasdetailsComponent;
  let fixture: ComponentFixture<PessoasdetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PessoasdetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PessoasdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
