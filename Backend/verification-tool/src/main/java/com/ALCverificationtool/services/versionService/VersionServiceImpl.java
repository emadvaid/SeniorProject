package com.ALCverificationtool.services.versionService;

import com.ALCverificationtool.dao.version.VersionRepository;
import com.ALCverificationtool.models.VerRec;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService{
    @Override
    public VerRec createVer(VerRec newVerRecDetails) {
        return null;
    }

    @Override
    public List<VerRec> getVer() {
        return null;
    }
}
