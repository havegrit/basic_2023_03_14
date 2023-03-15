package com.ll.basic1.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
    private String resultCode;
    private String msg;
    private final Object data;

    public static RsData of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }
    public static RsData of(String resultCode, String msg, Object data) {
        return new RsData(resultCode, msg, data);
    }

    public boolean isSuccess() {
        if (resultCode.startsWith("S-")) {
            return resultCode.startsWith("S-");
        }
        return false;
    }
}
