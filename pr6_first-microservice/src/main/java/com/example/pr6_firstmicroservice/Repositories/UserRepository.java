package com.example.pr6_firstmicroservice.Repositories;

import com.example.pr6_firstmicroservice.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String Nickname);

}
