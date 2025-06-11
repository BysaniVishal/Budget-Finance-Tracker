package src;
import java.util.*;

// Debt Management
public class Debt {
    private double loanAmount;
    private double interestRate;
    private int months;

    public Debt(double loanAmount, double interestRate, int months) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.months = months;
    }

    public double calculateEMI() {
        double rate = interestRate / 12 / 100;
        return (loanAmount * rate * Math.pow(1 + rate, months)) / (Math.pow(1 + rate, months) - 1);
    }

    // Getter methods
    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getMonths() {
        return months;
    }
}



// Analytics Class
class Analytics {
    private List<Transaction> transactions;

    public Analytics(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void generateMonthlyReport() {
        double income = 0;
        double expenses = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("income")) income += transaction.getAmount();
            else expenses += transaction.getAmount();
        }
        System.out.println("Monthly Income: " + income + ", Expenses: " + expenses);
        if (expenses > income) {
            System.out.println("Alert: Your expenses exceeded your income!");
        }
    }
}
