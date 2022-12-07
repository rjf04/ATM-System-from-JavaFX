package com.example.bjfx;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATM
{
    private static ATM INSTANCE;

    public static ATM getInstance(){
        if(INSTANCE==null){
            INSTANCE = new ATM();
        }
        return INSTANCE;
    }

    private Map<Integer, Account> accounts;     //Collections framework

    private ATM(){
        accounts = new HashMap<>();
        Account account1 = new Account(1111, 1000, "9999");
        Account account2 = new Account(2222, 2000, "8888");
        Account account3 = new Account(3333, "7777");
        Account account4 = new Account(4444);
        accounts.put(account1.acId.func(),account1);
        accounts.put(account2.acId.func(),account2);
        accounts.put(account3.acId.func(),account3);
        accounts.put(account4.acId.func(),account4);
    }

    public boolean isAccountValid(int accountId, String password){
        Account account = accounts.get(accountId);
        if(account == null){
            return false;
        }else{
            Pattern pat=Pattern.compile(password);              //regular expressions
            Matcher mat=pat.matcher(account.pw.func());
            if(mat.matches())
                return true;
        }
        return false;
    }
    public float showBalance(int accountId){
        Account account = accounts.get(accountId);
        if(account != null){
            return account.bal.func();
        }else{
            return -1f;
        }
    }

    public void withDraw(int accountId, float amount){
        Account account = accounts.get(accountId);
        if(account != null){
            account.withDrawal(amount);
        }
    }

    public void depo(int accountId, float amount){
        Account account = accounts.get(accountId);
        if(account != null){
            account.deposit(amount);
        }
    }

    public void pinChange(int accountId, String pin){
        Account account = accounts.get(accountId);
        account.changePin(pin);
    }
}