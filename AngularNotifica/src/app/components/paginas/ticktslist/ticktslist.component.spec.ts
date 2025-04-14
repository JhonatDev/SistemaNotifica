import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicktslistComponent } from './ticktslist.component';

describe('TicktslistComponent', () => {
  let component: TicktslistComponent;
  let fixture: ComponentFixture<TicktslistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicktslistComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicktslistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
