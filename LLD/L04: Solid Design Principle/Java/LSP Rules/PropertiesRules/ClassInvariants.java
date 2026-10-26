// Class Invariant of a parent class Object should not be broken by child class Object.
// Hence child class can either maintain or strengthen the invariant but never narrows it down.

// Invariant: Balance cannot be negative
class BankAccount {
    protected double balance;

    public BankAccount(double b) {
        if (b < 0) throw new IllegalArgumentException("Balance can't be negative");
        balance = b;
    }

    public void withdraw(double amount) {
        if (balance - amount < 0) throw new RuntimeException("Insufficient funds");
        balance -= amount;
        System.out.println("Amount withdrawn. Remaining balance is " + balance);
    }
}

// Breaks invariant: Should not be allowed.
class CheatAccount extends BankAccount {
    public CheatAccount(double b) {
        super(b);
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount; // LSP break! Negative balance allowed
        System.out.println("Amount withdrawn. Remaining balance is " + balance);
    }
}

public class ClassInvariants {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(100);
        bankAccount.withdraw(100);
    }
}

