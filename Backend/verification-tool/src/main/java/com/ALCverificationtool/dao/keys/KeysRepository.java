package com.ALCverificationtool.dao.keys;

import com.ALCverificationtool.models.keysRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeysRepository extends JpaRepository<keysRec, Long> {

    Optional<keysRec> findByKeyName(String username);

}