package src;
public class IncomeTransaction extends Transaction {
    private String source; // Source of income, e.g., salary, bonus

    public IncomeTransaction(double amount, String category, String subtype) {
        super(amount, category, subtype);
    }


    @Override
    public String getType() {
        return "Income";
    }
}