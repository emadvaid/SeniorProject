package com.programwithemad.restservice.demo.dao;

import com.programwithemad.restservice.demo.models.DealerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerProfileRepository extends JpaRepository<DealerProfile, Integer> {
}
