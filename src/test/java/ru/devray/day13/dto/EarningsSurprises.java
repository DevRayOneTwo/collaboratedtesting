package ru.devray.day13.dto;

public class EarningsSurprises {
    public String date;
    public String symbol;
    public String actualEarningResult;
    public String estimatedEarning;

    public EarningsSurprises() {
    }

    public EarningsSurprises(String date, String symbol, String actualEarningResult, String estimatedEarning) {
        this.date = date;
        this.symbol = symbol;
        this.actualEarningResult = actualEarningResult;
        this.estimatedEarning = estimatedEarning;
    }

    @Override
    public String toString() {
        return "EarningsSurprises{" +
                "date='" + date + '\'' +
                ", symbol='" + symbol + '\'' +
                ", actualEarningResult='" + actualEarningResult + '\'' +
                ", estimatedEarning='" + estimatedEarning + '\'' +
                '}';
    }
}
