export enum UserTypes {admin, dealer, unknown}

export class User {
    id: number;
    username: string;
    typeAsStr: string;
    isActive: boolean;
    firstName: string;
    lastName: string;
    email: string;
    language1: string;
    language2: string;

    // add any other user details

    get type(): UserTypes {

        switch (this.typeAsStr) {
            case 'admin':
                return UserTypes.admin;
            case 'dealer':
                return UserTypes.dealer;
            default:
                return ;
        }
    }

    set type(type: UserTypes) {
        switch (type) {
            case UserTypes.admin:
                this.typeAsStr = 'admin';
                break;
            case UserTypes.dealer:
                this.typeAsStr = 'dealer';
                break;
            default:
                this.type = UserTypes.unknown;
        }
    }

    /*
     * Simple helper method to build a User object from a JSON stringified object.
     */
    static fromJsonString(jsonString: string): User {
        const newUser = new User();

        const parsedJson: any = JSON.parse(jsonString);

        newUser.id        = parsedJson['id'];
        newUser.username  = parsedJson['username'];
        newUser.typeAsStr = parsedJson['typeAsStr'];
        newUser.isActive  = parsedJson['isActive'];
        newUser.firstName = parsedJson['firstName'];
        newUser.lastName  = parsedJson['lastName'];
        newUser.email     = parsedJson['email'];
        newUser.language1 = parsedJson['language1'];
        newUser.language2 = parsedJson['language2'];

        return newUser;
    }
}
