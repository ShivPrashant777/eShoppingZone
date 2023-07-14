import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthService } from 'src/app/services/auth.service';

import { NavbarComponent } from './navbar.component';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [NavbarComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should check the username when user is logged in', () => {
    let fixture = TestBed.createComponent(NavbarComponent);
    let app = fixture.debugElement.componentInstance;
    app.user = {
      profileId: 1,
      username: 'John',
      token: 'token',
      roles: ['Customer'],
    };
    fixture.detectChanges();
    let compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('#username').textContent).toEqual('Hi John');
  });

  it('should check the username when user is not logged in', () => {
    let fixture = TestBed.createComponent(NavbarComponent);
    let app = fixture.debugElement.componentInstance;
    fixture.detectChanges();
    expect(app.user).toEqual(null);
  });
});
