package repository;


import model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository {


    private List<Currency> currencyList;

    public CurrencyRepository() {
        this.currencyList = new ArrayList<>();
        listCurrency();
    }

    public void listCurrency() {
        currencyList.add(new Currency("Euro", "EUR"));
        currencyList.add(new Currency("Dollar", "USD"));
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public Currency addCurrency(String name, String code) {
        Currency currency = new Currency(name, code);
        currencyList.add(currency);
        return currency;
    }


    public Currency removeCurrency(String code) {
        for (Currency currency: currencyList){
            if (currency.getCode().equals(code)){
                currencyList.remove(currency);
                return currency;
            }
        }
        return  null;
    }



}
