package ro.simonamihai.a2lei.model;

import java.util.ArrayList;

public class Currency {

    private ArrayList<String> availableCurrencies;
    private ArrayList<String> currencySymbol;
    public Currency() {
        this.availableCurrencies = new ArrayList<String>();
        this.currencySymbol = new ArrayList<String>();

        this.availableCurrencies.add("Romanian Leu");
        this.currencySymbol.add("RON");
        this.availableCurrencies.add("Euro");
        this.currencySymbol.add("€");
        this.availableCurrencies.add("Dollar");
        this.currencySymbol.add("$");
    }

    public String getCurrencySymbol(String name) {
        int index = this.availableCurrencies.indexOf(name);
        return getCurrencySymbolIndex(index);
    }

    public String getCurrencyName(String symbol) {
        int index = this.currencySymbol.indexOf(symbol);
        if (index > -1) {
            return this.availableCurrencies.get(index);
        }
        return "";
    }

    public String getCurrencySymbolIndex(int index) {
        if (index > -1) {
            return this.currencySymbol.get(index);
        }
        return "";
    }
}
