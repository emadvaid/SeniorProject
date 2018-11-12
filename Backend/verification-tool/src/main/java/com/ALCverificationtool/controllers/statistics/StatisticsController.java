package com.ALCverificationtool.controllers.statistics;

import com.ALCverificationtool.services.statisticsService.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @CrossOrigin
    @PostMapping(value = "/statistics/new")
    public int getNewKeys(
            @RequestParam(value="language") String language,
            @RequestParam(value="versionNumber") String versionNumber
    ) {
        int keys = statisticsService.newKeys(language, versionNumber);
        return keys;
    }

    @CrossOrigin
    @PostMapping(value = "/statistics/total")
    public int getTotalKeys(
            @RequestParam(value="language") String language,
            @RequestParam(value="versionNumber") String versionNumber
    ) {
        int keys = statisticsService.totalKeys(language, versionNumber);
        return keys;
    }

    @CrossOrigin
    @PostMapping(value = "/statistics/approved")
    public int getApprovedKeys(
            @RequestParam(value="language") String language,
            @RequestParam(value="versionNumber") String versionNumber
    ) {
        int keys = statisticsService.approvedKeys(language, versionNumber);
        return keys;
    }

    @CrossOrigin
    @PostMapping(value = "/statistics/files")
    public int totalFiles(
            @RequestParam(value="language") String language,
            @RequestParam(value="versionNumber") String versionNumber
    ) {
        int files = statisticsService.totalFiles(language, versionNumber);
        return files;
    }
}
