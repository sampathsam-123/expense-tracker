package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Create a new transaction
    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        Transaction saved = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(saved);
    }

    // Get all transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> list = transactionService.getAllTransactions();
        return ResponseEntity.ok(list);
    }

    // Get summary of necessary vs unnecessary
    @GetMapping("/summary")
public Map<String, BigDecimal> getSummary() {
    return transactionService.getSummary();
}
@GetMapping("/monthly")
public List<Map<String, Object>> monthly() {
    return transactionService.getMonthlyTotals();
}

    // Get summary grouped by category
    @GetMapping("/summary/by-category")
    public ResponseEntity<Map<String, BigDecimal>> getSummaryByCategory() {
        return ResponseEntity.ok(transactionService.getSummaryByCategory());
    }
}