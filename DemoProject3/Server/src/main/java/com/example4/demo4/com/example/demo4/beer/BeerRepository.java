package com.example4.demo4.com.example.demo4.beer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
interface BeerRepository extends JpaRepository<Beer, Long> {
}