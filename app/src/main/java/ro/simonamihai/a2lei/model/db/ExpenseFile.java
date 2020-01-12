package ro.simonamihai.a2lei.model.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ro.simonamihai.a2lei.model.Expense;

public class ExpenseFile {

    public static ArrayList<Expense> getExpensesFile(InputStreamReader is) {
        ArrayList<Expense> values = new ArrayList<>();
        try {

            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] valuesTxt = line.split("\t");
                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                values.add(new Expense(parser.parse(valuesTxt[0]), valuesTxt[1], Double.parseDouble(valuesTxt[2])));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return values;
    }


}
