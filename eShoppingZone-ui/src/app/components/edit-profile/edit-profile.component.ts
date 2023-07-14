import { Component, OnDestroy } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Address } from 'src/app/models/Address.model';
import { Alert } from 'src/app/models/Alert.model';
import { User } from 'src/app/models/User.model';
import { UserProfile } from 'src/app/models/UserProfile.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css'],
})
export class EditProfileComponent implements OnDestroy {
  public isLoading: boolean = false;
  public alert: Alert = new Alert('', '');
  public user: User | null = null;
  public userProfile: UserProfile = new UserProfile(
    '',
    '',
    '',
    0,
    '',
    '',
    '',
    '',
    '',
    []
  );
  private authSubscription: Subscription = new Subscription();

  constructor(private authService: AuthService, private router: Router) {
    this.authSubscription = this.authService.user.subscribe((user) => {
      this.user = user;
      if (this.user) {
        this.authService
          .getUserProfile(this.user.profileId)
          .subscribe((res) => {
            this.userProfile = res.body ? res.body : this.userProfile;
            console.log(this.userProfile);
          });
      }
    });
  }

  public addAddressInput() {
    this.userProfile.addresses.push(new Address('', '', '', '', '', '', ''));
  }

  public removeAddressInput(index: number) {
    this.userProfile.addresses.splice(index, 1);
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }

    this.isLoading = true;

    console.log(this.userProfile);
    this.authService.updateUserProfile(this.userProfile).subscribe(
      (res) => {
        if (res.status === 200) {
          this.alert = new Alert('info', 'Profile Updated Successfully');
        } else {
          this.alert = new Alert('error', 'Error in Updating Profile');
        }
        this.isLoading = false;
      },
      (err) => {
        console.log(err);
        this.alert = new Alert('error', `Error: ${err.error.message}`);
        this.isLoading = false;
      }
    );
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
  }
}
