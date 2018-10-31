package com.ALCverificationtool.dao.keys;

import com.ALCverificationtool.models.TranslationResourceRec;

import java.util.List;


public interface KeysRepository {
    default boolean createKeyTable(String keyLanguageCode, String keyLanguageVersion) {
        return createKeyTable(keyLanguageCode, keyLanguageVersion, false);
    }

    boolean createKeyTable(String keyLanguageCode, String keyLanguageVersion, boolean dopExisiting);

    TranslationResourceRec create(TranslationResourceRec keyData);

    List<TranslationResourceRec> getKeys(String tableName);
}