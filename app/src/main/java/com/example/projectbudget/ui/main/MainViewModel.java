package com.example.projectbudget.ui.main;

/*The ViewModel is responsible for creating an instance of the repository and for providing methods and
  LiveData objects that can be utilized by the UI controller to keep the user interface synchronized with the
  underlying database.*/

import androidx.lifecycle.AndroidViewModel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectbudget.Transaction;
import com.example.projectbudget.TransactionRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private TransactionRepository repository;
    private LiveData<List<Transaction>> allTransactions;
    private MutableLiveData<List<Transaction>> searchResults;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allTransactions = repository.getAllTransactions();
        searchResults = repository.getSearchResults();
    }

    public MutableLiveData<List<Transaction>> getSearchResults(){
        return searchResults;
    }

    public LiveData<List<Transaction>> getAllTransactions(){
        return allTransactions;
    }

    public void insertTransaction (Transaction tran){
        repository.insertTransaction((tran));
    }

    public void findTransaction(String name){
        repository.findTransaction(name);
    }

    public void deleteTransaction(String name){
        repository.deleteTransaction(name);
    }
}

