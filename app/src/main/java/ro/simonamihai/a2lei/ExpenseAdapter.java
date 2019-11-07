package ro.simonamihai.a2lei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ro.simonamihai.a2lei.model.Expense;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    List<Expense> expenseList;
    Context context;

    public ExpenseAdapter(List<Expense>expenseList)
    {
        this.expenseList = expenseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expense_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Expense expense = expenseList.get(position);

        holder.textExpenseName.setText(expense.getName());
        holder.textExpenseDate.setText(expense.getStringCreatedAt());
        holder.textExpensePrice.setText(expense.getStringPrice());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"The position is:"+position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textExpenseName;
        TextView textExpenseDate;
        TextView textExpensePrice;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textExpenseName = itemView.findViewById(R.id.textExpenseName);
            textExpenseDate = itemView.findViewById(R.id.textExpenseDate);
            textExpensePrice = itemView.findViewById(R.id.textExpensePrice);

            cv = itemView.findViewById(R.id.cv);
        }

    }
}
