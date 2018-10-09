package com.ALCverificationtool.dao.users;

import com.ALCverificationtool.models.UserRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserRec, UUID> {

    Optional<UserRec> findByUsername(String username);
}
