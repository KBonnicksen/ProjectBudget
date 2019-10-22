package com.example.projectbudget;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//The DAO implements methods to insert, find and delete records from the products database.
@Dao
public interface TransactionDAO {
    @Insert
    void insertTransaction(Transaction tran);

    // Select all
    @Query("SELECT * FROM Transactions")        // will be used to keep the recyclerView up to date at all times
    LiveData<List<Transaction>> getAllTransactions();

    // Delete
    @Query("DELETE FROM Transactions WHERE name = :name")
    void deleteTransaction(String name);

    // Select single
    @Query("SELECT * FROM Transactions WHERE name = :name")
    List<Transaction> findTransaction(String name);
}
    /*@Query("SELECT * FROM Transactions WHERE transactionID = :id")
    Transaction getItemById(Long id);*/

/*    @Update
    void update(Transaction tran);
    @Delete

    void delete(Transaction tran);  Taking out because it is not identical to textbook*/


