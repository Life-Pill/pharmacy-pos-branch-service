package com.lifePill.posbranchservice.repository;

import com.lifePill.posbranchservice.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BranchRepository extends JpaRepository<Branch,Integer> {
    boolean existsByBranchEmail(String branchEmail);
}
