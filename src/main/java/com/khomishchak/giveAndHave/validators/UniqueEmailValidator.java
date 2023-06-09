package com.khomishchak.giveAndHave.validators;

import com.khomishchak.giveAndHave.annotations.UniqueEmail;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
