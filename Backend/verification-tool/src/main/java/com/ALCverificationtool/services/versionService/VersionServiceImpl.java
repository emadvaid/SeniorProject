package com.ALCverificationtool.services.versionService;

import com.ALCverificationtool.dao.keys.KeysRepository;
import com.ALCverificationtool.dao.language.LanguageRepository;
import com.ALCverificationtool.dao.logs.LogsRepository;
import com.ALCverificationtool.dao.version.VersionRepository;
import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.models.Logs;
import com.ALCverificationtool.models.VerRec;
import com.ALCverificationtool.services.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class VersionServiceImpl implements VersionService{
    private final KeysRepository keysDao;
    private final VersionRepository verDao;
    private final LanguageRepository langDao;
    private final LogsRepository logsDao;

    @Autowired
    VersionServiceImpl(
            KeysRepository keysDao,
            VersionRepository verDao,
            LanguageRepository langDao,
            LogsRepository logsDao
    ) {
        this.keysDao = keysDao;
        this.verDao = verDao;
        this.langDao = langDao;
        this.logsDao = logsDao;
    }

    @Override
    public VerRec createVer(VerRec newVerRecDetails) {
        if (newVerRecDetails == null) {
            throw new ServiceException("version cannot be null");
        }

        if(newVerRecDetails.getVerNum()== null || newVerRecDetails.getVerNum().trim().length() < 1) {
            throw new ServiceException("version number cannot be null or less than 1");
        }

        if (verDao.findByRawVerNum(newVerRecDetails.getVerNum()).isPresent()) {
            throw new ServiceException("version already exist ");

        }

        VerRec newVerRec = this.verDao.save(newVerRecDetails);

        //Create log for create language
        Logs logData = new Logs();
        logData.setAction("Version created");
        logData.setUserName("test username");
        logData.setVersion(newVerRecDetails.getVerNum());
        //Get date and time for log
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = sdf.format(cal.getTime());
        logData.setDate(date);
        this.logsDao.save(logData);

        if(newVerRec== null || newVerRec.getId() ==  null) {
            throw new ServiceException("could not create new version number");
        }

        // now create the tables
        //keysDao.createKeyTable();

        // first get a list of the available language codes
        List<LangRec> languages = langDao.findAll();

        // loop over all the language codes, and create a versioned table for each n
        for(LangRec langRec :languages) {
            this.keysDao.createKeyTable(langRec.getLangCode(), newVerRec.getSafeVersionNumber(),true);
        }

        return newVerRec;
    }

    @Override
    public List<VerRec> getVer() {
        List<VerRec> results = verDao.findAll();

        return results;
    }

    @Override
    public boolean deleteVersion(String versionNumber) {
        if(versionNumber == null || versionNumber.trim().length() < 1) {
            throw new ServiceException("version number cannot be empty");
        }

        boolean couldDelTables = this.keysDao.deleteKeyTablesByVersion(versionNumber);

        if(!couldDelTables) {
            throw new ServiceException("table deleted not successful");
        }

        this.verDao.deleteVerRecByRawVerNum(versionNumber);

        return true;

    }

    @Override
    public boolean deleteVersionLanguage(String langCode, String versionNumber) {
        if(versionNumber == null || versionNumber.trim().length() < 1) {
            throw new ServiceException("version number cannot be empty");
        }
        if(langCode == null || langCode.trim().length() < 1) {
            throw new ServiceException("version number cannot be empty");
        }

        boolean couldDelTable = this.keysDao.deleteKeyTable(langCode, versionNumber);

        if(!couldDelTable) {
            throw new ServiceException("table deleted not successful");
        }

        return true;
    }

    @Override
    public List<VerRec>getByLangCode(String langCode) {
        // first get the list of table names which end with this version number
        List<String> keyTableNames =  this.keysDao.findKeyTableNamesByLangCode(langCode);

        // get a list of all the language records
        List<VerRec> allLangRec = verDao.findAll();
        List<VerRec> results = new ArrayList<>();

        // remove any lang rec from this list if there is no corresponding key table
        for(VerRec verRec: allLangRec) {

            // see if there is a key table that begins with <langCode>_
            String versionSuffix = "_" + verRec.getSafeVersionNumber();

            // loop over our key table names
            for(String tableName: keyTableNames) {

                // if tableName ends with the version suffix
                if(tableName.toLowerCase().endsWith(versionSuffix)) {

                    // add langRec to results
                    results.add(verRec);

                    //  ____ out of this loop
                    break;
                }
            }
        }
        return results;
    }
}

