package com.catstudio.recyclerviewexample;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

public class UviInfo {
    private String county, publishAgency, publishTime, siteName, UVI, wgs84lat, wgs84lon;

    public UviInfo(@NotNull JsonObject obj) {
        county = obj.get("County").getAsString();
        publishAgency = obj.get("PublishAgency").getAsString();
        publishTime = obj.get("PublishTime").getAsString();
        siteName = obj.get("SiteName").getAsString();
        UVI = obj.get("UVI").getAsString();
        wgs84lat = obj.get("WGS84Lat").getAsString();
        wgs84lon = obj.get("WGS84Lon").getAsString();
    }

    public String getCounty() {
        return county;
    }

    public String getPublishAgency() {
        return publishAgency;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getWgs84lat() {
        return wgs84lat;
    }

    public String getWgs84lon() {
        return wgs84lon;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getUVI() {
        return UVI;
    }
}
