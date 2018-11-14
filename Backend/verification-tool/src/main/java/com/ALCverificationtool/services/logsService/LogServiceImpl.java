package com.ALCverificationtool.services.logsService;

import com.ALCverificationtool.dao.logs.LogsRepository;
import com.ALCverificationtool.models.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService{
    private final LogsRepository logsDao;

    @Autowired
    LogServiceImpl(
            LogsRepository logsDao
    ) {
        this.logsDao = logsDao;
    }

    @Override
    public List<Logs> getLogs() {
        List<Logs> results = logsDao.findAll();
        return results;
    }
}
