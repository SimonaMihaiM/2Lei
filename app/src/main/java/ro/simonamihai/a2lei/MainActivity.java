package ro.simonamihai.a2lei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ro.simonamihai.a2lei.db.DatabaseManager;
import ro.simonamihai.a2lei.model.Currency;
import ro.simonamihai.a2lei.model.Expense;
import ro.simonamihai.a2lei.model.db.ExpenseFile;


public class MainActivity extends AppCompatActivity {

    private static final String CURRENCY_ID = "currency_id";

    private ArrayList<Expense> expenses;

    private TextView currentDate;
    private TextView totalExpenses;
    private RecyclerView recyclerView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentDate = findViewById(R.id.currentDate);
        totalExpenses = findViewById(R.id.totalExpenses);

        recyclerView = findViewById(R.id.res);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sharedPreferences = getApplicationContext().getSharedPreferences(CURRENCY_ID, MODE_PRIVATE);

        if (!sharedPreferences.contains("currencyId")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("currencyId", Currency.CURRENCY_RON);
            editor.apply();
        }

        this.showMainScreen();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    public void showMainScreen() {
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        expenses = databaseManager.findAll();

        double total = 0;
        String currentMonth = android.text.format.DateFormat.format("yyyy-MM", new Date()).toString();
        currentDate.setText(currentMonth);

        String expenseMonthExpression;
        for (Expense expense : expenses) {
            expenseMonthExpression = android.text.format.DateFormat.format("yyyy-MM", expense.getCreatedAt()).toString();
            if (expenseMonthExpression.equals(currentMonth)) {
                total += expense.getPrice();
            }
        }

        Currency currency = new Currency();
        int currencyIndex = sharedPreferences.getInt("currencyId", Currency.CURRENCY_RON);

        totalExpenses.setText(currency.getSymbolForIndex(currencyIndex)+" "+String.format("%.2f", total));

        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenses);

        recyclerView.setAdapter(expenseAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettingsScreen();
            return true;
        }

        if (id == R.id.action_reports) {
            showReportsScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showReportsScreen() {
        Intent intent = new Intent(this, ReportsActivity.class);
        startActivity(intent);
    }

    public void showSettingsScreen() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_actions, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_new_expense) {
                    showNewExpenseScreen();
                }
                if (item.getItemId() == R.id.load_test_data) {
                    loadTestData();
                }
                if (item.getItemId() == R.id.delete_data) {
                    deleteData();
                }
                return true;
            }
        });
        popup.show();
    }

    public void showNewExpenseScreen() {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void loadTestData() {
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(getAssets().open("expenses.csv"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        expenses = ExpenseFile.getExpensesFile(is);

        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager.deleteAll();

        for (Expense expense : expenses) {
            databaseManager.insert(expense);
        }

        databaseManager.close();

        showMainScreen();
    }

    public void deleteData() {
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager.deleteAll();
        databaseManager.close();
        showMainScreen();
    }

}
