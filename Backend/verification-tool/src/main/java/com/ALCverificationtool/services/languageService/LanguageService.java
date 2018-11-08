package com.ALCverificationtool.services.languageService;

import com.ALCverificationtool.models.LangRec;


import java.util.List;


public interface LanguageService {

    LangRec createLang(LangRec newLangDetails);

    List<LangRec> getLang();
    List<LangRec> findByVersion(String versionNumber);
}