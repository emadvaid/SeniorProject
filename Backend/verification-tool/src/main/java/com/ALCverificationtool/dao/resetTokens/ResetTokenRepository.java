package com.ALCverificationtool.dao.resetTokens;

import com.ALCverificationtool.models.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, UUID> {
}
