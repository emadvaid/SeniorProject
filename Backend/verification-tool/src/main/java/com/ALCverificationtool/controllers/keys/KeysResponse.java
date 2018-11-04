package com.ALCverificationtool.controllers.keys;

import com.ALCverificationtool.models.TranslationResourceRec;
import com.ALCverificationtool.models.keys;

import java.util.List;

public class KeysResponse {
    private List<TranslationResourceRec> KeysDetails;

    public KeysResponse(List<TranslationResourceRec> keysDetails) {KeysDetails = keysDetails;}
}
