// Budget class
package src;
import java.util.*;
class Budget {
    private double goalAmount;
    private double currentSpending;

    public Budget(double goalAmount) {
        this.goalAmount = goalAmount;
        this.currentSpending = 0;
    }
    public double getAmount() {
        return goalAmount;
    }
    

    public void addToBudget(double amount) {
        currentSpending += amount;
        if (currentSpending > goalAmount) {
            System.out.println("Warning: You have exceeded your budget goal!");
        } else {
            System.out.println("Budget updated. Remaining: " + (goalAmount - currentSpending));
        }
    }

    public double getGoalAmount() { return goalAmount; }
    public double getCurrentSpending() { return currentSpending; }
}

// Education class extending Budget
class Education extends Budget {
    private String institutionName;
    private int monthsUntilGoal;

    public Education(double goalAmount, String institutionName, int monthsUntilGoal) {
        super(goalAmount);
        this.institutionName = institutionName;
        this.monthsUntilGoal = monthsUntilGoal;
    }

    public void updateMonthsUntilGoal(int months) {
        this.monthsUntilGoal = months;
        System.out.println("Months until goal updated to: " + monthsUntilGoal);
    }

    public void checkProgress() {
        double remainingAmount = getGoalAmount() - getCurrentSpending();
        if (remainingAmount <= 0) {
            System.out.println("Congratulations! You have reached your education savings goal for " + institutionName + ".");
        } else {
            System.out.println("Remaining to save for " + institutionName + ": " + remainingAmount + " over " + monthsUntilGoal + " months.");
            System.out.println("Monthly target: " + (remainingAmount / monthsUntilGoal));
        }
    }
}



class SIP extends Budget {
    private double sipFund;
    private int duration; // Duration in months

    public SIP(double goalAmount, double sipFund, int duration) {
        super(goalAmount); // Initialize the goal amount in the Budget class
        this.sipFund = sipFund;
        this.duration = duration;
    }

    public void updateDuration(int newDuration) {
        this.duration = newDuration;
        System.out.println("SIP duration updated to: " + duration + " months");
    }

    public void checkSIPProgress() {
        double remainingAmount = getGoalAmount() - getCurrentSpending();
        if (remainingAmount <= 0) {
            System.out.println("Congratulations! You have achieved your SIP goal of " + sipFund);
        } else {
            System.out.println("Remaining amount for SIP goal: " + remainingAmount);
            if (duration > 0) {
                System.out.println("Monthly investment target: " + (remainingAmount / duration));
            }
        }
    }

    public double getSipFund() { return sipFund; }
    public int getDuration() { return duration; }
}


// Trip class extending Budget
class Trip extends Budget {
    private String destination;
    private Date startDate;
    private Date endDate;

    public Trip(double goalAmount, String destination, Date startDate, Date endDate) {
        super(goalAmount);
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public String getDestination() {
        return destination;
    }

    public void displayTripDetails() {
        System.out.println("Trip to " + destination);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Budget Goal: " + getGoalAmount());
        System.out.println("Current Spending: " + getCurrentSpending());
    }

    public void checkTripProgress() {
        double remainingAmount = getGoalAmount() - getCurrentSpending();
        if (remainingAmount <= 0) {
            System.out.println("Congratulations! You have reached your trip savings goal for " + destination + ".");
        } else {
            System.out.println("Remaining to save for trip to " + destination + ": " + remainingAmount);
        }
    }
}
