package com.ALCverificationtool.services.languageService;

import com.ALCverificationtool.dao.language.LanguageRepository;
import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.services.userService.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository langDao;

    @Autowired
    public LanguageServiceImpl(LanguageRepository langDao) {
        this.langDao = langDao;
    }

    @Override
    public LangRec createLang(LangRec newLangDetails) {
        if (newLangDetails == null) {
            throw new UserException("Language cannot be null");
        }

        if (langDao.findBylangName(newLangDetails.getLangName()).isPresent()) {
            throw new UserException("Language already exists");
        }

        LangRec tmpLangRec = this.langDao.save(newLangDetails);

        if (tmpLangRec == null || tmpLangRec.getId() == null) {
            throw new UserException("Could not create language");
        }

        return tmpLangRec;
    }
    @Override
    public List<LangRec> getLang() {
        List<LangRec> results = langDao.findAll();

        return results;

    }
}

