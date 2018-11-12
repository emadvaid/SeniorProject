export class Language {
    id: string;
    langName: string;
    langCode: string;

    constructor(details: any = null) {
        if (details && details != null) {
            Object.assign(this, details);
            // just in case remove this
            delete this['checked'];
        }
    }

     /*
     * Simple helper method to build a Language  object from a JSON stringified object.
     */
    static fromJsonString(jsonString: string): Language {
        const newLanguage = new Language();

        const parsedJson: any = JSON.parse(jsonString);

        newLanguage.id        = parsedJson['id'];
        newLanguage.langName  = parsedJson['langName'];
        newLanguage.langCode = parsedJson['langCode'];


        return newLanguage;
    }

    static fromJsonObject(jsonString: any): Language {
        const newLanguage = new Language();

        const parsedJson: any = JSON.parse(jsonString);

        newLanguage.id        = parsedJson['id'];
        newLanguage.langName  = parsedJson['langName'];
        newLanguage.langCode = parsedJson['langCode'];


        return newLanguage;
    }
}
