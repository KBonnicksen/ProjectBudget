package com.example.projectbudget.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectbudget.R;
import com.example.projectbudget.Transaction;

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private int transactionItemLayout;
    private List<Transaction> transactionList;

    public TransactionListAdapter(int layoutId){
        transactionItemLayout = layoutId;
    }

    public void setTransactionList(List<Transaction> transactions){
        transactionList = transactions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return transactionList == null ? 0 : transactionList.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(transactionItemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextView item = holder.item;
        item.setText(transactionList.get(position).getName());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView item;
        ViewHolder(View itemView){
            super(itemView);
            item = itemView.findViewById(R.id.transaction_row);
        }
    }
}
