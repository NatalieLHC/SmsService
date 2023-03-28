package com.example.test.service;

import com.example.test.exception.ValidatorException;
import com.example.test.validator.Validator;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {
    public  void validate(Validator validator) throws ValidatorException {
        if (!validator.isValid()) {
            throw new ValidatorException(validator.getMessage());

        }
    }
}
