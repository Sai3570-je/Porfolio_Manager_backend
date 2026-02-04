package com.example.portfoliomanager.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.Map;

@Component
public class DataSeeder {
    private final JdbcTemplate jdbc;

    public DataSeeder(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void seed() {
        // Run all seeds; each method is idempotent (skips if table already has rows)
        seedInstrumentsMinimal();
        seedMarketQuotes();
        seedPositions();
        seedOrders();
        seedWatchlist();
        seedPortfolioSnapshots();
        seedTransactions();
        System.out.println("Demo data seeding finished.");
    }

    // public so you can call from a controller if you want manual trigger
    public void runSeedNow() {
        seed();
    }

    private Long findOrCreateInstrument(String symbol, String name) {
        try {
            Long id = jdbc.queryForObject("SELECT id FROM instruments WHERE symbol = ? LIMIT 1", Long.class, symbol);
            if (id != null) return id;
        } catch (Exception ignored) {}

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO instruments (symbol, name, asset_type, exchange, sector) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, symbol);
            ps.setString(2, name);
            ps.setString(3, "STOCK"); // default assetType matching frontend demo
            ps.setString(4, null);
            ps.setString(5, null);
            return ps;
        }, kh);

        Number key = kh.getKey();
        return key == null ? null : key.longValue();
    }

    private void seedInstrumentsMinimal() {
        // ensure common instruments exist (safe to run repeatedly)
        String[][] list = {
                {"AAPL", "Apple Inc."},
                {"GOOGL", "Alphabet Inc."},
                {"NVDA", "NVIDIA Corp."},
                {"TSLA", "Tesla Inc."},
                {"AMZN", "Amazon.com Inc."},
                {"SMCI", "Super Micro Computer"},
                {"PLTR", "Palantir Technologies"},
                {"MSFT", "Microsoft Corp."}
        };
        for (String[] it : list) {
            try {
                // MySQL: attempt insert; ignore if duplicate
                jdbc.update("INSERT INTO instruments (symbol, name, asset_type) VALUES (?,?,?)",
                        it[0], it[1], "STOCK");
            } catch (Exception ignored) { /* duplicate or other - fine */ }
        }
    }

    private void seedMarketQuotes() {
        Integer cnt = safeCount("market_quotes");
        if (cnt != null && cnt > 0) return;

        Object[][] demo = {
                {"AAPL", 178.72, 1.33, "52.3M"},
                {"GOOGL", 175.45, -0.49, "28.1M"},
                {"NVDA", 875.30, 5.67, "67.8M"},
                {"TSLA", 248.50, -3.45, "89.7M"},
                {"AMZN", 185.92, 0.79, "21.4M"},
                {"SMCI", 980.50, 4.89, "45.2M"},
                {"PLTR", 75.80, 3.56, "12.3M"},
                {"MSFT", 415.00, 0.72, "33.5M"}
        };

        for (Object[] r : demo) {
            String symbol = (String) r[0];
            String name = symbol; // not used here, instrument must exist
            double price = ((Number) r[1]).doubleValue();
            double changePercent = ((Number) r[2]).doubleValue();
            String volume = (String) r[3];

            Long insId = findOrCreateInstrument(symbol, name);
            if (insId == null) continue;

            double bid = Math.max(0.01, price - Math.max(0.01, price * 0.0015));
            double ask = price + Math.max(0.01, price * 0.0015);
            Timestamp ts = Timestamp.from(OffsetDateTime.now().toInstant());

            jdbc.update("INSERT INTO market_quotes (instrument_id, price, bid, ask, change_percent, volume, timestamp) VALUES (?,?,?,?,?,?,?)",
                    insId, price, bid, ask, changePercent, volume, ts);
        }
        System.out.println("Inserted demo market_quotes.");
    }

    private void seedPositions() {
        Integer cnt = safeCountEither("positions", "position");
        if (cnt != null && cnt > 0) return;

        try {
            Long aapl = findOrCreateInstrument("AAPL", "Apple Inc.");
            Long googl = findOrCreateInstrument("GOOGL", "Alphabet Inc.");

            jdbc.update("INSERT INTO positions (instrument_id, quantity, avg_purchase_price, current_price, purchase_date, notes) VALUES (?,?,?,?,?,?)",
                    aapl, 50.0, 150.00, 178.72, Timestamp.from(OffsetDateTime.now().minusDays(30).toInstant()), "Demo position AAPL");
            jdbc.update("INSERT INTO positions (instrument_id, quantity, avg_purchase_price, current_price, purchase_date, notes) VALUES (?,?,?,?,?,?)",
                    googl, 25.0, 140.00, 175.45, Timestamp.from(OffsetDateTime.now().minusDays(60).toInstant()), "Demo position GOOGL");
            return;
        } catch (Exception ignored) {}

        try {
            // fallback: some schemas use `position`
            Long aapl = findOrCreateInstrument("AAPL", "Apple Inc.");
            Long googl = findOrCreateInstrument("GOOGL", "Alphabet Inc.");

            jdbc.update("INSERT INTO position (instrument_id, quantity, avg_purchase_price, current_price, purchase_date, notes) VALUES (?,?,?,?,?,?)",
                    aapl, 50.0, 150.00, 178.72, Timestamp.from(OffsetDateTime.now().minusDays(30).toInstant()), "Demo position AAPL");
            jdbc.update("INSERT INTO position (instrument_id, quantity, avg_purchase_price, current_price, purchase_date, notes) VALUES (?,?,?,?,?,?)",
                    googl, 25.0, 140.00, 175.45, Timestamp.from(OffsetDateTime.now().minusDays(60).toInstant()), "Demo position GOOGL");
        } catch (Exception ignored) {}
        System.out.println("Inserted demo positions.");
    }

    private void seedOrders() {
        Integer cnt = safeCount("orders");
        if (cnt != null && cnt > 0) return;

        Long aapl = findOrCreateInstrument("AAPL", "Apple Inc.");
        Long msft = findOrCreateInstrument("MSFT", "Microsoft Corp.");
        Long nvda = findOrCreateInstrument("NVDA", "NVIDIA Corp.");

        jdbc.update("INSERT INTO orders (instrument_id, side, order_type, quantity, limit_price, status, placed_at, executed_at) VALUES (?,?,?,?,?,?,?,?)",
                aapl, "BUY", "MARKET", 10.0, null, "EXECUTED", Timestamp.from(OffsetDateTime.now().minusDays(2).toInstant()), Timestamp.from(OffsetDateTime.now().minusDays(2).toInstant()));
        jdbc.update("INSERT INTO orders (instrument_id, side, order_type, quantity, limit_price, status, placed_at) VALUES (?,?,?,?,?,?,?)",
                msft, "BUY", "LIMIT", 5.0, 415.00, "EXECUTED", Timestamp.from(OffsetDateTime.now().minusDays(3).toInstant()));
        jdbc.update("INSERT INTO orders (instrument_id, side, order_type, quantity, limit_price, status, placed_at, executed_at) VALUES (?,?,?,?,?,?,?,?)",
                nvda, "SELL", "MARKET", 2.0, null, "EXECUTED", Timestamp.from(OffsetDateTime.now().minusDays(4).toInstant()), Timestamp.from(OffsetDateTime.now().minusDays(4).toInstant()));
        System.out.println("Inserted demo orders.");
    }

    private void seedWatchlist() {
        Integer cnt = safeCountEither("watchlist_items", "watchlist_item");
        if (cnt != null && cnt > 0) return;

        Long amzn = findOrCreateInstrument("AMZN", "Amazon.com Inc.");
        Long smci = findOrCreateInstrument("SMCI", "Super Micro Computer");
        Long pltr = findOrCreateInstrument("PLTR", "Palantir Technologies");

        try {
            jdbc.update("INSERT INTO watchlist_items (instrument_id, added_at) VALUES (?,?)",
                    amzn, Timestamp.from(OffsetDateTime.now().minusHours(5).toInstant()));
            jdbc.update("INSERT INTO watchlist_items (instrument_id, added_at) VALUES (?,?)",
                    smci, Timestamp.from(OffsetDateTime.now().minusHours(10).toInstant()));
            jdbc.update("INSERT INTO watchlist_items (instrument_id, added_at) VALUES (?,?)",
                    pltr, Timestamp.from(OffsetDateTime.now().minusDays(1).toInstant()));
        } catch (Exception e) {
            // try singular table name
            try {
                jdbc.update("INSERT INTO watchlist_item (instrument_id, added_at) VALUES (?,?)",
                        amzn, Timestamp.from(OffsetDateTime.now().minusHours(5).toInstant()));
                jdbc.update("INSERT INTO watchlist_item (instrument_id, added_at) VALUES (?,?)",
                        smci, Timestamp.from(OffsetDateTime.now().minusHours(10).toInstant()));
                jdbc.update("INSERT INTO watchlist_item (instrument_id, added_at) VALUES (?,?)",
                        pltr, Timestamp.from(OffsetDateTime.now().minusDays(1).toInstant()));
            } catch (Exception ignored) {}
        }
        System.out.println("Inserted demo watchlist items.");
    }

    private void seedPortfolioSnapshots() {
        Integer cnt = safeCount("portfolio_snapshots");
        if (cnt != null && cnt > 0) return;

        // Build a minimal jsonPositions based on demo positions
        String jsonPositions = "[{\"symbol\":\"AAPL\",\"quantity\":50,\"avgPurchasePrice\":150.0,\"currentPrice\":178.72},"
                + "{\"symbol\":\"GOOGL\",\"quantity\":25,\"avgPurchasePrice\":140.0,\"currentPrice\":175.45}]";

        jdbc.update("INSERT INTO portfolio_snapshots (timestamp, total_value, json_positions) VALUES (?,?,?)",
                Timestamp.from(OffsetDateTime.now().toInstant()), 50 * 178.72 + 25 * 175.45, jsonPositions);
        System.out.println("Inserted demo portfolio snapshot.");
    }

    private void seedTransactions() {
        Integer cnt = safeCount("transactions");
        if (cnt != null && cnt > 0) return;

        Long aapl = findOrCreateInstrument("AAPL", "Apple Inc.");
        jdbc.update("INSERT INTO transactions (instrument_id, type, amount, quantity, price, fee, timestamp) VALUES (?,?,?,?,?,?,?)",
                aapl, "DEBIT", 1755.0, 10.0, 175.5, 1.5, Timestamp.from(OffsetDateTime.now().minusDays(2).toInstant()));
        System.out.println("Inserted demo transactions.");
    }

    // Helpers
    private Integer safeCount(String table) {
        try {
            return jdbc.queryForObject("SELECT COUNT(*) FROM " + table, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer safeCountEither(String primary, String alt) {
        Integer c = safeCount(primary);
        if (c != null && c > 0) return c;
        return safeCount(alt);
    }
}
