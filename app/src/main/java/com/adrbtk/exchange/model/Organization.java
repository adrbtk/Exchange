package com.adrbtk.exchange.model;

import java.util.HashMap;

public class Organization {
    public String id;
    public int oldId;
    public int orgType;
    public String title;
    public String regionId;
    public String cityId;
    public String phone;
    public String address;
    public String link;
    public HashMap<String, AskBid> currencies;
}
