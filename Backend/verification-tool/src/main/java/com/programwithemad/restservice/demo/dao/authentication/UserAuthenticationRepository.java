package com.programwithemad.restservice.demo.dao.authentication;

import com.programwithemad.restservice.demo.models.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, String> {
}
