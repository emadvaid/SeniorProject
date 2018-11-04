package com.ALCverificationtool.controllers.keys;

import com.ALCverificationtool.models.TranslationResourceRec;
import com.ALCverificationtool.services.keysService.KeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KeysController {
    @Autowired
    private KeysService keysService;

    @CrossOrigin
    @PostMapping(value="/getAllKeys")
    public ResponseEntity<KeysResponse> getAllKeys(@RequestParam(value="language") String language,
                                                   @RequestParam(value="versionNumber") String versionNumber) {
        String tableName = language + "_" + versionNumber;
        List<TranslationResourceRec> keysList = this.keysService.getKeys(tableName);

        KeysResponse response = new KeysResponse(keysList);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}