package com.hh.util;

import java.util.UUID;

public class TokenUtil {
    public static String GetGUID()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
