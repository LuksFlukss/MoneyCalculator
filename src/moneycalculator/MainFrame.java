package moneycalculator;

import UI.MoneyDialog;
import UI.MoneyDisplay;
import UI.swing.SwingMoneyDialog;
import UI.swing.SwingMoneyDisplay;
import control.Command;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Currency;

public class MainFrame extends JFrame{

    private final Map<String, Command> commands = new HashMap<>();
    private MoneyDialog moneyDialog;
    private MoneyDisplay moneyDisplay;
    private final Currency[] currencies;
    
    public MainFrame(Currency[] currencies) {
        this.currencies = currencies;
        this.setTitle("Money Calculator IS2");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(moneyDialog(), BorderLayout.NORTH);
        this.add(moneyDisplay(), BorderLayout.CENTER);
        //this.add(toolsbar(), BorderLayout.SOUTH);
        this.add(calculateButton(), BorderLayout.SOUTH);
        this.setVisible(true);
    }
    
    public void add(Command command) {
        commands.put(command.name(), command);
    }

    public MoneyDialog getMoneyDialog() {
        return moneyDialog;
    }

    public MoneyDisplay getMoneyDisplay() {
        return moneyDisplay;
    }
       
    public Component moneyDialog() {
        SwingMoneyDialog dialog = new SwingMoneyDialog(currencies);
        moneyDialog = dialog;
        return dialog;
    }

    public Component moneyDisplay() {
        SwingMoneyDisplay display = new SwingMoneyDisplay();
        moneyDisplay = display;
        return display;        
    }

    public Component toolsbar() {
        JPanel panel = new JPanel();
        panel.add(calculateButton());
        return new JPanel();
    }

    private Component calculateButton() {
        JButton button = new JButton("Calculate");
        button.addActionListener(calculate());
        return button;        
    }

    private ActionListener calculate() {
        return (ActionEvent e) -> {
            commands.get("calculate").execute();
        };
    }
}
