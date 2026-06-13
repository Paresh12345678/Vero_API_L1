package com.vero.api.util;

import com.vero.api.model.Category;
import com.vero.api.model.Transaction;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class BudgetCalculator {

    private BudgetCalculator() {
    }

    public static Map<Category, BigDecimal> getTopSpendingCategories(
            List<Transaction> transactions,
            int topN) {

        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Transaction::getAmount,
                                BigDecimal::add
                        )
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Category, BigDecimal>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
