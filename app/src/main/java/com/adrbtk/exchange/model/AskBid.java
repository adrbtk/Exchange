package com.adrbtk.exchange.model;

public class AskBid {
    public String ask;
    public String bid;

    @Override
    public String toString() {
        return "? " + ask + " ? " + bid;
    }
}
