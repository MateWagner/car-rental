package com.codecool.carrental.service;

import com.codecool.carrental.entity.Account;
import com.codecool.carrental.repository.AccountRepository;
import com.codecool.carrental.security.Roles;
import com.codecool.carrental.utils.AccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImpTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private UserDetailsServiceImp userDetailsService;

    @Test
    public void loadUserByUsernameUserNotFound() {
        when(accountRepository.findByEmail("user")).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("user"));
        verify(accountRepository, times(1)).findByEmail("user");
    }

    @Test
    public void loadUserByUsernameAndGetBackUserDetails() {
        Account account = new Account(
                "email",
                "password",
                Roles.ADMIN
        );
        UserDetails user = AccountMapper.accountToUser(account);

        when(accountRepository.findByEmail("email")).thenReturn(Optional.of(account));
        assertEquals(user, userDetailsService.loadUserByUsername("email"));
        verify(accountRepository, times(1)).findByEmail("email");
    }
}
