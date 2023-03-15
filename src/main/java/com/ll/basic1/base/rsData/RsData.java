package com.ll.basic1.base.rsData;

import jakarta.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
    private String resultCode;
    private String msg;

    public static RsData of(String resultCode, String msg) {
        return new RsData(resultCode, msg);
    }
}
