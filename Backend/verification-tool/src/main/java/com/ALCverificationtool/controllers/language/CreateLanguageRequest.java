package com.ALCverificationtool.controllers.language;

import com.ALCverificationtool.models.LangRec;

public class CreateLanguageRequest {
    LangRec langDetails;

    public CreateLanguageRequest() {}

    public CreateLanguageRequest(LangRec langDetails) { this.langDetails = langDetails; }

    public LangRec getLangDetails() { return langDetails; }

    public void setLangDetails(LangRec langDetails) { this.langDetails = langDetails; }
}
