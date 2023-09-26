package com.codecool.carrental.utils;

import com.codecool.carrental.entity.Account;
import com.codecool.carrental.entity.AuthAccount;

public class AccountMapper {
    public static AuthAccount accountToUser(Account account) {
        return new AuthAccount(
                account.getEmail(),
                account.getPassword(),
                account.getRole()
        );
    }
}
