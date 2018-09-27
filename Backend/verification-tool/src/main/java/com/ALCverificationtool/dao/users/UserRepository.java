package com.ALCverificationtool.dao.users;

import com.ALCverificationtool.models.UserRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserRec, Long> {

    Optional<UserRec> findByUsername(String username);
}
