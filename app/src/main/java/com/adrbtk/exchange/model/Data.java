package com.adrbtk.exchange.model;

import java.util.HashMap;
import java.util.List;

public class Data {
    public String sourceId;
    public String date;
    public List<Organization> organizations;
    public HashMap<String, String> orgTypes;
    public HashMap<String, String> currencies;
    public HashMap<String, String> regions;
    public HashMap<String, String> cities;
}