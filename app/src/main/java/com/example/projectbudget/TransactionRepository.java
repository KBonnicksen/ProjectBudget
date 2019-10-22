package com.example.projectbudget;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

//Responsible for interacting with the room database
//on behalf of the ViewModel and will need to provide
//methods that use the DAO to insert, delete, and
//query transaction records.
public class TransactionRepository {

    private TransactionDAO transactionDAO;
    private AppDatabase db;

    private MutableLiveData<List<Transaction>> searchResults =
            new MutableLiveData<>();
    private LiveData<List<Transaction>> allTransactions;


    public TransactionRepository(Application application){
        db = AppDatabase.getDatabase(application);
        transactionDAO = db.transactionDAO();
        allTransactions = transactionDAO.getAllTransactions();
    }

    private void asyncFinished(List<Transaction> results){
        searchResults.setValue(results);
    }

    private static class QueryAsyncTask extends AsyncTask<String, Void, List<Transaction>>{

        private TransactionDAO asyncTaskDAO;
        private TransactionRepository delegate = null;

        QueryAsyncTask(TransactionDAO DAO){
            asyncTaskDAO = DAO;
        }

        @Override
        protected List<Transaction> doInBackground(final String... params) {
            return asyncTaskDAO.findTransaction(params[0]);
        }

        @Override
        protected  void onPostExecute(List<Transaction> result){
            delegate.asyncFinished(result);
        }
    }

    public static class InsertAsyncTask extends  AsyncTask<Transaction, Void, Void>{

        private TransactionDAO asyncTaskDAO;

        InsertAsyncTask(TransactionDAO DAO){
            asyncTaskDAO = DAO;
        }

        @Override
        protected Void doInBackground(final Transaction... params) {
            asyncTaskDAO.insertTransaction(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void>{

        private TransactionDAO asyncTaskDAO;

        DeleteAsyncTask(TransactionDAO DAO){
            asyncTaskDAO = DAO;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDAO.deleteTransaction(params[0]);
            return null;
        }
    }

    public void insertTransaction(Transaction tran){
        InsertAsyncTask task = new InsertAsyncTask(transactionDAO);
        task.execute(tran);
    }

    public void deleteTransaction(String name){
        DeleteAsyncTask task = new DeleteAsyncTask(transactionDAO);
        task.execute(name);
    }

    public void findTransaction(String name){
        QueryAsyncTask task = new QueryAsyncTask(transactionDAO);
        task.delegate = this;
        task.execute(name);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public MutableLiveData<List<Transaction>> getSearchResults() {
        return searchResults;
    }
}
