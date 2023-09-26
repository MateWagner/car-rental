package com.codecool.carrental.service;

import com.codecool.carrental.entity.Account;
import com.codecool.carrental.repository.AccountRepository;
import com.codecool.carrental.utils.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDerailServiceImp implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                "User doesn't exist with username: " + username
        ));
        return AccountMapper.accountToUser(account);
    }
}
