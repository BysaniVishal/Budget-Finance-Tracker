package src;
import java.util.Date;

// Base class for transactions
public abstract class Transaction {
    private static int idCounter = 0; // Static counter for unique ID
    private int id;
    private double amount;
    private String category; // Main category (Income or Expense)
    private String subtype; // User-defined subtype
    private Date date;

    public Transaction(double amount, String category, String subtype) {
        this.id = ++idCounter;
        this.amount = amount;
        this.category = category;
        this.subtype = subtype;
        this.date = new Date(); // Set current date
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getSubtype() {
        return subtype;
    }

    public Date getDate() {
        return date;
    }

    public abstract String getType(); // Abstract method to get transaction type
}
