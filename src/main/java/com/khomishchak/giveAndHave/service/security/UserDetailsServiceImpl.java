package com.khomishchak.giveAndHave.service.security;

import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.UserRepository;
import com.khomishchak.giveAndHave.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found"));

        return new UserDetailsImpl(user);
    }
}
