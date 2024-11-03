import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PessoaslistComponent } from './pessoaslist.component';

describe('PessoaslistComponent', () => {
  let component: PessoaslistComponent;
  let fixture: ComponentFixture<PessoaslistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PessoaslistComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PessoaslistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
