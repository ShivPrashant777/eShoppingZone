export class User {
  constructor(
    public profileId: number,
    public username: string,
    public token: string,
    public roles: Array<string>
  ) {}
}
