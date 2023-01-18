/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import UI.MoneyDialog;
import UI.MoneyDisplay;
import model.Currency;
import model.Money;
import persistence.ExchangeRateLoader;

/**
 *
 * @author Louka Vanhoucke
 */
public class CalculateCommand  implements Command {

    private final MoneyDialog moneyDialog;
    private final MoneyDisplay moneyDisplay;
    private final ExchangeRateLoader loader;
    private final Currency eur = new Currency("Euro", "EUR", "€");

    public CalculateCommand(MoneyDialog moneyDialog, MoneyDisplay moneyDisplay, ExchangeRateLoader loader) {
        this.moneyDialog = moneyDialog;
        this.moneyDisplay = moneyDisplay;
        this.loader = loader;
    }

    @Override
    public String name() {
        return "calculate";
    }

    @Override
    public void execute() {
        moneyDisplay.display(exchange(moneyDialog.get()));  
    }

    private Money exchange(Money money) {
        return new Money(money.getAmount() * rateOf(money.getCurrency()), eur);
    }

    private double rateOf(Currency currency) {
        return loader.load(currency, eur).getAmount();
    }
}
