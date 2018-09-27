package com.ALCverificationtool.dao.authentication;

import com.ALCverificationtool.models.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, String> {
}
