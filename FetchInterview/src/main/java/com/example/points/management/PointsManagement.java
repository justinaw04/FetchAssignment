package com.example.points.management;

import org.springframework.stereotype.Service;

import com.example.points.setup.Transaction;

import java.util.*;


// manages points transaction
@Service
public class PointsManagement {
	// list to store all transactions
    private final List<Transaction> transactions = new ArrayList<>();
    // create a map with key-value pairs to track payer and balance respectively
    private final Map<String, Integer> pointBalances = new HashMap<>();

    public void addPoints(Transaction transaction) {
    	// keep track + add transaction to list
        transactions.add(transaction);
        // add new points to payer
        pointBalances.put(transaction.getPayer(), pointBalances.getOrDefault(transaction.getPayer(), 0) + transaction.getPoints());
    }

    public Object spendPoints(int pointsToSpend) {
        // check if user has enough points to spend
        if (getTotalPoints() < pointsToSpend) {
            return "User doesn't have enough points";
        }

        // keep track of how many points each payer has spent
        List<Map<String, Object>> spentPoints = new ArrayList<>();
        int pointsRemaining = pointsToSpend;

        // sort transactions by timestamp
        transactions.sort(Comparator.comparing(Transaction::getTimestamp));

        for (Transaction transaction : transactions) {
            if (pointsRemaining <= 0) {
                break;
            }
            if (transaction.getPoints() <= 0) {
                continue;
            }

            int spendablePoints = Math.min(pointsRemaining, transaction.getPoints());

            transaction.setPoints(transaction.getPoints() - spendablePoints);
            pointsRemaining -= spendablePoints;

            // update the payer's balance
            pointBalances.put(transaction.getPayer(), 
                pointBalances.get(transaction.getPayer()) - spendablePoints);

            // create a mapping for the spent entry
            Map<String, Object> spentEntry = new HashMap<>();
            spentEntry.put("payer", transaction.getPayer());
            spentEntry.put("points", -spendablePoints);
            spentPoints.add(spentEntry);
        }

        return spentPoints; // Return the list of points spent
    }


    
    // returns current point balances for all payers
    public Map<String, Integer> getBalance() {
        return pointBalances;
    }

    private int getTotalPoints() {
        return pointBalances.values().stream().mapToInt(Integer::intValue).sum();
    }
}
