import { Component, OnDestroy } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { Alert } from 'src/app/models/Alert.model';
import { User } from 'src/app/models/User.model';
import { UserProfile } from 'src/app/models/UserProfile.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent implements OnDestroy {
  public isLoading: boolean = false;
  public alert: Alert = new Alert('', '');
  public isAuthenticated = false;
  private authSubscription: Subscription = new Subscription();

  constructor(private authService: AuthService, private router: Router) {
    this.authSubscription = this.authService.user.subscribe((user) => {
      if (user) {
        this.router.navigate(['/products']);
      }
    });
  }

  onSwitchMode() {
    this.router.navigate(['/register']);
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }

    this.isLoading = true;

    const username = form.value.username;
    const password = form.value.password;

    this.authService.login(username, password).subscribe(
      (res) => {
        console.log(res);
        this.isLoading = false;
      },
      (err) => {
        console.log(err);
        this.alert = new Alert('error', `Error: ${err.error.message}`);
        this.isLoading = false;
      }
    );

    form.reset();
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
  }
}
