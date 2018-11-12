package com.ALCverificationtool.services.statisticsService;

public interface StatisticsService {
    int newKeys(String language, String versionNumber);
    int totalKeys(String language, String versionNumber);
    int approvedKeys(String language, String versionNumber);
    int totalFiles(String language, String versionNumber);
}
