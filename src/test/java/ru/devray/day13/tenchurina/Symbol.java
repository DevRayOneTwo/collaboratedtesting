package ru.devray.day13.tenchurina;

public class Symbol {

    public String symbol;
    public String name;
    public Double price;
    public String exchange;

    public Symbol(String symbol, String name, Double price, String exchange) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.exchange = exchange;
    }


    //Обязателен для работы jackson и корректной десериализации!!!
    public Symbol(){}

    @Override
    public String toString() {
        return
          "symbol=  " + symbol + ", name= " + name + ", price= " + price +     ", exchange= " + exchange;
    }
}
