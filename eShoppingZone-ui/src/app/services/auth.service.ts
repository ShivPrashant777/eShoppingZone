import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap } from 'rxjs';
import { User } from '../models/User.model';
import { UserProfile } from '../models/UserProfile.model';
import { CartService } from './cart.service';
import { AppConstants } from '../constants/app.constants';

const { API_GATEWAY_URL, PROFILE_SERVICE_URL } = AppConstants;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  user = new BehaviorSubject<User | null>(null);

  constructor(
    private http: HttpClient,
    private router: Router,
    private cartService: CartService
  ) {}

  autoLogin() {
    const userData: User = JSON.parse(localStorage.getItem('user')!);
    if (!userData) {
      return;
    }
    this.validateToken(userData.token).subscribe(
      () => {
        this.user.next(userData);
      },
      (err) => {
        console.log(err);
        localStorage.removeItem('user');
      }
    );
  }

  validateToken(token: string) {
    return this.http.get(
      `${API_GATEWAY_URL}/${PROFILE_SERVICE_URL}/auth/validateToken`,
      { headers: { Authorization: token } }
    );
  }

  signup(userProfile: UserProfile) {
    return this.http
      .post<User>(
        `${API_GATEWAY_URL}/${PROFILE_SERVICE_URL}/auth/add-profile`,
        userProfile
      )
      .pipe(
        tap(
          (res) => {
            const user = new User(
              res.profileId,
              res.username,
              res.token,
              res.roles
            );
            this.user.next(user);
            localStorage.setItem('user', JSON.stringify(user));
          },
          (err) => {},
          () => {
            const userData: User = JSON.parse(localStorage.getItem('user')!);
            this.cartService.addCart(userData.profileId).subscribe(() => {
              this.router.navigate(['/products']);
            });
          }
        )
      );
  }

  login(username: string, password: string) {
    return this.http
      .post<User>(`${API_GATEWAY_URL}/${PROFILE_SERVICE_URL}/auth/login`, {
        username,
        password,
      })
      .pipe(
        tap((res) => {
          const user = new User(
            res.profileId,
            res.username,
            res.token,
            res.roles
          );
          this.user.next(user);
          localStorage.setItem('user', JSON.stringify(user));
          this.router.navigate(['/products']);
        })
      );
  }

  logout() {
    this.user.next(null);
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  getUserProfile(profileId: number) {
    return this.http.get<UserProfile>(
      `${API_GATEWAY_URL}/${PROFILE_SERVICE_URL}/profiles/id/${profileId}`,
      { observe: 'response' }
    );
  }

  updateUserProfile(userProfile: UserProfile) {
    return this.http.put(
      `${API_GATEWAY_URL}/${PROFILE_SERVICE_URL}/profiles/update`,
      userProfile,
      { observe: 'response' }
    );
  }
}
