package com.ALCverificationtool.services.statisticsService;

import com.ALCverificationtool.dao.keys.KeysRepository;
import com.ALCverificationtool.models.TranslationResourceRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService{
    private final KeysRepository keysDao;

    @Autowired
    public StatisticsServiceImpl(
            KeysRepository keysDao
    ) {
        this.keysDao = keysDao;
    }

    @Override
    public int newKeys(String language, String versionNumber) {
        String tmp = versionNumber.replaceAll("\\.", "_");
        String tableName = language + "_" + tmp;

        int newKeys = 0;

        List<TranslationResourceRec> keys = keysDao.getKeys(tableName);
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).getKeyNew()) {
                newKeys++;
            }
        }
        return newKeys;
    }

    @Override
    public int totalKeys(String language, String versionNumber) {
        String tmp = versionNumber.replaceAll("\\.", "_");
        String tableName = language + "_" + tmp;

        List<TranslationResourceRec> keys = keysDao.getKeys(tableName);
        int totalKeys = keys.size();
        return totalKeys;
    }

    @Override
    public int approvedKeys(String language, String versionNumber) {
        String tmp = versionNumber.replaceAll("\\.", "_");
        String tableName = language + "_" + tmp;

        int approvedKeys = 0;

        List<TranslationResourceRec> keys = keysDao.getKeys(tableName);
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).getApproved()) {
                approvedKeys++;
            }
        }
        return approvedKeys;
    }

    @Override
    public int totalFiles(String language, String versionNumber) {
        String tmp = versionNumber.replaceAll("\\.", "_");
        String tableName = language + "_" + tmp;

        int totalFiles = 0;
        String tmpFile = "";

        List<TranslationResourceRec> keys = keysDao.getKeys(tableName);
        for (int i = 0; i < keys.size(); i++) {
            if (!tmpFile.equals(keys.get(i).getFileName())) {
                tmpFile = keys.get(i).getFileName();
                totalFiles++;
                System.out.println(tmpFile);
            }
        }

        return totalFiles;
    }
}
