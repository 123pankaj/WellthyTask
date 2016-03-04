package com.dev.wellthytask.networking;

import org.json.JSONObject;

public interface ApiResponseFetcher {
    void result(JSONObject obj);
    void error(String error);
}
