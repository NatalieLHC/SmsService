package com.example.test.validator;

public class PhoneNumberValidator implements Validator{
    String phoneNumber;

    public PhoneNumberValidator(String phoneNumber) {
        this.phoneNumber=phoneNumber;
    }

    @Override
    public boolean isValid() {
        if (( this.phoneNumber.startsWith("5") ||this.phoneNumber.startsWith("995")) &&
                (this.phoneNumber.length()==9 || this.phoneNumber.length()==11)){
            return true;
        } else
            return false;
    }

    @Override
    public String getMessage() {
        return "Invalid Phone Number";
    }
}
