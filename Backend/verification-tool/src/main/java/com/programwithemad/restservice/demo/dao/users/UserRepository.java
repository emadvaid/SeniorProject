package com.programwithemad.restservice.demo.dao.users;

import com.programwithemad.restservice.demo.models.UserRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserRec, String> {
}
