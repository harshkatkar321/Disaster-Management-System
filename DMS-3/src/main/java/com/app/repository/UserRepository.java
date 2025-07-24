package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.User;
import com.app.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByUsername(String username);
	
	List<User> findByCityContainingIgnoreCase(String city);

//	User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.role = :role")
	List<User> findAllByRole(@Param("role") Role role);
	
	boolean existsByRole(Role role);
	
	List<String> findPhoneNumberByCityContainingIgnoreCase(String city);
	
//	List<String> findUsernameByCityContainingIgnoreCase(String city);
	
	@Query("SELECT u.username FROM User u WHERE LOWER(u.city) LIKE LOWER(CONCAT('%', :city, '%'))")
	List<String> findUsernameByCityContainingIgnoreCase(@Param("city") String city);

}
