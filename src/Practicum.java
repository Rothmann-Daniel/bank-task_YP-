import org.w3c.dom.ls.LSOutput;

public class Practicum {
    public static void main(String[] args) {
        String debit = "debit_account";
        String credit = "credit_account";
        String rub = "RUB";
        String usd = "USD";
        String eur = "EUR";
        Bank sber = new Bank();
        Bank raif = new Bank();
        Bank alfa = new Bank();


        BankAccount alfaAccount = alfa.createNewAccount(credit, eur);
        alfaAccount.withdrawCash(560);
        alfaAccount.replenishBalance(1000);
        alfaAccount.showBalance();
        alfa.closeAccount(alfaAccount);
        System.out.println();

        BankAccount sberDebAccount = sber.createNewAccount(debit, rub);
        sberDebAccount.showBalance();
        sberDebAccount.replenishBalance(1000);
        BankAccount sberCredit = sber.createNewAccount(credit, rub);
        sberCredit.showBalance();
        sberCredit.withdrawCash(5896);
        sberCredit.showBalance();
        sberCredit.replenishBalance(5896);
        sberCredit.showBalance();
        sber.closeAccount(sberCredit);
        sber.closeAccount(sberDebAccount);
        sber.closeAccount(alfaAccount);

    }
}

class BankAccount {

    protected int amount;
    protected String currency;

    public void replenishBalance(int amount) {
        this.amount += amount;
        System.out.println("Счет пополнен на " + amount + " " + currency);
    }

    public void withdrawCash(int amount) {
    }

    public void showBalance() {
    }
}

class DebitAccount extends BankAccount {

    public DebitAccount(int amount, String currency) {
        if (amount < 0) {
            System.out.println("Баланс дебетового счета не может быть меньше 0");
        } else {
            this.amount = amount;
            this.currency = currency;
        }
    }

    @Override
    public void withdrawCash(int amount) {
        if (amount > this.amount) {
            System.out.println("У вас недостаточно средств для снятия суммы " + amount + " " + currency);
        } else {
            this.amount -= amount;
            System.out.println("Вы сняли " + amount + " " + currency);
        }
    }

    @Override
    public void showBalance() {
        System.out.println("На вашем счету осталось " + amount + " " + currency);
    }
}

class CreditAccount extends BankAccount {

    public int creditLimit;

    public CreditAccount(int amount, String currency, int creditLimit) {
        this.amount = amount;
        this.currency = currency;
        this.creditLimit = creditLimit;
    }

    @Override
    public void withdrawCash(int amount) {
        if (this.amount - amount < -creditLimit) {
            System.out.println("У вас недостаточно средств для снятия суммы " + amount + " " + currency);
        } else {
            this.amount -= amount;
            System.out.println("Вы сняли " + amount + " " + currency);
        }
    }

    @Override
    public void showBalance() {
        if (amount >= 0) {
            System.out.println("На вашем счету " + amount + " " + currency);
        } else {
            System.out.println("Ваша задолженность по кредитному счету составялет " + Math.abs(amount) + currency);
        }
    }
}

class Bank {

    public BankAccount createNewAccount(String account, String currency) {
        switch (account) {
            case "debit_account":
                DebitAccount debitAccount = new DebitAccount(0, currency);
                System.out.println("Ваш дебетовый счет создан");
                return debitAccount;

            case "credit_account":
                int limit = calculateCreditLimit(currency);
                CreditAccount creditAccount = new CreditAccount(0, currency, limit);
                System.out.println("Кредитный счет создан. Ваш лимит по счету " + limit + " " + currency);
                return creditAccount;

            default:
                System.out.println("Неверно указан тип создаваемого счета");
                return new BankAccount();
        }
    }


    public BankAccount closeAccount(BankAccount account) {
        if (account instanceof DebitAccount) {
            if (account.amount > 0) {
                System.out.println("Ваш дебетовый счет закрыт. Вы можете получить остаток по вашему счету в размере " + account.amount + " " + account.currency + " в отделении банка");
            } else {
                System.out.println("Ваш дебетовый счет закрыт");
            }
        } else if (account instanceof CreditAccount) {
            if (account.amount == 0) {
                System.out.println("Ваш кредитный счет закрыт");
            } else if (account.amount > 0) {
                System.out.println("Ваш кредитный счет закрыт. Вы можете получить остаток по вашему счету в размере " + account.amount + " " + account.currency + " в отделении банка");
            } else {
                System.out.println("Вы не можете закрыть кредитный счет потому как на нем еще есть задолженность. Ваша задолженность по счету составляет " + account.amount + " " + account.currency);
            }
        } else {
            System.out.println("Пока что мы не можем закрыть данный вид счета");
        }
        return account;
    }


    private int calculateCreditLimit(String currency) {
        int creditLimit = 0;
        switch (currency) {
            case "RUB":
                creditLimit = 100000;
                break;
            case "USD":
                creditLimit = 1250;
                break;
            case "EUR":
                creditLimit = 1000;
                break;
            default:
                break;
        }
        return creditLimit;
    }
}




