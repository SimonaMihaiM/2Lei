package ro.simonamihai.a2lei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ro.simonamihai.a2lei.db.DatabaseManager;
import ro.simonamihai.a2lei.model.Expense;

public class ExpenseActivity extends AppCompatActivity {
    private Spinner spinnerExpenseName;
    private TextView expensePrice;
    private CalendarView calendar;
    private Date selectedDate;
    private Button btnInsertExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        spinnerExpenseName = (Spinner) findViewById(R.id.spinner_expense_name);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_entries, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerExpenseName.setAdapter(adapter);

        expensePrice = findViewById(R.id.insert_expense_price);

        calendar = findViewById(R.id.calendar_view);
        calendar.setDate((new Date()).getTime());
        selectedDate = new Date();

        // Calendar not pass beyond today - Gandalf style
        // calendar.setMaxDate((new Date()).getTime());

        calendar.setVisibility(View.INVISIBLE);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                try {
                    selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(dayOfMonth + "/" + (month+1) + "/" + year);
                } catch (ParseException a) {
                    // to implement
                }
            }
        });

        btnInsertExpense = findViewById(R.id.btn_upsert_expense);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getIncomingIntent();
    }

    public void getIncomingIntent(){
         if (getIntent().hasExtra("updateId")) {
            final int updateId = getIntent().getIntExtra("updateId",0);

            calendar.setVisibility(View.VISIBLE);

            DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
            Expense expense = databaseManager.findById(updateId);
            databaseManager.close();

            List<String> categories = Arrays.asList(getResources().getStringArray(R.array.category_entries));
            int categoryIndex = categories.indexOf(expense.getName());

            spinnerExpenseName.setSelection(categoryIndex);

            expensePrice.setText(""+ expense.getPrice());
            calendar.setDate(expense.getCreatedAt().getTime());

            selectedDate = expense.getCreatedAt();

            btnInsertExpense.setText("Update");
            btnInsertExpense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                    databaseManager.open();

                    Expense e = databaseManager.findById(updateId);
                    e.setName(spinnerExpenseName.getSelectedItem().toString());
                    e.setPrice(Double.parseDouble(expensePrice.getText().toString()));
                    e.setCreatedAt(new Date(selectedDate.getTime()));

                    databaseManager.update(e);
                    databaseManager.close();

                    Snackbar.make(view, "Updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            btnInsertExpense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Expense expense = new Expense(selectedDate, spinnerExpenseName.getSelectedItem().toString(), Double.parseDouble(expensePrice.getText().toString()));

                    DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
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
