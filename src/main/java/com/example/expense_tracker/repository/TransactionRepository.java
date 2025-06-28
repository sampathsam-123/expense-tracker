package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /* --- overall necessary / unnecessary totals --- */
    @Query("""
           SELECT SUM(CASE WHEN t.type = 'NECESSARY'   THEN t.amount ELSE 0 END),
                  SUM(CASE WHEN t.type = 'UNNECESSARY' THEN t.amount ELSE 0 END)
           FROM Transaction t
           """)
    Object[] computeTotals();

    /* --- monthly totals (portable across H2, MySQL, Postgres) --- */
    @Query("""
           SELECT FUNCTION('FORMATDATETIME', t.date, 'yyyy-MM')                         AS ym,
                  SUM(CASE WHEN t.type = 'NECESSARY'   THEN t.amount ELSE 0 END)        AS necessary,
                  SUM(CASE WHEN t.type = 'UNNECESSARY' THEN t.amount ELSE 0 END)        AS unnecessary
           FROM Transaction t
           GROUP BY FUNCTION('FORMATDATETIME', t.date, 'yyyy-MM')
           ORDER BY ym
           """)
    List<Object[]> monthlyTotals();
}