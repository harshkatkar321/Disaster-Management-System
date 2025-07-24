package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.SuperAdmin;
import com.app.enums.Role;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, String> {
	
	Optional<SuperAdmin> findByUsername(String username);
	
	boolean existsByRole(Role role);

}
