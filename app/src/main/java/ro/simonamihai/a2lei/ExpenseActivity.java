package ro.simonamihai.a2lei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ro.simonamihai.a2lei.db.DatabaseManager;
import ro.simonamihai.a2lei.model.Expense;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnInsertExpense = findViewById(R.id.insertExpenseBtn);
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
