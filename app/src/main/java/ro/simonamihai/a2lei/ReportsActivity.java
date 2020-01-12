package ro.simonamihai.a2lei;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ro.simonamihai.a2lei.db.DatabaseManager;
import ro.simonamihai.a2lei.model.Currency;
import ro.simonamihai.a2lei.model.Expense;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_piechart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PieChart pieChart = findViewById(R.id.pie_chart);

        int[] colors = new int[] {
                Color.parseColor("#E91E63"),
                Color.parseColor("#9C27B0"),
                Color.parseColor("#673AB7"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#2196F3"),
                Color.parseColor("#f44336"),
                Color.parseColor("#00BCD4"),
                Color.parseColor("#4CAF50")
                };

        // get expenses from database
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Expense> expenses = databaseManager.findAll();


        // add totals for year-month
        HashMap<String, Float> hmap = new HashMap<String, Float>();
        for (Expense expense : expenses) {
            String date = android.text.format.DateFormat.format("yyyy-MM", expense.getCreatedAt()).toString();

            if (hmap.containsKey(date)) {
                hmap.put(date, (float)expense.getPrice() + hmap.get(date));
            } else {
                hmap.put(date, (float)expense.getPrice());
            }

        }

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (String key:hmap.keySet()) {
            pieEntries.add(new PieEntry(hmap.get(key), key));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);

        SharedPreferences sharedPreferences = getSharedPreferences("currency_id", MODE_PRIVATE);
        final int currencyIndex = sharedPreferences.getInt("currencyId", Currency.CURRENCY_RON);
        final Currency currency = new Currency();

        pieDataSet.setValueTextSize(14f);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return currency.getSymbolForIndex(currencyIndex) + " " + value;
            }
        });

        pieChart.invalidate();
        setTitle("Reports");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

}
