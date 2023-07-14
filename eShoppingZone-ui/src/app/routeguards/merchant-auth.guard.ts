import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';

import { User } from '../models/User.model';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class MerchantAuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  user: User | null = null;

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (localStorage.getItem('user')) {
      this.user = JSON.parse(localStorage.getItem('user')!);
    }
    if (!this.user || !this.user.roles.includes('Merchant')) {
      this.router.navigate(['/login']);
    }
    return this.user ? true : false;
  }
}
