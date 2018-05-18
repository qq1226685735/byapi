package com.hh.service;

import net.sf.json.JSONObject;

public interface HomeService {
    JSONObject jokebynumber(int location, int number);

    JSONObject selectAnnounce(int location, int number);

    JSONObject deleteannouncebyid(int announceid);

    JSONObject deletejokebyid(int jokeid);
}
