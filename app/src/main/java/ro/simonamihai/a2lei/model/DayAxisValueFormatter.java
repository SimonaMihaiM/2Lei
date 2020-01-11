package ro.simonamihai.a2lei.model;

import android.util.Log;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.Date;

public class DayAxisValueFormatter extends ValueFormatter {

    private final BarLineChartBase<?> chart;

    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    public String getFormattedValue(long value) {
        Date date = new Date(value);
        return android.text.format.DateFormat.format("yyyy-MM", date).toString();
    }

}
