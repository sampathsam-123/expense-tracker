package com.example.expense_tracker.model;   // must match folder

import jakarta.persistence.*;                // or javax.persistence.*
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;      // ← field #1
    private String description;
    private String category;        // ← field #2  (must be here)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    /* =====  GETTERS & SETTERS  ===== */

    public Long getId()            { return id; }
    public void setId(Long id)     { this.id = id; }

    public BigDecimal getAmount()  { return amount; }      // <-- exists?
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory()    { return category; }    // <-- exists?
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDate()     { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public Transaction() {}  // no-arg constructor required by JPA
}