import { Address } from './Address.model';

export class UserProfile {
  constructor(
    public username: string,
    public image: string,
    public email: string,
    public mobileNumber: number,
    public about: string,
    public dateOfBirth: string,
    public gender: string,
    public role: string,
    public password: string,
    public addresses: Address[],
    public profileId?: number
  ) {}
}
