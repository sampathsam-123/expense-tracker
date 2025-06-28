package com.example.expense_tracker.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/ai")
public class AIAnalysisController {

    @PostMapping("/analyze")
    public Map<String, Object> analyzeSpending(@RequestBody List<Map<String, Object>> transactions) {
        double total = 0;
        double unnecessary = 0;

        for (Map<String, Object> tx : transactions) {
            double amount = Double.parseDouble(tx.get("amount").toString());
            total += amount;

            String category = tx.get("category").toString().toLowerCase();
            if (Arrays.asList("entertainment", "dining", "shopping", "luxury").contains(category)) {
                unnecessary += amount;
            }
        }

        double unnecessaryPct = (unnecessary / total) * 100;

        String tip = unnecessaryPct > 30
            ? "You're spending a large portion on non-essentials. Try budgeting fixed amounts for fun expenses."
            : "Your spending looks healthy! Keep tracking it monthly.";

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("unnecessary", unnecessary);
        result.put("unnecessaryPct", unnecessaryPct);
        result.put("advice", tip);

        return result;
    }
}