package ro.simonamihai.a2lei.model;

import java.util.ArrayList;

public class Currency {

    public static int CURRENCY_DOLLAR = 2;
    public static int CURRENCY_EURO = 1;
    public static int CURRENCY_RON = 0;

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

    public int getIndex(String name) {
        return this.availableCurrencies.indexOf(name);
    }

    public String getSymbolForIndex(int index) {
        if (!this.currencySymbol.get(index).isEmpty()) {
            return this.currencySymbol.get(index);
        }
        return "";
    }
}
