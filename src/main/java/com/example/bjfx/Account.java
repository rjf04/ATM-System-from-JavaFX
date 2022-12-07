package com.example.bjfx;

interface genLam<T>{        //generic interface without a pre-defined return type
    T func();
}
public class Account{
    private int accountId;
    private float balance;
    private String password;

    public Account(int accountId){
        this.accountId=accountId;
        this.balance = 0;
        this.password="1111";
    }
    public Account(int accountId, String password){         //constructor overloaded
        this.accountId=accountId;
        this.balance=0;
        this.password=password;
    }
    public Account(int accountId, float balance, String password) {          //constructor overloaded
        this.accountId = accountId;
        this.balance = balance;
        this.password=password;
    }
    genLam<Integer> acId=()->  accountId;           //lambda expressions
    genLam<Float> bal=()-> balance;
    genLam<String> pw=()->  password;

    public void withDrawal(float amount){
        if(balance>amount){
            balance = balance - amount;
        }
    }

    public void deposit(float amount){
        balance=balance+amount;
    }

    public void changePin(String pin){
        password=pin;
    }
}