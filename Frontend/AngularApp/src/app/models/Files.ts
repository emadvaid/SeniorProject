export class Files {
    verNumber: Number;
    static fromJsonString(jsonString: string): Files {
        const newFiles = new Files();

        const parsedJson: any = JSON.parse(jsonString);

        newFiles.verNumber        = parsedJson['verNumber'];


        return newFiles;
}
}
