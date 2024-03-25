
package service;

import model.Currency;

import java.util.HashMap;
import java.util.Map;

public class CurrencyService {
    private Map<String,Currency> currencies = new HashMap<>();
    public CurrencyService() {
        currencies.put("USD",new Currency("US Dollar","USD"));
    }
    public double getExchangeCourse(String code, String targetCurrencyCode) {

        return 0;
    }

    public Currency getCurrencyByCode(String currencyCode) {
        return currencies.get(currencyCode);
    }
    public void addCurrency (Currency currency) {
        currencies.put(currency.getCode(),currency);
    }

}