package persistence.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.round;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Currency;
import model.ExchangeRate;
import persistence.ExchangeRateLoader;

public class RestExchangeRateLoader implements ExchangeRateLoader {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        System.out.println("From => " + from);
        System.out.println("To => " + to);
        return new ExchangeRate(from, to, (readRate(read(from.getCode(), to.getCode()))));
    }

    private String read(String from, String to) {
        URL url = null;
        try {
            url = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + 
                    from.toLowerCase() + "/" + to.toLowerCase() + ".json");
        } catch (MalformedURLException ex) {
            Logger.getLogger(RestExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BufferedReader in;
        String line = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {
                if (line.contains(to.toLowerCase())) break;
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(RestExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(line);
        return line;
    }
    
   private Double readRate(String line) {
       Double valueOff;
       valueOff = Double.valueOf(line.split(": ")[1]);
       return (valueOff);
   }
}
