package UI.swing;

import UI.MoneyDialog;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import model.Currency;
import model.Money;

public class SwingMoneyDialog extends JPanel implements MoneyDialog{

    private Currency currency;
    private String amount;
    private final Currency[] currencies;
    
    public SwingMoneyDialog(Currency[] currencies) {
        this.currencies = currencies;
        this.add(amount());
        this.add(currency());
    }
    
    @Override
    public Money get() {
        return new Money(Double.parseDouble(amount), currency);
    }

    private JTextField amount() {
        JTextField textField = new JTextField("100");
        textField.setColumns(10);
        textField.getDocument().addDocumentListener(amountChanged());
        amount = textField.getText();
        return textField;                 
    }

    private JComboBox currency() {
      JComboBox combo = new JComboBox(currencies);
      combo.addItemListener(currencyChanged());
      currency = (Currency) combo.getSelectedItem();
      return combo;
    }
    
    private ItemListener currencyChanged() {
        return (ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) return;
            currency = (Currency) e.getItem();
        };
    }

    private DocumentListener amountChanged() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            private void amountChanged(Document document) {
                try {
                    amount = document.getText(0, document.getLength());
                } catch (BadLocationException ex) {
                    Logger.getLogger(SwingMoneyDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }   
}
