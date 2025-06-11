// Subclass for Expense Transactions
package src;
public class ExpenseTransaction extends Transaction {
    private String vendor; // Vendor for the expense, e.g., grocery store

    public ExpenseTransaction(double amount, String category, String subtype) {
        super(amount, category, subtype);
    }
    @Override
    public String getType() {
        return "Expense";
    }
}
