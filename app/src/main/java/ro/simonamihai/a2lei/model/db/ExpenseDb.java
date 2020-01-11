package ro.simonamihai.a2lei.model.db;

import android.content.Context;
import android.database.Cursor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ro.simonamihai.a2lei.db.DatabaseHelper;
import ro.simonamihai.a2lei.db.DatabaseManager;
import ro.simonamihai.a2lei.model.Expense;

public class ExpenseDb extends Expense {
    private DatabaseManager dbManager;
    private InputStreamReader is;

    public ExpenseDb() {
    }

    public ArrayList<Expense> getExpensesFile(InputStreamReader is) {
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

    public ArrayList<Expense> getExpenses(Context context) {
        ArrayList<Expense> values = new ArrayList<>();
        dbManager = new DatabaseManager(context);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
         do{
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
            Date createdAt = new Date(cursor.getLong(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.DATE)));
            double price = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.PRICE));
            values.add(new Expense(id, createdAt, name, price));
        } while (cursor.moveToNext());
        cursor.close();
        dbManager.close();


        return values;
    }
}
