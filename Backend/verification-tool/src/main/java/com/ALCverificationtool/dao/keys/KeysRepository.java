package com.ALCverificationtool.dao.keys;

import com.ALCverificationtool.models.TranslationResourceRec;

import java.util.List;


public interface KeysRepository {

    // Methods for managing key Tables
    default boolean createKeyTable(String keyLanguageCode, String keyLanguageVersion) {
        return createKeyTable(keyLanguageCode, keyLanguageVersion, false);
    }
    boolean createKeyTable(String keyLanguageCode, String keyLanguageVersion, boolean dopExisiting);
    boolean deleteKeyTable(String keyLanguageCode, String keyLanguageVersion);
    boolean deleteKeyTablesByVersion(String keyLanguageVersion);
    List<String> findKeyTableNamesByVersion(String keyLanguageVersion);
    List<String> findKeyTableNamesByLangCode(String keyLanguageCode);

    TranslationResourceRec create(TranslationResourceRec keyData);

    List<TranslationResourceRec> getKeys(String tableName);
}