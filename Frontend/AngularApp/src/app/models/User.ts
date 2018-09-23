export enum UserTypes {admin, dealer, unknown}

export class User {
    id: number;
    username: string;
    type: UserTypes;
    isActive: boolean;
    firstName: string;
    lastName: string;
    email: string;
    language1: string;
    language2: string;

    // add any other user details
}
