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
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import ro.simonamihai.a2lei.model.db.ExpenseDb;


public class MainActivity extends AppCompatActivity {
    private static final String CURRENCY_ID = "currency_id";
    RecyclerView recyclerView;
    ExpenseAdapter expenseAdapter;
    ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(CURRENCY_ID, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getInt("currencyId", -1) == -1) {
            editor.putInt("currencyId", 0);
            editor.apply();
        }
        this.showMainScreen();
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
                return true;
            }
        });
        popup.show();
    }

    public void showMainScreen() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ExpenseDb e = new ExpenseDb();

        expenses = e.getExpenses(getApplicationContext());

        ArrayList<Expense> todayExp = new ArrayList<>();
        double total = 0;
        String currentMonth = android.text.format.DateFormat.format("yyyy-MM", new Date()).toString();
        for (Expense expense : expenses) {

            if (android.text.format.DateFormat.format("yyyy-MM", expense.getCreatedAt()).toString().equals(currentMonth)) {
                total += expense.getPrice();

            }
            todayExp.add(expense);

        }

        TextView totalV = findViewById(R.id.totalExpenses);
        NumberFormat formatter = new DecimalFormat("#0.00");


        Currency currency = new Currency();
        SharedPreferences s = getSharedPreferences(CURRENCY_ID, MODE_PRIVATE);
        int currencyIndex = s.getInt("currencyId", 2);
        totalV.setText(currency.getCurrencySymbolIndex(currencyIndex)+" "+String.format("%.2f", total));
        expenseAdapter = new ExpenseAdapter(todayExp);

        TextView currentDate = findViewById(R.id.currentDate);
        currentDate.setText(currentMonth);
        recyclerView = findViewById(R.id.res);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(expenseAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showSettingsScreen() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void showNewExpenseScreen() {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void showReportsScreen() {
        Intent intent = new Intent(this, ReportsActivity.class);
        startActivity(intent);
    }

    public void loadTestData() {
        ExpenseDb e = new ExpenseDb();
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(getAssets().open("expenses.csv"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        expenses = e.getExpensesFile(is);
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager.open();
        databaseManager.deleteAll();
        for (Expense expense : expenses) {
            databaseManager.insert(expense);
        }

        databaseManager.close();
        showMainScreen();
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
}
