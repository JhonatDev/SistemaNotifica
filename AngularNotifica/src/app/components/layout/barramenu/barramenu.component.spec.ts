import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarramenuComponent } from './barramenu.component';

describe('BarramenuComponent', () => {
  let component: BarramenuComponent;
  let fixture: ComponentFixture<BarramenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BarramenuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BarramenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
