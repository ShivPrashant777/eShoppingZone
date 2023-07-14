import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  NgForm,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Address } from 'src/app/models/Address.model';
import { Alert } from 'src/app/models/Alert.model';
import { UserProfile } from 'src/app/models/UserProfile.model';
import { AuthService } from 'src/app/services/auth.service';
import { pastDateValidator } from 'src/app/util/past-date-validator';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css'],
})
export class RegisterPageComponent implements OnInit, OnDestroy {
  public form: any;
  public isLoading: boolean = false;
  public alert: Alert = new Alert('', '');
  public isAuthenticated = false;
  private authSubscription: Subscription = new Subscription();

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.authSubscription = this.authService.user.subscribe((user) => {
      if (user) {
        this.router.navigate(['/products']);
      }
    });
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      mobileNumber: [
        '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(10),
          Validators.pattern('[0-9]{10}'),
        ],
      ],
      about: ['', Validators.required],
      dateOfBirth: ['', [Validators.required, pastDateValidator()]],
      gender: ['', Validators.required],
      role: ['Customer', Validators.required],
      address: this.formBuilder.array([this.createAddressGroup()]),
    });
  }

  // Helper method to create an address form group
  createAddressGroup() {
    return this.formBuilder.group({
      houseNumber: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      streetName: ['', Validators.required],
      colonyName: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      pincode: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
    });
  }

  get addressForms() {
    return this.form.get('address') as FormArray;
  }

  addAddress() {
    this.addressForms.push(this.createAddressGroup());
  }

  removeAddress(index: number) {
    this.addressForms.removeAt(index);
  }

  onSubmit() {
    if (!this.form.valid) {
      return;
    }

    this.isLoading = true;

    const username = this.form.value.username;
    const email = this.form.value.email;
    const password = this.form.value.password;
    const mobileNumber = this.form.value.mobileNumber;
    const about = this.form.value.about;
    const dateOfBirth = this.form.value.dateOfBirth;
    const gender = this.form.value.gender;
    const role = this.form.value.role;
    const address = this.form.value.address;

    const userProfile = new UserProfile(
      username,
      '',
      email,
      mobileNumber,
      about,
      dateOfBirth,
      gender,
      role,
      password,
      address
    );

    console.log(userProfile);
    this.authService.signup(userProfile).subscribe(
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

    this.form.reset();
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
  }
}
