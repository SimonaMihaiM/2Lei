package ro.simonamihai.a2lei;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ro.simonamihai.a2lei.db.DatabaseManager;
import ro.simonamihai.a2lei.model.Expense;
import ro.simonamihai.a2lei.model.db.ExpenseDb;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getIncomingIntent();


    }

    public void getIncomingIntent(){
        Log.d("", "a");
        Button btnInsertExpense = findViewById(R.id.insertExpenseBtn);

        if (getIntent().hasExtra("updateId")) {
            final int updateId= getIntent().getIntExtra("updateId",0);

            DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
            databaseManager.open();
            Expense e = databaseManager.findById(updateId);
            databaseManager.close();
            TextView expenseName = findViewById(R.id.insertExpenseName);
            TextView expensePrice = findViewById(R.id.inserExpensePrice);
            expenseName.setText(e.getName());
            expensePrice.setText(""+ e.getPrice());
            btnInsertExpense.setText("Update");
            btnInsertExpense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView expenseName = findViewById(R.id.insertExpenseName);
                    TextView expensePrice = findViewById(R.id.inserExpensePrice);

                    // Expense expense = new Expense(new Date(), expenseName.getText().toString(), Double.parseDouble(expensePrice.getText().toString()));


                    DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                    databaseManager.open();
                    Expense e = databaseManager.findById(updateId);
                    e.setName(expenseName.getText().toString());
                    e.setPrice(Double.parseDouble(expensePrice.getText().toString()));
                    databaseManager.update(e);
                    databaseManager.close();
                    Snackbar.make(view, "Inserted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            btnInsertExpense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView expenseName = findViewById(R.id.insertExpenseName);
                    TextView expensePrice = findViewById(R.id.inserExpensePrice);

                    Expense expense = new Expense(new Date(), expenseName.getText().toString(), Double.parseDouble(expensePrice.getText().toString()));

                    DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                    databaseManager.open();
                    databaseManager.insert(expense);
                    databaseManager.close();
                    Snackbar.make(view, "Inserted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

}
