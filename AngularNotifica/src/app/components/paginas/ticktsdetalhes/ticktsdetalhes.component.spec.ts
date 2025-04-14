import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicktsdetalhesComponent } from './ticktsdetalhes.component';

describe('TicktscriarComponent', () => {
  let component: TicktsdetalhesComponent;
  let fixture: ComponentFixture<TicktsdetalhesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicktsdetalhesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicktsdetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
