package ro.simonamihai.a2lei.ro.simonamihai.a2lei.model.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import ro.simonamihai.a2lei.model.Expense;

public class ExpenseDb extends Expense {
    public ExpenseDb(Date createdAt, String name, double price) {
        super(createdAt, name, price);
    }

    public ExpenseDb() {
    }

    public ArrayList<Expense> getExpenses(InputStreamReader is) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return values;
    }
}
