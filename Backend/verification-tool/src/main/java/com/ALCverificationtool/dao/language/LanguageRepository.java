package com.ALCverificationtool.dao.language;

import com.ALCverificationtool.models.LangRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<LangRec, UUID> {

    Optional<LangRec> findBylangName(String langName);
}
