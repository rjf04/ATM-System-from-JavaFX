package com.example.bjfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class AppController {
    //initialising elements from the fxml file
    @FXML
    private TextArea displayTextArea;

    @FXML
    private Button zeroButton;

    @FXML
    private Button oneButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button sevenButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button nineButton;

    @FXML
    private ImageView cashImageView;

    private String numbersEntered = "";

    private int selectedAccountId;

    private int optionSelected;

    private int check = 0;      //Makes sure that the password display page appears only after account ID page

    private int empty = 0;      //Ensures menu is displayed only when valid ID and password are entered
    private EventHandler<ActionEvent> keyboardListener = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            empty = 1;
            Button sourceComponent = (Button) actionEvent.getSource();
            String number = sourceComponent.getText();
            boolean wasShowBalanceOptionSelected = number.equals("1") && selectedAccountId != 0 && optionSelected == 0;
            boolean wasWithdrawOptionSelected = number.equals("2") && selectedAccountId != 0 && optionSelected == 0;
            boolean wasDepositOptionSelected = number.equals("3") && selectedAccountId != 0 && optionSelected == 0;
            boolean wasChangePinOptionSelected = number.equals("4") && selectedAccountId != 0 && optionSelected == 0;
            if (wasShowBalanceOptionSelected) {
                float balance = ATM.getInstance().showBalance(selectedAccountId);
                displayTextArea.clear();
                displayTextArea.setText("YOUR ACCOUNT BALANCE IS: " + balance);
                numbersEntered = "";
            } else if (wasWithdrawOptionSelected) {
                displayTextArea.clear();
                numbersEntered = "";
                displayTextArea.setText("PLEASE ENTER THE AMOUNT TO WITHDRAW: \n");
                optionSelected = 2;
            } else if (wasDepositOptionSelected) {
                displayTextArea.clear();
                numbersEntered = "";
                displayTextArea.setText("PLEASE ENTER THE AMOUNT TO DEPOSIT: \n");
                optionSelected = 3;
            } else if (wasChangePinOptionSelected) {
                displayTextArea.clear();
                numbersEntered = "";
                displayTextArea.setText("PLEASE ENTER THE NEW PASSWORD: \n");
                optionSelected = 4;
            } else {
                numbersEntered = numbersEntered + number;
                displayTextArea.appendText(number);
                if (check == 0 && numbersEntered.length() == 4) {
                    displayPassword();
                }
            }
        }
    };

    @FXML
    public void initialize()  {
        displayWelcomeMessage();
        showMoney(true);
        zeroButton.setOnAction(keyboardListener);
        oneButton.setOnAction(keyboardListener);
        twoButton.setOnAction(keyboardListener);
        threeButton.setOnAction(keyboardListener);
        fourButton.setOnAction(keyboardListener);
        fiveButton.setOnAction(keyboardListener);
        sixButton.setOnAction(keyboardListener);
        sevenButton.setOnAction(keyboardListener);
        eightButton.setOnAction(keyboardListener);
        nineButton.setOnAction(keyboardListener);

    }

    public void okButtonListener() throws FileNotFoundException {
        if (numbersEntered.equals("")) {
            showMoney(true);
            if (empty == 1)     //used to prevent empty strings
                displayMenu();
            return;
        }
        if (optionSelected == 1) {
            showMoney(true);
            optionSelected = 0;
        }
        if (optionSelected == 2) {
            float amount = Float.parseFloat(numbersEntered);
            numbersEntered = "";
            showMoney(false);
            float balance = ATM.getInstance().showBalance(selectedAccountId);
            if (amount > balance)
                displayTextArea.setText("Withdrawal unsuccessful due to insufficient balance.\nPRESS OK TO GO BACK TO MENU \n");
            else displayTextArea.setText("Withdrawal Successful\nPRESS OK TO GO BACK TO MENU \n");
            ATM.getInstance().withDraw(selectedAccountId, amount);
            optionSelected = 0;
            return;
        }
        if (optionSelected == 3) {
            float amount = Float.parseFloat(numbersEntered);
            numbersEntered = "";
            showMoney(false);
            displayTextArea.setText("Rs." + amount + " deposited successfully\nPRESS OK TO GO BACK TO MENU \n");
            ATM.getInstance().depo(selectedAccountId, amount);
            optionSelected = 0;
            return;
        }
        if (optionSelected == 4) {
            String pin = numbersEntered;
            numbersEntered = "";
            displayTextArea.setText("Your password has been successfully changed");
            ATM.getInstance().pinChange(selectedAccountId, pin);
            optionSelected = 0;
            return;
        }
        int accountId = Integer.parseInt(numbersEntered.substring(0, 4));
        boolean valid=Pattern.matches("[0-9]{4}",numbersEntered.substring(4 ));     //Regular expression to check if password is b/w 0 & 9 and has a length of 4
        if(valid){
            String password = numbersEntered.substring(4, 8);
            if (ATM.getInstance().isAccountValid(accountId, password)) {
            selectedAccountId = accountId;
            numbersEntered = "";
            displayMenu();
        }else {
                displayErrorInvalidAccount();
            }
        }
        else {
            displayErrorInvalidAccount();
        }

    }

    public void cancelButtonListener()  {
        empty = 0;
        optionSelected = 1;
        initialize();
    }

    public void clearButtonListener()  {
        numbersEntered = "";
        if (optionSelected == 2) {
            displayTextArea.clear();
            displayTextArea.setText("PLEASE ENTER THE AMOUNT TO WITHDRAW: \n");
        }
        if (optionSelected == 3) {
            displayTextArea.clear();
            displayTextArea.setText("PLEASE ENTER THE AMOUNT TO DEPOSIT: \n");
        }
        if (optionSelected == 4) {
            displayTextArea.clear();
            displayTextArea.setText("PLEASE ENTER THE NEW PASSWORD: \n");
        }
    }

    public void exitButtonListener() {
        Platform.exit();
    }

    private void displayErrorInvalidAccount() {
        displayTextArea.clear();
        displayTextArea.setText(" ERROR : ACCOUNT INVALID\nPLEASE TRY AGAIN AFTER HITTING CANCEL");
        optionSelected = 1;
        check = 1;
        numbersEntered = "";
    }

    private void displayPassword() {

        displayTextArea.setText("ENTER THE PASSWORD:");
        check = 1;
    }

    private void displayMenu() {
        displayTextArea.clear();
        displayTextArea.setText("       MENU          \n" +
                "    1. SHOW BALANCE  \n" +
                "    2. WITHDRAW      \n" +
                "    3. DEPOSIT       \n" +
                "    4. CHANGE PASSWORD\n");

    }

    private void displayWelcomeMessage() {
        displayTextArea.setText("WELCOME TO THE ATM!! \n" +
                "PLEASE ENTER YOUR ACCOUNT ID:\n");
        check = 0;
    }

    private void showMoney(boolean empty) {         //try-catch block to demonstrate exception handling
        try {
            FileInputStream imageLocation = null;
            if (empty) {
                imageLocation = new FileInputStream("C:\\Users\\lenovo\\IdeaProjects\\bjfx\\target\\images.png");
            } else {
                imageLocation = new FileInputStream("C:\\Users\\lenovo\\IdeaProjects\\bjfx\\target\\download.png");
            }
            cashImageView.setImage(new Image(imageLocation));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}