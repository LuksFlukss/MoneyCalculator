package persistence.files;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.Currency;
import persistence.CurrencyListLoader;


public class FileCurrencyListLoader implements CurrencyListLoader {
    
    private final String filename;

    public FileCurrencyListLoader(String filename) {
        this.filename = filename;
    }

    @Override
    public Currency[] currencies() {
        List<Currency> list;
   
        list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            while(true){
                String line = reader.readLine();
                if (line == null) break;
                list.add(currencyOf(line));
            }
        } 
        catch (IOException ex) {
            System.err.println(ex);
        }
        return list.toArray(new Currency[0]);
    }
    
    private Currency currencyOf(String line) {
        String[] split = line.split(",");
        return new Currency(split[0],split[1],split[2]);
    }  
}
