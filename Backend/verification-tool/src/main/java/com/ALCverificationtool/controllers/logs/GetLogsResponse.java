package com.ALCverificationtool.controllers.logs;

import com.ALCverificationtool.models.Logs;

import java.util.List;

public class GetLogsResponse {
    private List<Logs> LogDetails;

    public GetLogsResponse () {}

    public GetLogsResponse(List<Logs> logDetails) { LogDetails = logDetails; }

}
