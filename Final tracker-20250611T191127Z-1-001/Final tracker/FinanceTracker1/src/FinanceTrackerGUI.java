package src;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;

public class FinanceTrackerGUI {
    private JFrame frame;
    private UserManager userManager;
    private User currentUser;

    // Application data structures
    private JTextArea analyticsArea;
    private List<Transaction> transactions;
    private HashMap<String, Budget> budgets;
    private List<Debt> debts; // List to manage debts

    public FinanceTrackerGUI() {
        userManager = new UserManager();
        transactions = new ArrayList<>();
        budgets = new HashMap<>();
        debts = new ArrayList<>(); // Initialize debts

        initializeLoginScreen();
    }
        private void initializeLoginScreen() {
        // Set up frame
        frame = new JFrame("Finance Tracker - Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create main panel with background color
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));  // Light blue background color
        
        // Set up layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add welcome image at the top
        JLabel welcomeImageLabel = new JLabel(new ImageIcon("welcome_image.jpg")); // Make sure to have an image named "welcome_image.png" in your project directory
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeImageLabel, gbc);

        // Reset gridwidth for input fields
        gbc.gridwidth = 1;

        // Username label and text field
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(15);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userText, gbc);

        // Password label and password field
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passText = new JPasswordField(15);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        panel.add(passText, gbc);

        // Login and Signup buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(34, 139, 34));  // Forest green background for login
        loginButton.setForeground(Color.WHITE);
        
        JButton signupButton = new JButton("Signup");
        signupButton.setBackground(new Color(70, 130, 180));  // Steel blue background for signup
        signupButton.setForeground(Color.WHITE);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(loginButton, gbc);

        gbc.gridy = 4;
        panel.add(signupButton, gbc);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);

        // Login button action
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());
            currentUser = userManager.login(username, password);
            if (currentUser != null) {
                initializeMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid login credentials.");
            }
        });

        // Signup button action with email validation
        signupButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());
            String email = JOptionPane.showInputDialog("Enter Email:");

            // Email validation regex pattern
            if (email != null && isValidEmail(email)) {
                currentUser = userManager.signup(username, password, email);
                if (currentUser != null) {
                    initializeMainMenu();
                } else {
                    JOptionPane.showMessageDialog(frame, "Signup failed.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email address.");
            }
        });
    }

    // Utility method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }


    private void initializeMainMenu() {

        frame.getContentPane().removeAll();
        frame.setTitle("Finance Tracker - Main Menu");
        frame.setSize(500, 500);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245)); // Light grey background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton transactionButton = new JButton("Manage Transactions");
        JButton budgetButton = new JButton("Manage Budgets");
        JButton analyticsButton = new JButton("View Analytics");
        JButton debtButton = new JButton("Manage Debts");
        JButton taxButton = new JButton("Tax Calculation");
        JButton paymentButton = new JButton("List Payment Methods");
        JButton logoutButton = new JButton("Logout");

        // Add welcome label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        gbc.gridwidth = 1; // Reset for buttons
        gbc.gridy = 1;

        // Add buttons in a grid
        addButtonToPanel(transactionButton, panel, gbc, 0, 1);
        addButtonToPanel(budgetButton, panel, gbc, 1, 1);
        addButtonToPanel(analyticsButton, panel, gbc, 0, 2);
        addButtonToPanel(debtButton, panel, gbc, 1, 2);
        addButtonToPanel(taxButton, panel, gbc, 0, 3);
        addButtonToPanel(paymentButton, panel, gbc, 1, 3);
        addButtonToPanel(logoutButton, panel, gbc, 0, 4, 2);

        transactionButton.addActionListener(e -> showTransactionManagement());
        budgetButton.addActionListener(e -> showBudgetManagement());
        analyticsButton.addActionListener(e -> showAnalytics(transactions));
        debtButton.addActionListener(e -> showDebtManagement());
        taxButton.addActionListener(e -> showTaxCalculation());
        paymentButton.addActionListener(e -> showPaymentManagement());
        logoutButton.addActionListener(e -> {
            currentUser = null;
            frame.dispose();
            initializeLoginScreen();
        });
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    private void addButtonToPanel(JButton button, JPanel panel, GridBagConstraints gbc, int x, int y) {
        addButtonToPanel(button, panel, gbc, x, y, 1);
    }

    private void addButtonToPanel(JButton button, JPanel panel, GridBagConstraints gbc, int x, int y, int width) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        button.setPreferredSize(new Dimension(200, 40)); // Set button size
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        button.setBackground(new Color(100, 149, 237)); // Cornflower blue color
        button.setForeground(Color.WHITE); // Text color
        panel.add(button, gbc);
    }

    private void showTransactionManagement() {
        JDialog dialog = new JDialog((Frame) null, "Manage Transactions", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 300);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title label with custom font and color
        JLabel titleLabel = new JLabel("Manage Your Transactions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(51, 102, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        dialog.add(titleLabel, gbc);

        // Amount input field
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        dialog.add(new JLabel("Amount:"), gbc);
        JTextField amountField = new JTextField(10);
        gbc.gridx = 1;
        dialog.add(amountField, gbc);

        // Category input field
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Category:"), gbc);
        JTextField categoryField = new JTextField(10);
        gbc.gridx = 1;
        dialog.add(categoryField, gbc);

        // Subtype input field
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(new JLabel("Subtype:"), gbc);
        JTextField subtypeField = new JTextField(10);
        gbc.gridx = 1;
        dialog.add(subtypeField, gbc);

        // Add Income Transaction button with color customization
        JButton addIncomeButton = new JButton("Add Income Transaction");
        addIncomeButton.setBackground(new Color(76, 175, 80)); // Green color
        addIncomeButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        dialog.add(addIncomeButton, gbc);

        // Add Expense Transaction button with color customization
        JButton addExpenseButton = new JButton("Add Expense Transaction");
        addExpenseButton.setBackground(new Color(244, 67, 54)); // Red color
        addExpenseButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        dialog.add(addExpenseButton, gbc);

        // View Transactions button with color customization
        JButton viewButton = new JButton("View Transactions");
        viewButton.setBackground(new Color(33, 150, 243)); // Blue color
        viewButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        dialog.add(viewButton, gbc);

        // Adding Income Transaction
        addIncomeButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryField.getText();
                String subtype = subtypeField.getText();
                if (!category.isEmpty() && amount > 0 && !subtype.isEmpty()) {
                    transactions.add(new IncomeTransaction(amount, category, subtype));
                    JOptionPane.showMessageDialog(dialog, "Income transaction added.");
                    clearFields(amountField, categoryField, subtypeField);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please enter valid amount, category, and subtype.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid number for amount.");
            }
        });

        // Adding Expense Transaction
        addExpenseButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryField.getText();
                String subtype = subtypeField.getText();
                if (!category.isEmpty() && amount > 0 && !subtype.isEmpty()) {
                    transactions.add(new ExpenseTransaction(-amount, category, subtype)); // Store as negative amount
                    JOptionPane.showMessageDialog(dialog, "Expense transaction added.");
                    clearFields(amountField, categoryField, subtypeField);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please enter valid amount, category, and subtype.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid number for amount.");
            }
        });

        // Viewing Transactions
        viewButton.addActionListener(e -> {
            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No transactions available.");
            } else {
                StringBuilder message = new StringBuilder("Transactions:\n");
                for (Transaction transaction : transactions) {
                    message.append("ID: ").append(transaction.getId())
                        .append(", Type: ").append(transaction.getType())
                        .append(", Amount: ").append(transaction.getAmount())
                        .append(", Category: ").append(transaction.getCategory())
                        .append(", Subtype: ").append(transaction.getSubtype())
                        .append(", Date: ").append(transaction.getDate()).append("\n");
                }
                JOptionPane.showMessageDialog(dialog, message.toString());
            }
        });

        // Dialog settings
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true);
    }

    // Method to clear fields
    private void clearFields(JTextField amountField, JTextField categoryField, JTextField subtypeField) {
        amountField.setText("");
        categoryField.setText("");
        subtypeField.setText("");
    }
    
    
// private void showBudgetManagement() {
//     JDialog dialog = new JDialog(frame, "Manage Budgets", true);
//     dialog.setLayout(new GridBagLayout());
//     dialog.setSize(500, 400);

//     // Define components
//     String[] budgetTypes = {"Standard Budget", "Education", "SIP", "Trip"};
//     JComboBox<String> budgetTypeCombo = new JComboBox<>(budgetTypes);
//     JTextField goalField = new JTextField(10);
//     JLabel additionalLabel1 = new JLabel("Additional Info 1:");
//     JTextField additionalField1 = new JTextField(10);
//     JLabel additionalLabel2 = new JLabel("Additional Info 2:");
//     JTextField additionalField2 = new JTextField(10);
//     JButton addButton = new JButton("Add Budget");
//     JButton viewButton = new JButton("View Budgets");

//     // Color customization
//     dialog.getContentPane().setBackground(new Color(245, 245, 245));
//     addButton.setBackground(new Color(70, 130, 180));
//     addButton.setForeground(Color.WHITE);
//     viewButton.setBackground(new Color(70, 130, 180));
//     viewButton.setForeground(Color.WHITE);

//     // Layout constraints
//     GridBagConstraints gbc = new GridBagConstraints();
//     gbc.insets = new Insets(10, 10, 10, 10);
//     gbc.fill = GridBagConstraints.HORIZONTAL;
//     gbc.anchor = GridBagConstraints.WEST;

//     // Budget Type
//     gbc.gridx = 0;
//     gbc.gridy = 0;
//     dialog.add(new JLabel("Budget Type:"), gbc);
//     gbc.gridx = 1;
//     dialog.add(budgetTypeCombo, gbc);

//     // Goal Amount
//     gbc.gridx = 0;
//     gbc.gridy = 1;
//     dialog.add(new JLabel("Goal Amount:"), gbc);
//     gbc.gridx = 1;
//     dialog.add(goalField, gbc);

//     // Additional Info Field 1
//     gbc.gridx = 0;
//     gbc.gridy = 2;
//     dialog.add(additionalLabel1, gbc);
//     gbc.gridx = 1;
//     dialog.add(additionalField1, gbc);

//     // Additional Info Field 2
//     gbc.gridx = 0;
//     gbc.gridy = 3;
//     dialog.add(additionalLabel2, gbc);
//     gbc.gridx = 1;
//     dialog.add(additionalField2, gbc);

//     // Add Button
//     gbc.gridx = 0;
//     gbc.gridy = 4;
//     gbc.gridwidth = 2;
//     gbc.fill = GridBagConstraints.CENTER;
//     dialog.add(addButton, gbc);

//     // View Button
//     gbc.gridx = 0;
//     gbc.gridy = 5;
//     dialog.add(viewButton, gbc);

//     // Add action listener to budget type combo box to update additional fields
//     budgetTypeCombo.addActionListener(e -> {
//         String selectedType = (String) budgetTypeCombo.getSelectedItem();

//         // Show relevant labels and fields based on selected budget type
//         switch (selectedType) {
//             case "Education":
//                 additionalLabel1.setText("Institution Name:");
//                 additionalLabel2.setText("Months Until Goal:");
//                 additionalField1.setVisible(true);
//                 additionalField2.setVisible(true);
//                 break;
//             case "SIP":
//                 additionalLabel1.setText("SIP Fund:");
//                 additionalLabel2.setText("Duration (Months):");
//                 additionalField1.setVisible(true);
//                 additionalField2.setVisible(true);
//                 break;
//             case "Trip":
//                 additionalLabel1.setText("Destination:");
//                 additionalLabel2.setText("Trip Duration (Days):"); // Adjusted for clarity
//                 additionalField1.setVisible(true);
//                 additionalField2.setVisible(true);
//                 break;
//             default:
//                 additionalLabel1.setText("Additional Info 1:");
//                 additionalLabel2.setText("Additional Info 2:");
//                 additionalField1.setVisible(false);
//                 additionalField2.setVisible(false);
//                 break;
//         }
//     });

//     // Action listener for addButton
//     addButton.addActionListener(e -> {
//         String selectedType = (String) budgetTypeCombo.getSelectedItem();
//         double goalAmount;
//         try {
//             goalAmount = Double.parseDouble(goalField.getText());
//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(dialog, "Please enter a valid goal amount.");
//             return;
//         }

//         Budget newBudget = null;

//         if ("Education".equals(selectedType)) {
//             String institutionName = additionalField1.getText();
//             int monthsUntilGoal;
//             try {
//                 monthsUntilGoal = Integer.parseInt(additionalField2.getText());
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(dialog, "Please enter a valid number for months until goal.");
//                 return;
//             }
//             newBudget = new Education(goalAmount, institutionName, monthsUntilGoal);
//         } else if ("SIP".equals(selectedType)) {
//             double sipFund;
//             int duration;
//             try {
//                 sipFund = Double.parseDouble(additionalField1.getText());
//                 duration = Integer.parseInt(additionalField2.getText());
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for SIP Fund and duration.");
//                 return;
//             }
//             newBudget = new SIP(goalAmount, sipFund, duration);
//         } else if ("Trip".equals(selectedType)) {
//             String destination = additionalField1.getText();
//             int duration;
//             try {
//                 duration = Integer.parseInt(additionalField2.getText());
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(dialog, "Please enter a valid number for trip duration.");
//                 return;
//             }
//             Date startDate = new Date(); // Placeholder, modify to parse dates as needed
//             Date endDate = new Date(); // Also a placeholder
//             newBudget = new Trip(goalAmount, destination, startDate, endDate);
//         } else {
//             newBudget = new Budget(goalAmount);
//         }

//         if (newBudget != null) {
//             budgets.put(selectedType + ": " + (budgets.size() + 1), newBudget);
//             JOptionPane.showMessageDialog(dialog, "Budget added successfully.");
//         }
//     });

//     // Action listener for viewButton
//     viewButton.addActionListener(e -> {
//         StringBuilder message = new StringBuilder("Budgets:\n");
//         for (String key : budgets.keySet()) {
//             Budget budget = budgets.get(key);
//             message.append(key)
//                    .append(": Goal: ").append(budget.getGoalAmount())
//                    .append(", Current Spending: ").append(budget.getCurrentSpending()).append("\n");
//         }
//         JOptionPane.showMessageDialog(dialog, message.toString());
//     });

//     dialog.setVisible(true);
// }
// private void showBudgetManagement() {
//     JDialog dialog = new JDialog(frame, "Manage Budgets", true);
//     dialog.setLayout(new GridBagLayout());
//     dialog.setSize(500, 500);

//     // Define components
//     String[] budgetTypes = {"Standard Budget", "Education", "SIP", "Trip"};
//     JComboBox<String> budgetTypeCombo = new JComboBox<>(budgetTypes);
//     JTextField goalField = new JTextField(10);
//     JLabel additionalLabel1 = new JLabel("Additional Info 1:");
//     JTextField additionalField1 = new JTextField(10);
//     JLabel additionalLabel2 = new JLabel("Additional Info 2:");
//     JTextField additionalField2 = new JTextField(10);
//     JButton addButton = new JButton("Add Budget");
//     JButton viewButton = new JButton("View Budgets");
//     JButton spendButton = new JButton("Spend from Budget");
//     JTextField spendAmountField = new JTextField(10);

//     // Color customization
//     dialog.getContentPane().setBackground(new Color(245, 245, 245));
//     addButton.setBackground(new Color(70, 130, 180));
//     addButton.setForeground(Color.WHITE);
//     viewButton.setBackground(new Color(70, 130, 180));
//     viewButton.setForeground(Color.WHITE);
//     spendButton.setBackground(new Color(255, 69, 0));
//     spendButton.setForeground(Color.WHITE);

//     // Layout constraints
//     GridBagConstraints gbc = new GridBagConstraints();
//     gbc.insets = new Insets(10, 10, 10, 10);
//     gbc.fill = GridBagConstraints.HORIZONTAL;
//     gbc.anchor = GridBagConstraints.WEST;

//     // Budget Type
//     gbc.gridx = 0;
//     gbc.gridy = 0;
//     dialog.add(new JLabel("Budget Type:"), gbc);
//     gbc.gridx = 1;
//     dialog.add(budgetTypeCombo, gbc);

//     // Goal Amount
//     gbc.gridx = 0;
//     gbc.gridy = 1;
//     dialog.add(new JLabel("Goal Amount:"), gbc);
//     gbc.gridx = 1;
//     dialog.add(goalField, gbc);

//     // Additional Info Field 1
//     gbc.gridx = 0;
//     gbc.gridy = 2;
//     dialog.add(additionalLabel1, gbc);
//     gbc.gridx = 1;
//     dialog.add(additionalField1, gbc);

//     // Additional Info Field 2
//     gbc.gridx = 0;
//     gbc.gridy = 3;
//     dialog.add(additionalLabel2, gbc);
//     gbc.gridx = 1;
//     dialog.add(additionalField2, gbc);

//     // Spend Amount Field
//     gbc.gridx = 0;
//     gbc.gridy = 4;
//     dialog.add(new JLabel("Spend Amount:"), gbc);
//     gbc.gridx = 1;
//     dialog.add(spendAmountField, gbc);

//     // Add Button
//     gbc.gridx = 0;
//     gbc.gridy = 5;
//     gbc.gridwidth = 2;
//     gbc.fill = GridBagConstraints.CENTER;
//     dialog.add(addButton, gbc);

//     // View Button
//     gbc.gridy = 6;
//     dialog.add(viewButton, gbc);

//     // Spend Button
//     gbc.gridy = 7;
//     dialog.add(spendButton, gbc);

//     // Update additional fields based on budget type
//     budgetTypeCombo.addActionListener(e -> {
//         String selectedType = (String) budgetTypeCombo.getSelectedItem();

//         switch (selectedType) {
//             case "Education":
//                 additionalLabel1.setText("Institution Name:");
//                 additionalLabel2.setText("Months Until Goal:");
//                 additionalField1.setVisible(true);
//                 additionalField2.setVisible(true);
//                 break;
//             case "SIP":
//                 additionalLabel1.setText("SIP Fund:");
//                 additionalLabel2.setText("Duration (Months):");
//                 additionalField1.setVisible(true);
//                 additionalField2.setVisible(true);
//                 break;
//             case "Trip":
//                 additionalLabel1.setText("Destination:");
//                 additionalLabel2.setText("Duration (Days):");
//                 additionalField1.setVisible(true);
//                 additionalField2.setVisible(true);
//                 break;
//             default:
//                 additionalLabel1.setText("Additional Info 1:");
//                 additionalLabel2.setText("Additional Info 2:");
//                 additionalField1.setVisible(false);
//                 additionalField2.setVisible(false);
//                 break;
//         }
//     });

//     // Add Button ActionListener
//     addButton.addActionListener(e -> {
//         String selectedType = (String) budgetTypeCombo.getSelectedItem();
//         double goalAmount;

//         try {
//             goalAmount = Double.parseDouble(goalField.getText());
//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(dialog, "Please enter a valid goal amount.");
//             return;
//         }

//         Budget newBudget = null;
//         switch (selectedType) {
//             case "Education":
//                 String institutionName = additionalField1.getText();
//                 int monthsUntilGoal = Integer.parseInt(additionalField2.getText());
//                 newBudget = new Education(goalAmount, institutionName, monthsUntilGoal);
//                 break;
//             case "SIP":
//                 double sipFund = Double.parseDouble(additionalField1.getText());
//                 int duration = Integer.parseInt(additionalField2.getText());
//                 newBudget = new SIP(goalAmount, sipFund, duration);
//                 break;
//             case "Trip":
//                 String destination = additionalField1.getText();
//                 int tripDuration = Integer.parseInt(additionalField2.getText());
//                 Date startDate = new Date(); // Placeholder
//                 Date endDate = new Date(); // Placeholder
//                 newBudget = new Trip(goalAmount, destination, startDate, endDate);
//                 break;
//             default:
//                 newBudget = new Budget(goalAmount);
//                 break;
//         }

//         budgets.put(selectedType + ": " + (budgets.size() + 1), newBudget);
//         JOptionPane.showMessageDialog(dialog, "Budget added successfully.");
//     });

//     // View Button ActionListener
//     viewButton.addActionListener(e -> {
//         StringBuilder message = new StringBuilder("Budgets:\n");
//         for (String key : budgets.keySet()) {
//             Budget budget = budgets.get(key);
//             message.append(key)
//                    .append(": Goal: ").append(budget.getGoalAmount())
//                    .append(", Current Spending: ").append(budget.getCurrentSpending()).append("\n");
//         }
//         JOptionPane.showMessageDialog(dialog, message.toString());
//     });

//     // Spend Button ActionListener
//     spendButton.addActionListener(e -> {
//         String selectedBudget = (String) budgetTypeCombo.getSelectedItem();
//         Budget budget = budgets.get(selectedBudget + ": " + (budgets.size()));

//         if (budget != null) {
//             try {
//                 double amount = Double.parseDouble(spendAmountField.getText());
//                 budget.addToBudget(amount);
//                 JOptionPane.showMessageDialog(dialog, "Spent " + amount + " from " + selectedBudget + " budget.");
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(dialog, "Please enter a valid spending amount.");
//             }
//         } else {
//             JOptionPane.showMessageDialog(dialog, "No budget found for selected type.");
//         }
//     });

//     dialog.setVisible(true);
// }
    private void showBudgetManagement() {
        JDialog dialog = new JDialog(frame, "Manage Budgets", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(500, 600);

        // Define components
        String[] budgetTypes = {"Standard Budget", "Education", "SIP", "Trip"};
        JComboBox<String> budgetTypeCombo = new JComboBox<>(budgetTypes);
        JTextField goalField = new JTextField(10);
        JLabel additionalLabel1 = new JLabel("Additional Info 1:");
        JTextField additionalField1 = new JTextField(10);
        JLabel additionalLabel2 = new JLabel("Additional Info 2:");
        JTextField additionalField2 = new JTextField(10);
        JButton addButton = new JButton("Add Budget");
        JButton viewButton = new JButton("View Budgets");

        // Dropdown for selecting a budget to spend from
        JComboBox<String> budgetSelectCombo = new JComboBox<>();
        JTextField spendAmountField = new JTextField(10);
        JButton spendButton = new JButton("Spend from Budget");
        JLabel remainingLabel = new JLabel("Remaining Amount:");

        // Color customization
        dialog.getContentPane().setBackground(new Color(245, 245, 245));
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        viewButton.setBackground(new Color(70, 130, 180));
        viewButton.setForeground(Color.WHITE);
        spendButton.setBackground(new Color(255, 69, 0));
        spendButton.setForeground(Color.WHITE);

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Budget Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Budget Type:"), gbc);
        gbc.gridx = 1;
        dialog.add(budgetTypeCombo, gbc);

        // Goal Amount
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Goal Amount:"), gbc);
        gbc.gridx = 1;
        dialog.add(goalField, gbc);

        // Additional Info Field 1
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(additionalLabel1, gbc);
        gbc.gridx = 1;
        dialog.add(additionalField1, gbc);

        // Additional Info Field 2
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(additionalLabel2, gbc);
        gbc.gridx = 1;
        dialog.add(additionalField2, gbc);

        // Add Budget Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        dialog.add(addButton, gbc);

        // View Budgets Button
        gbc.gridy = 5;
        dialog.add(viewButton, gbc);

        // Budget selection for spending
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        dialog.add(new JLabel("Select Budget:"), gbc);
        gbc.gridx = 1;
        dialog.add(budgetSelectCombo, gbc);

        // Spend Amount and Remaining Balance
        gbc.gridx = 0;
        gbc.gridy = 7;
        dialog.add(new JLabel("Spend Amount:"), gbc);
        gbc.gridx = 1;
        dialog.add(spendAmountField, gbc);
        gbc.gridy = 8;
        gbc.gridx = 0;
        dialog.add(remainingLabel, gbc);

        // Spend Button
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        dialog.add(spendButton, gbc);

        // Update additional fields based on budget type
        budgetTypeCombo.addActionListener(e -> {
            String selectedType = (String) budgetTypeCombo.getSelectedItem();

            switch (selectedType) {
                case "Education":
                    additionalLabel1.setText("Institution Name:");
                    additionalLabel2.setText("Months Until Goal:");
                    additionalField1.setVisible(true);
                    additionalField2.setVisible(true);
                    break;
                case "SIP":
                    additionalLabel1.setText("SIP Fund:");
                    additionalLabel2.setText("Duration (Months):");
                    additionalField1.setVisible(true);
                    additionalField2.setVisible(true);
                    break;
                case "Trip":
                    additionalLabel1.setText("Destination:");
                    additionalLabel2.setText("Duration (Days):");
                    additionalField1.setVisible(true);
                    additionalField2.setVisible(true);
                    break;
                default:
                    additionalLabel1.setText("Additional Info 1:");
                    additionalLabel2.setText("Additional Info 2:");
                    additionalField1.setVisible(false);
                    additionalField2.setVisible(false);
                    break;
            }
        });

        // Add Budget Button ActionListener
        addButton.addActionListener(e -> {
            String selectedType = (String) budgetTypeCombo.getSelectedItem();
            double goalAmount;

            try {
                goalAmount = Double.parseDouble(goalField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid goal amount.");
                return;
            }

            Budget newBudget = null;
            String budgetName = selectedType + " - " + (budgets.size() + 1);

            switch (selectedType) {
                case "Education":
                    String institutionName = additionalField1.getText();
                    int monthsUntilGoal = Integer.parseInt(additionalField2.getText());
                    newBudget = new Education(goalAmount, institutionName, monthsUntilGoal);
                    break;
                case "SIP":
                    double sipFund = Double.parseDouble(additionalField1.getText());
                    int duration = Integer.parseInt(additionalField2.getText());
                    newBudget = new SIP(goalAmount, sipFund, duration);
                    break;
                case "Trip":
                    String destination = additionalField1.getText();
                    int tripDuration = Integer.parseInt(additionalField2.getText());
                    Date startDate = new Date(); // Placeholder
                    Date endDate = new Date(); // Placeholder
                    newBudget = new Trip(goalAmount, destination, startDate, endDate);
                    break;
                default:
                    newBudget = new Budget(goalAmount);
                    break;
            }

            budgets.put(budgetName, newBudget);
            budgetSelectCombo.addItem(budgetName);
            JOptionPane.showMessageDialog(dialog, "Budget added successfully.");
        });

        // View Budgets Button ActionListener
        viewButton.addActionListener(e -> {
            StringBuilder message = new StringBuilder("Budgets:\n");
            for (String key : budgets.keySet()) {
                Budget budget = budgets.get(key);
                message.append(key)
                    .append(": Goal: ").append(budget.getGoalAmount())
                    .append(", Current Spending: ").append(budget.getCurrentSpending())
                    .append(", Remaining: ").append(budget.getGoalAmount() - budget.getCurrentSpending());
        
                // Check if it's a Trip budget and add the destination name
                if (budget instanceof Trip) {
                    Trip trip = (Trip) budget;
                    message.append(", Destination: ").append(trip.getDestination());
                }
        
                message.append("\n");
            }
            JOptionPane.showMessageDialog(dialog, message.toString());
        });

        // Update remaining amount on budget selection
        budgetSelectCombo.addActionListener(e -> {
            String selectedBudget = (String) budgetSelectCombo.getSelectedItem();
            if (selectedBudget != null) {
                Budget budget = budgets.get(selectedBudget);
                if (budget != null) {
                    remainingLabel.setText("Remaining Amount: " + (budget.getGoalAmount() - budget.getCurrentSpending()));
                }
            }
        });

        // Spend Button ActionListener
        spendButton.addActionListener(e -> {
            String selectedBudget = (String) budgetSelectCombo.getSelectedItem();
            if (selectedBudget != null) {
                Budget budget = budgets.get(selectedBudget);
                if (budget != null) {
                    try {
                        double amount = Double.parseDouble(spendAmountField.getText());
                        if (budget.getCurrentSpending() + amount <= budget.getGoalAmount()) {
                            budget.addToBudget(amount);
                            remainingLabel.setText("Remaining Amount: " + (budget.getGoalAmount() - budget.getCurrentSpending()));
                            JOptionPane.showMessageDialog(dialog, "Spent " + amount + " from " + selectedBudget + " budget.");
                        } else {
                            JOptionPane.showMessageDialog(dialog, "Amount exceeds remaining budget.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Please enter a valid spending amount.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Please select a budget to spend from.");
            }
        });

        dialog.setVisible(true);
    }


    
    private void showAnalytics(List<Transaction> transactions) {
        JDialog dialog = new JDialog(frame, "Monthly Analytics", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 200);
        
        double totalIncome = transactions.stream().filter(t -> t.getAmount() > 0).mapToDouble(Transaction::getAmount).sum();
        double totalExpense = transactions.stream().filter(t -> t.getAmount() < 0).mapToDouble(Transaction::getAmount).sum();
    
        JTextArea analyticsArea = new JTextArea(5, 20);
        analyticsArea.setText("Income: Rs." + totalIncome + "\nExpenses: Rs." + Math.abs(totalExpense) + "\nBalance: Rs." + (totalIncome + totalExpense));
        dialog.add(analyticsArea);
    
        dialog.setVisible(true);
    }





    //after adding chart for analytics
    // private void showAnalytics(List<Transaction> transactions) {
    //     JDialog dialog = new JDialog(frame, "Monthly Analytics", true);
    //     dialog.setLayout(new BorderLayout());
    //     dialog.setSize(500, 400);
    
    //     // Calculate income, expenses, and balance
    //     double totalIncome = transactions.stream().filter(t -> t.getAmount() > 0).mapToDouble(Transaction::getAmount).sum();
    //     double totalExpense = transactions.stream().filter(t -> t.getAmount() < 0).mapToDouble(Transaction::getAmount).sum();
    //     double balance = totalIncome + totalExpense;
    
    //     // Create a dataset for the pie chart
    //     DefaultPieDataset dataset = new DefaultPieDataset();
    //     dataset.setValue("Total Income", totalIncome);
    //     dataset.setValue("Total Expense", Math.abs(totalExpense));
    //     dataset.setValue("Balance", balance);
    
    //     // Create the pie chart
    //     JFreeChart pieChart = ChartFactory.createPieChart("Monthly Analytics", dataset, true, true, false);
    //     PiePlot plot = (PiePlot) pieChart.getPlot();
    //     plot.setCircular(true);
    //     plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
    //     plot.setNoDataMessage("No data to display");
    
    //     // Add the chart to a panel
    //     ChartPanel chartPanel = new ChartPanel(pieChart);
    //     dialog.add(chartPanel, BorderLayout.CENTER);
    
    //     // Text area for detailed info
    //     JTextArea analyticsArea = new JTextArea(5, 20);
    //     analyticsArea.setEditable(false);
    //     analyticsArea.setText("Income: Rs." + totalIncome + "\nExpenses: Rs." + Math.abs(totalExpense) + "\nBalance: Rs." + balance);
    //     dialog.add(analyticsArea, BorderLayout.SOUTH);
    
    //     dialog.setVisible(true);
    // }
    
    

    private void showDebtManagement() {
        JDialog dialog = new JDialog(frame, "Manage Debts", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 300);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField loanAmountField = new JTextField(10);
        JTextField interestRateField = new JTextField(10);
        JTextField monthsField = new JTextField(10);
        JButton addDebtButton = new JButton("Add Debt");
        JButton viewDebtsButton = new JButton("View Debts");
        
        // Set colors for the dialog
        dialog.getContentPane().setBackground(Color.LIGHT_GRAY);
        addDebtButton.setBackground(Color.GREEN);
        viewDebtsButton.setBackground(Color.BLUE);
        addDebtButton.setForeground(Color.WHITE);
        viewDebtsButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Loan Amount:"), gbc);

        gbc.gridx = 1;
        dialog.add(loanAmountField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Interest Rate (%):"), gbc);

        gbc.gridx = 1;
        dialog.add(interestRateField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Months:"), gbc);

        gbc.gridx = 1;
        dialog.add(monthsField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(addDebtButton, gbc);

        gbc.gridx = 1;
        dialog.add(viewDebtsButton, gbc);
        
        addDebtButton.addActionListener(e -> {
            try {
                double loanAmount = Double.parseDouble(loanAmountField.getText()); // Parse input to double
                double interestRate = Double.parseDouble(interestRateField.getText()); // Parse input to double
                int months = Integer.parseInt(monthsField.getText()); // Parse input to int
                debts.add(new Debt(loanAmount, interestRate, months)); // Create new Debt object
                JOptionPane.showMessageDialog(dialog, "Debt added.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for loan amount, interest rate, and months.");
            }
        });

        viewDebtsButton.addActionListener(e -> {
            StringBuilder message = new StringBuilder("Debts:\n");
            for (Debt debt : debts) {
                double emi = debt.calculateEMI();
                message.append("Loan Amount: Rs.").append(debt.getLoanAmount())
                    .append(", Interest Rate: ").append(debt.getInterestRate())
                    .append("%, Months: ").append(debt.getMonths())
                    .append(", EMI: Rs.").append(String.format("%.4f", emi)).append("\n");
            }
            JOptionPane.showMessageDialog(dialog, message.toString());
        });

        // viewDebtsButton.addActionListener(e -> {
        //     StringBuilder message = new StringBuilder("Debts:\n");
        //     for (Debt debt : debts) {
        //         message.append("Loan Amount: Rs.").append(debt.getLoanAmount())
        //             .append(", Interest Rate: ").append(debt.getInterestRate())
        //             .append("%, Months: ").append(debt.getMonths())
        //             .append(", EMI: Rs.").append(debt.calculateEMI()).append("\n");
        //     }
        //     JOptionPane.showMessageDialog(dialog, message.toString());
        // });

        dialog.setVisible(true);
    }

    // Tax Calculation Window
    private void showTaxCalculation() {
        JDialog dialog = new JDialog(frame, "Tax Calculation", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(300, 200);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField incomeField = new JTextField(10);
        JButton calculateButton = new JButton("Calculate Tax");

        // Set colors for the dialog
        dialog.getContentPane().setBackground(Color.LIGHT_GRAY);
        calculateButton.setBackground(Color.ORANGE);
        calculateButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Income:"), gbc);

        gbc.gridx = 1;
        dialog.add(incomeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Button spans both columns
        dialog.add(calculateButton, gbc);

        calculateButton.addActionListener(e -> {
            try {
                double income = Double.parseDouble(incomeField.getText());
                double tax = calculateTax(income); // You can implement a more complex calculation here
                JOptionPane.showMessageDialog(dialog, "Calculated Tax: Rs." + tax);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid number for income.");
            }
        });

        dialog.setVisible(true);
    }

    private double calculateTax(double income) {
        // Simple tax calculation (for example, 20% of income)
        return income * 0.20;
    }

    

    private void showPaymentManagement() {
        JDialog dialog = new JDialog(frame, "List Payment Methods", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 300);
        
        // Define layout constraints for positioning components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField paymentMethodField = new JTextField(15);
        JButton addPaymentButton = new JButton("Add Payment Method");
        JButton viewPaymentButton = new JButton("View Payment Methods");
        List<String> paymentMethods = new ArrayList<>(); // To hold linked payment methods
    
        // Set colors for the dialog and buttons for a modern look
        dialog.getContentPane().setBackground(Color.LIGHT_GRAY);
        addPaymentButton.setBackground(new Color(34, 139, 34)); // Forest green
        addPaymentButton.setForeground(Color.WHITE);
        viewPaymentButton.setBackground(new Color(70, 130, 180)); // Steel blue
        viewPaymentButton.setForeground(Color.WHITE);
    
        // Position components in the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Payment Method:"), gbc);
    
        gbc.gridx = 1;
        dialog.add(paymentMethodField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(addPaymentButton, gbc);
    
        gbc.gridx = 1;
        dialog.add(viewPaymentButton, gbc);
    
        // Add action listeners for button functionalities
        addPaymentButton.addActionListener(e -> {
            String paymentMethod = paymentMethodField.getText();
            if (!paymentMethod.isEmpty()) {
                paymentMethods.add(paymentMethod);
                JOptionPane.showMessageDialog(dialog, "Payment method added.");
                paymentMethodField.setText(""); // Clear input after adding
            } else {
                JOptionPane.showMessageDialog(dialog, "Please enter a payment method.");
            }
        });
    
        viewPaymentButton.addActionListener(e -> {
            if (!paymentMethods.isEmpty()) {
                StringBuilder message = new StringBuilder("Payment Methods:\n");
                for (String method : paymentMethods) {
                    message.append("- ").append(method).append("\n");
                }
                JOptionPane.showMessageDialog(dialog, message.toString());
            } else {
                JOptionPane.showMessageDialog(dialog, "No payment methods added.");
            }
        });
    
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FinanceTrackerGUI());
    }
}
