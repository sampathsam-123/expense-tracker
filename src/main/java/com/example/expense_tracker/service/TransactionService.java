package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.model.TransactionType;
import com.example.expense_tracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /* ───────────── CRUD ───────────── */
    public Transaction saveTransaction(Transaction tx) {
        return transactionRepository.save(tx);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /* ───────────── SUMMARY 1 – NECESSARY vs UNNECESSARY ───────────── */
    public Map<String, BigDecimal> getSummary() {
        List<Transaction> all = transactionRepository.findAll();

        BigDecimal necessary   = all.stream()
                .filter(t -> t.getType() == TransactionType.NECESSARY)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal unnecessary = all.stream()
                .filter(t -> t.getType() == TransactionType.UNNECESSARY)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
                "necessary",   necessary,
                "unnecessary", unnecessary
        );
    }

    /* ───────────── SUMMARY 2 – GROUP BY CATEGORY ───────────── */
    public Map<String, BigDecimal> getSummaryByCategory() {
        return transactionRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Transaction::getAmount,
                                BigDecimal::add
                        )
                ));
    }
    // TransactionService.java
public List<Map<String, Object>> getMonthlyTotals() {
    List<Map<String, Object>> result = new ArrayList<>();

    for (Object[] r : transactionRepository.monthlyTotals()) {
        Map<String, Object> m = new HashMap<>();
        m.put("month",       (String)  r[0]);
        m.put("necessary",   ((Number) r[1]).doubleValue());
        m.put("unnecessary", ((Number) r[2]).doubleValue());
        result.add(m);
    }
    return result;
}
    
}