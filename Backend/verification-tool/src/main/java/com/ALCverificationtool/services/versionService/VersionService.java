package com.ALCverificationtool.services.versionService;

import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.models.VerRec;

import java.util.List;

public interface VersionService {
    VerRec createVer(VerRec newVerRecDetails);
    List<VerRec> getVer();
    List<VerRec> getByLangCode(String langCode);
    boolean deleteVersion(String versionNumber);
    boolean deleteVersionLanguage(String langCode, String versionNumber);


}


