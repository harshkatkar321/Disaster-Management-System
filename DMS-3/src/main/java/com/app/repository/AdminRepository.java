package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Admin;
import com.app.entity.User;
import com.app.enums.Role;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	
	Optional<Admin> findByUsername(String username);
	
	List<User> findByCityContainingIgnoreCase(String city);

//	User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.role = :role")
	List<Admin> findAllByRole(@Param("role") Role role);
	
	boolean existsByRole(Role role);

}
