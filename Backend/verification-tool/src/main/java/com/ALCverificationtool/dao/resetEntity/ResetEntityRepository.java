package com.ALCverificationtool.dao.resetEntity;

import com.ALCverificationtool.models.ResetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetEntityRepository extends JpaRepository<ResetEntity, Long> {
}
