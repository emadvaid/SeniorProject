package com.ALCverificationtool.services.languageService;

import com.ALCverificationtool.dao.keys.KeysRepository;
import com.ALCverificationtool.dao.language.LanguageRepository;
import com.ALCverificationtool.dao.logs.LogsRepository;
import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.models.Logs;
import com.ALCverificationtool.services.userService.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository langDao;
    private final KeysRepository keyDao;
    private final LogsRepository logsDao;

    @Autowired
    public LanguageServiceImpl(LanguageRepository langDao,KeysRepository keyDao, LogsRepository logsDao ) {
        this.langDao = langDao;
        this.keyDao = keyDao;
        this.logsDao = logsDao;
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
        //Create log for create language
        Logs logData = new Logs();
        logData.setAction("Language created");
        logData.setUserName(newLangDetails.getUsername());
        logData.setLanguage(newLangDetails.getLangName());
        //Get date and time for log
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = sdf.format(cal.getTime());
        logData.setDate(date);
        this.logsDao.save(logData);

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

    @Override
    public List<LangRec>findByVersion(String versionNumber) {
        // first get the list of table names which end with this version number
        List<String> keyTableNames =  this.keyDao.findKeyTableNamesByVersion(versionNumber);

        // get a list of all the language records
        List<LangRec> allLangRec = langDao.findAll();
        List<LangRec> results = new ArrayList<>();

        // remove any lang rec from this list if there is no corresponding key table
        for(LangRec langRec: allLangRec) {

            // see if there is a key table that begins with <langCode>_
            String tablePreffix = langRec.getLangCode().toLowerCase() + "_";

            // loop over our key table names
            for(String tableName: keyTableNames) {

                // if tableName starts with tablePreffix
                if(tableName.toLowerCase().startsWith(tablePreffix)) {

                    // add langRec to results
                    results.add(langRec);

                    //  ____ out of this loop
                    break;
                }
            }
        }
        return results;
    }
}