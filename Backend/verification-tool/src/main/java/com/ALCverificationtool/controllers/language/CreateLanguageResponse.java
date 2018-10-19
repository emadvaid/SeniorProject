package com.ALCverificationtool.controllers.language;

import com.ALCverificationtool.models.LangRec;

public class CreateLanguageResponse {
    LangRec langDetails;

    public CreateLanguageResponse() {
    }

    public CreateLanguageResponse(LangRec langDetails) { this.langDetails = langDetails; }

    public LangRec getLangDetails() { return langDetails; }

    public void setLangDetails(LangRec langDetails) { this.langDetails = langDetails; }
}
