package com.example.repository;

import com.example.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    public boolean existsAccountByUsername(String username);
    public boolean existsAccountByAccountId(Integer id);
    public Account findAccountByUsername(String username);
}
