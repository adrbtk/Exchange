package com.adrbtk.exchange.model;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class Request {
    public static Data get(String language) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://resources.finance.ua")
                .build();
        Currencies currency = adapter.create(Currencies.class);
        return currency.get(language.toLowerCase());
    }


    public interface Currencies {
        @GET("/{lang}/public/currency-cash.json")
        Data get(@Path("lang") String lang);
    }
}
