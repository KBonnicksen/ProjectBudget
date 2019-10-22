package com.example.projectbudget.ui.main;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectbudget.R;
import com.example.projectbudget.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private MainViewModel mViewModel;
    private TransactionListAdapter adapter;

    private TextView transactionId;
    private EditText transactionName;
    private EditText transactionCost;
    private EditText transactionDescription;
    private EditText transactionDate;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        transactionId = getView().findViewById(R.id.transactionID);
        transactionName = getView().findViewById(R.id.transactionName);
        transactionCost = getView().findViewById(R.id.transactionCost);
        transactionDescription = getView().findViewById(R.id.transactionDescription);
        transactionDate = getView().findViewById(R.id.transactionDate);

        listenerSetup();
        observerSetup();
        recyclerSetup();
    }

    private void recyclerSetup() {
        RecyclerView recyclerView;

        adapter = new TransactionListAdapter(R.layout.transaction_list_item);
        recyclerView = getView().findViewById(R.id.transaction_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void observerSetup() {
        mViewModel.getAllTransactions().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> transactions) {
                adapter.setTransactionList(transactions);
            }
        });

        mViewModel.getSearchResults().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                if (transactions.size() > 0) {
                    transactionId.setText(String.format(Locale.US, "%d", transactions.get(0).getID()));
                    transactionName.setText(transactions.get(0).getName());
                    transactionCost.setText(String.format(Locale.US, "%d", transactions.get(0).getCost()));
                    transactionDescription.setText(transactions.get(0).getDescription());
                    transactionDate.setText(String.format(Locale.US, "%d", transactions.get(0).getDate()));
                } else {
                    transactionId.setText("No match");
                }
            }
        });
    }

    private void listenerSetup() {
        Button addButton = getView().findViewById(R.id.addButton);
        Button findButton = getView().findViewById(R.id.findButton);
        Button deleteButton = getView().findViewById(R.id.deleteButton);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Date date;
                String name = transactionName.getText().toString();
                Double cost = Double.parseDouble(transactionCost.getText().toString());
                String description = transactionDescription.getText().toString();

                String dateText = transactionDate.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try{
                    date = formatter.parse(dateText);
                }
                catch (Exception e){
                    date = null;
                }

                if(!name.equals("") && cost != 0.0 && !description.equals("")){
                    Transaction transaction = new Transaction(name, cost, description, date);
                    mViewModel.insertTransaction(transaction);
                    clearFields();
                }
                else{
                    transactionId.setText("Incomplete information");
                }
            }
        });

        findButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mViewModel.findTransaction(transactionName.getText().toString());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mViewModel.deleteTransaction(transactionName.getText().toString());
                clearFields();
            }
        });
    }

    private void clearFields(){
        transactionId.setText("");
        transactionName.setText("");
        transactionCost.setText("");
        transactionDescription.setText("");
        transactionDate.setText("");
    }
}


