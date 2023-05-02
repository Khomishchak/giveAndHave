package com.khomishchak.giveAndHave.validators;

import com.khomishchak.giveAndHave.annotations.UniqueName;
import com.khomishchak.giveAndHave.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    private UserRepository userRepository;

    @Autowired
    public UniqueNameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt) {
        return userRepository != null && userRepository.findByName(name).isPresent();
    }
}
