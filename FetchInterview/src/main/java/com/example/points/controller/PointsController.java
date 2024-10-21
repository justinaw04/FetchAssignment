package com.example.points.controller;

import com.example.points.management.PointsManagement;
import com.example.points.setup.Transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PointsController {
    private final PointsManagement pointsService;

    public PointsController(PointsManagement pointsService) {
        this.pointsService = pointsService;
    }

    // endpoint to add points using a POST request
    @PostMapping("/add")
    public ResponseEntity<Void> addPoints(@RequestBody Transaction transaction) {
        pointsService.addPoints(transaction);
        return ResponseEntity.ok().build();
    }

    // endpoint to spend points using a POST request
    @PostMapping("/spend")
    public ResponseEntity<?> spendPoints(@RequestBody Map<String, Integer> request) {
        int points = request.get("points");
        Object response = pointsService.spendPoints(points);
        
        // check if we need to send an error message
        if (response instanceof String) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // endpoint to get balance
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Integer>> getBalance() {
        return ResponseEntity.ok(pointsService.getBalance());
    }
}
