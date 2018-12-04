package com.ALCverificationtool.dao.logs;

import com.ALCverificationtool.models.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogsRepository extends JpaRepository<Logs, UUID> {
}
