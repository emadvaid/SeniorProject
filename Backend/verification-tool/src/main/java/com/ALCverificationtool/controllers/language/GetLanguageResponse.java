package com.ALCverificationtool.controllers.language;

import com.ALCverificationtool.models.LangRec;
import java.util.List;


public class GetLanguageResponse {
    private List<LangRec> langDetails;

    public GetLanguageResponse() {
    }

    public GetLanguageResponse(List<LangRec> langDetails) {
        this.langDetails = langDetails;
    }

    public List<LangRec> getLangDetails() {
        return langDetails;
    }

    public void setLangDetails(List<LangRec> langDetails) {
        this.langDetails = langDetails;
    }
}
