import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;;


class BankAccount{
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static HashMap<String,Integer> account_numbers = new HashMap<>(); 
    static Random rand = new Random();
    static Scanner sc1 = new Scanner(System.in);

    protected double balance;
    protected String account_number;
    protected int account_pin;

    public BankAccount( double initial_balance ) {
        System.out.println("Create your pin : ");
        account_pin = sc1.nextInt();
        balance = initial_balance;
        while( true ) {
            String number = "XYZ"+ (int)(rand.nextDouble(1)*10000000);
            if( account_numbers.get(number)==null ) {
                account_number = number;
                account_numbers.put( account_number , account_pin );
                break;
            }
        }
        accounts.add( this );
        System.out.println("Bank Account created successfully !!");
    }

    public String getBankAccountNumber(){
        return account_number;
    }

    protected double getBalance() {
        return balance;
    }

    public static BankAccount getBankAccount( String account_number , int account_pin ) {
        for( int i=0 ; i<accounts.size() ; i++ ) {
            if( accounts.get(i).account_number.equals(account_number) && accounts.get(i).account_pin==account_pin ) {
                return accounts.get(i);
            }
        }
        return null;
    }
}


class ATM{
    private static Scanner sc;
    private BankAccount b;

    ATM( BankAccount b ){
       this.b = b;
       sc = new Scanner( System.in );
    }

    public void withdraw( double amount ) {
        if( b.balance<amount ) {
           System.out.println("Insuffiecient Balanace\n");
        }
        else{
           b.balance -= amount;
           System.out.println("Rs: "+amount+" has been debited from your account !!");
        }
     }
 
     public void checkBalance() {
        System.out.println("Your account's current balance : "+b.balance+"\n");
     }
 
     public void deposit( double amount ) {
        b.balance += amount;
        System.out.println("Rs: "+amount+" has been credited into your successfully !!");
     }

    public void menu(){
        while( true ) {
            System.out.println("\n\n<============ XYZ BANK ==============>");
            System.out.println("Enter 4 to 'Withdraw' ");
            System.out.println("Enter 5 to 'Deposit'");
            System.out.println("Enter 6 to 'Check Balance'");
            System.out.println("Enter 7 to 'log out' from account");

            int choice = sc.nextInt();

            switch( choice ) {
                case 4 :  System.out.print("Enter the amount to be withdrawn : ");
                          double withdrawl_amt = sc.nextDouble();
                          withdraw( withdrawl_amt ); 
                          break;
                case 5 :  System.out.print("Enter the amount to be deposited : ");
                          double credit_amt = sc.nextDouble(); 
                          deposit( credit_amt ); 
                          break;
                case 6 :  checkBalance(); break;
                case 7 :  System.out.println("Logged out from account");
                          return;
                default : System.out.println("Invalid Choice -- Enter Valid Choice");
            }
        }
    }
}


public class Main{
    private static Scanner sc3 = new Scanner( System.in );

    public static void main(String[] args) {
         while(true ) {
            System.out.println("\n\n<=========== XYZ ==========>");
            System.out.println("Enter 1 to create account");
            System.out.println("Enter 2 to login into your account");
            System.out.println("Enter 3 to exit");
            int choice = sc3.nextInt();
            switch( choice ) {
                case 1 : System.out.println("Enter the initial Deposit amount : ");
                         double initial_balance = sc3.nextDouble();
                         BankAccount b1 = new BankAccount(initial_balance);
                         System.out.println("Your Bank Account number = "+b1.getBankAccountNumber() );
                         break;
                case 2 : System.out.println("Enter your bank account number : ");
                         String account_number = sc3.next();
                         System.out.println("Enter your pin : ");
                         int account_pin = sc3.nextInt();
                         BankAccount b2 = BankAccount.getBankAccount( account_number , account_pin );
                         if( b2==null ) {
                            System.out.println("No such Bank accounts actually exist!!\nEnter valid bank account number and account pin ");
                         }
                         else {
                            ATM atm_obj = new ATM( b2 );
                            atm_obj.menu();
                         }
                         break;
                case 3 : return;
                default : System.out.println("Invalid Choice -- Enter Valid Choice");
            }
         }
    }
}