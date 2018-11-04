package com.ALCverificationtool.dao.version;

import com.ALCverificationtool.models.VerRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VersionRepository extends JpaRepository <VerRec, UUID> {
    Optional<VerRec> findByRawVerNum(String verNum);

    @Transactional
    void deleteVerRecByRawVerNum(String verNum);
}