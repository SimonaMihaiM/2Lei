package ro.simonamihai.a2lei;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.github.mikephil.charting.charts.LineChart;
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
    private LineChart chart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barchart);

        PieChart pieChart = findViewById(R.id.chart0);


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
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Expense> expenses = databaseManager.findAll();
        HashMap<String, Float> hmap = new HashMap<String, Float>();
        for (Expense expense : expenses) {

            String date = android.text.format.DateFormat.format("yyyy-MM", expense.getCreatedAt()).toString();
            if (hmap.containsKey(date)) {
                hmap.put(date, (float)expense.getPrice() + hmap.get(date));
            } else {
                hmap.put(date, (float)expense.getPrice());
            }

        }

        SharedPreferences s = getSharedPreferences("currency_id", MODE_PRIVATE);
        final int currencyIndex = s.getInt("currencyId",Currency.CURRENCY_RON);
        for (String key:hmap.keySet()) {
            pieEntries.add(new PieEntry(hmap.get(key), key));
        }


        PieDataSet pieDataSet = new PieDataSet(pieEntries, "a");
        pieDataSet.setColors(colors);
        PieData pd = new PieData(pieDataSet);
        pieChart.setData(pd);

        pieDataSet.setValueTextSize(14f);
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Currency currency = new Currency();
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
