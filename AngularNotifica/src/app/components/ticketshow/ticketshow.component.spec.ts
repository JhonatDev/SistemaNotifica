import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketshowComponent } from './ticketshow.component';

describe('TicketshowComponent', () => {
  let component: TicketshowComponent;
  let fixture: ComponentFixture<TicketshowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketshowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketshowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
