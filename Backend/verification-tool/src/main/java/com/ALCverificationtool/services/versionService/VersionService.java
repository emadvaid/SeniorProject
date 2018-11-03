package com.ALCverificationtool.services.versionService;

import com.ALCverificationtool.models.VerRec;

import java.util.List;

public interface VersionService {
    VerRec createVer(VerRec newVerRecDetails);
    List<VerRec> getVer();

}


