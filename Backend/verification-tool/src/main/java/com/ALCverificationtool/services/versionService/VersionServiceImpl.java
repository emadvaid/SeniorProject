package com.ALCverificationtool.services.versionService;

import com.ALCverificationtool.dao.keys.KeysRepository;
import com.ALCverificationtool.dao.language.LanguageRepository;
import com.ALCverificationtool.dao.version.VersionRepository;
import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.models.VerRec;
import com.ALCverificationtool.services.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService{
    private final KeysRepository keysDao;
    private final VersionRepository verDao;
    private final LanguageRepository langDao;

    @Autowired
    VersionServiceImpl(
            KeysRepository keysDao,
            VersionRepository verDao,
            LanguageRepository langDao
    ) {
        this.keysDao = keysDao;
        this.verDao = verDao;
        this.langDao = langDao;
    }

    @Override
    public VerRec createVer(VerRec newVerRecDetails) {
        if (newVerRecDetails == null) {
            throw new ServiceException("version cannot be null");
        }


        if (verDao.findByRawVerNum(newVerRecDetails.getVerNum()).isPresent()) {
            throw new ServiceException("version already exist ");

        }

        VerRec newVerRec = this.verDao.save(newVerRecDetails);

        if(newVerRec== null || newVerRec.getId() ==  null) {
            throw new ServiceException("could not create new version number");
        }

        // now create the tables
        //keysDao.createKeyTable();

        // first get a list of the available language codes
        List<LangRec> languages = langDao.findAll();

        // loop over all the language codes, and create a versioned table for each n
        for(LangRec langRec :languages) {
            this.keysDao.createKeyTable(newVerRec.getSafeVersionNumber(),langRec.getLangCode(),true);
        }

        return newVerRec;
    }

    @Override
    public List<VerRec> getVer() {
        List<VerRec> results = verDao.findAll();

        return results;
    }
}
