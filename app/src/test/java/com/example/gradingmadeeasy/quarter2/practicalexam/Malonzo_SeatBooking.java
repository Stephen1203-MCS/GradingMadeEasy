package com.example.sampleapplicationfordemo.quarter2.practicalexam;

import java.util.*;

public class CinemeSeatBooking {
    static final int ROWS = 8; // A - H
    static final int COLS = 10; // 1 - 10
    static final int PREMIUM_START_ROW = 0; // rows A-B are premium
    static final int PREMIUM_END_ROW = 1;
    static final double REGULAR_PRICE = 220;
    static final double PREMIUM_PRICE = 320;

    static boolean[][] seats = new boolean[ROWS][COLS];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        randomlyOccupySomeSeats();

        System.out.println("=========================================");
        System.out.println(" CINEME SEAT BOOKING ");
        System.out.println("=========================================");
        System.out.println("Rows A-B: PREMIUM (₱" + fmt(PREMIUM_PRICE) + ")");
        System.out.println("Rows C-H: REGULAR (₱" + fmt(REGULAR_PRICE) + ")");

        List<String> selected = new ArrayList<>();
        boolean booking = true;

        while (booking) {
            printSeatMap();
            System.out.print("\nEnter seat to select (e.g. A5), or 0 to finish: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("0")) {
                booking = false;
                break;
            }

            int[] rc = parseSeat(input);
            if (rc == null) {
                System.out.println("Invalid seat format. Use A-H + 1-10, e.g. C7.");
                continue;
            }

            int row = rc[0], col = rc[1];
            if (seats[row][col]) {
                System.out.println("Seat " + input + " is already taken. Choose another.");
                continue;
            }

            seats[row][col] = true;
            selected.add(input);
            System.out.println("Seat " + input + " reserved.");
        }

        checkout(selected);
        scanner.close();
    }

    static void randomlyOccupySomeSeats() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int r = rand.nextInt(ROWS);
            int c = rand.nextInt(COLS);
            seats[r][c] = true;
        }
    }

    static void printSeatMap() {
        System.out.println("\n " + screenLabel());
        System.out.println();
        for (int r = 0; r < ROWS; r++) {
            StringBuilder sb = new StringBuilder();
            char rowLetter = (char) ('A' + r);
            sb.append(rowLetter).append(" ");
            for (int c = 0; c < COLS; c++) {
                if (seats[r][c]) {
                    sb.append(" X ");
                } else if (r >= PREMIUM_START_ROW && r <= PREMIUM_END_ROW) {
                    sb.append(" P ");
                } else {
                    sb.append(" o ");
                }
            }
            System.out.println(sb.toString());
        }
        System.out.print(" ");
        for (int c = 1; c <= COLS; c++) {
            System.out.printf("%3d", c);
        }
        System.out.println("\n\nLegend: o = available P = premium available X = taken");
    }

    static String screenLabel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < COLS * 2; i++) sb.append("=");
        return "[ " + sb.toString() + " SCREEN ]";
    }

    static int[] parseSeat(String input) {
        if (input.length() < 2 || input.length() > 3) return null;
        char letter = input.charAt(0);
        if (letter < 'A' || letter >= 'A' + ROWS) return null;
        String numPart = input.substring(1);
        int col;
        try {
            col = Integer.parseInt(numPart);
        } catch (NumberFormatException e) {
            return null;
        }
        if (col < 1 || col > COLS) return null;
        return new int[]{letter - 'A', col - 1};
    }

    static double priceForRow(int row) {
        return (row >= PREMIUM_START_ROW && row <= PREMIUM_END_ROW)? PREMIUM_PRICE : REGULAR_PRICE;
    }

    static void checkout(List<String> selected) {
        System.out.println("\n=========================================");
        System.out.println(" BOOKING SUMMARY");
        System.out.println("=========================================");
        if (selected.isEmpty()) {
            System.out.println("No seats selected.");
            return;
        }
        double total = 0;
        for (String seat : selected) {
            int[] rc = parseSeat(seat);
            double price = priceForRow(rc[0]);
            String type = (rc[0] >= PREMIUM_START_ROW && rc[0] <= PREMIUM_END_ROW)? "Premium" : "Regular";
            System.out.printf(" Seat %-5s %-10s ₱%s%n", seat, type, fmt(price));
            total += price;
        }
        System.out.println("-----------------------------------------");
        System.out.printf(" %-17s ₱%s%n", "TOTAL", fmt(total));
        System.out.println("=========================================");
        System.out.println("Enjoy your movie at Cineme!");
    }

    static String fmt(double price) {
        return String.format("%.0f", price);
    }
}