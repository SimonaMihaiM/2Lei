package ro.simonamihai.a2lei;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                if (item.getItemId() == R.id.action_new_budget) {
                    showNewBudgetScreen();
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

        ListView r = findViewById(R.id.res);
        Expense[] values = new Expense[]{
                new Expense(new Date(), "A", 2.5),
                new Expense(new Date(), "B", 4.5)
        };

        final ArrayList<Expense> list = new ArrayList<>();
        Collections.addAll(list, values);

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                list);
        r.setAdapter(adapter);

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

    public void showNewBudgetScreen() {
        Intent intent = new Intent(this, BudgetActivity.class);
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
