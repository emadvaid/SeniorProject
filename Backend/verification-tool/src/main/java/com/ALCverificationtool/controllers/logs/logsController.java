package com.ALCverificationtool.controllers.logs;

import com.ALCverificationtool.models.Logs;
import com.ALCverificationtool.services.logsService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class logsController {
    @Autowired
    private LogService logService;

    @CrossOrigin
    @GetMapping("/logs")
    public ResponseEntity<List> getLogs() {
        List<Logs> GetLogResults = this.logService.getLogs();

        GetLogsResponse response = new GetLogsResponse(GetLogResults);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(GetLogResults, headers, HttpStatus.OK);
    }
}
