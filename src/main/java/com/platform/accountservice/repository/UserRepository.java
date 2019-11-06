package com.platform.accountservice.repository;


import com.platform.accountservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        //UserEntity findUserByEmail(String email);

        User findByEmail(String email);
        
        User findByUsername(String username);
}
