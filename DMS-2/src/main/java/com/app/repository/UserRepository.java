package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Role;
import com.app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	List<User> findByLocationContainingIgnoreCase(String location);

	User findByEmail(String email);
	
//	@Query("SELECT u FROM User u WHERE u.role = 'ADMIN'")
//	  List<User> findAdmins();
	
	@Query("SELECT u FROM User u WHERE u.role = :role")
	List<User> findAllByRole(@Param("role") Role role);
	
	boolean existsByRole(Role role);

}
