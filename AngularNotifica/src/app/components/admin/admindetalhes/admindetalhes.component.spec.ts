import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmindetalhesComponent } from './admindetalhes.component';

describe('AdmincriarComponent', () => {
  let component: AdmindetalhesComponent;
  let fixture: ComponentFixture<AdmindetalhesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdmindetalhesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdmindetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
