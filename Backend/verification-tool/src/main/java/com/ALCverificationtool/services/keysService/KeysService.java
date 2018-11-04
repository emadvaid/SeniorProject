package com.ALCverificationtool.services.keysService;

import com.ALCverificationtool.models.TranslationResourceRec;

import java.util.List;

public interface KeysService {
    List<TranslationResourceRec> getKeys(String tableName);

    
}
