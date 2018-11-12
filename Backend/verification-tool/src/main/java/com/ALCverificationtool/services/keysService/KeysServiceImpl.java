package com.ALCverificationtool.services.keysService;

import com.ALCverificationtool.dao.keys.KeysRepository;
import com.ALCverificationtool.models.TranslationResourceRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeysServiceImpl implements KeysService {
    private final KeysRepository keysDao;

    @Autowired
    KeysServiceImpl(KeysRepository keysDao) {
        this.keysDao = keysDao;
    }

    @Override
    public List<TranslationResourceRec> getKeys(String tableName) {
        List<TranslationResourceRec> results = keysDao.getKeys(tableName);
        return results;
    }

    @Override
    public boolean updateKeys(String tableName, TranslationResourceRec updatedKey) {
        return keysDao.updateKey(tableName, updatedKey);
    }
}
