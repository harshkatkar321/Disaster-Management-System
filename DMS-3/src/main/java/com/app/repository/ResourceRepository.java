package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Resource;
import com.app.enums.ResourceKind;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, String> {
	
	Optional<Resource> findByUsername(String username);
	
	 List<Resource> findByKind(ResourceKind kind);
	
	List<Resource> findByCityContainingIgnoreCase(String city);

	@Query("SELECT u.phoneNumber FROM Resource u WHERE LOWER(u.city) LIKE LOWER(CONCAT('%', :city, '%'))")
	List<String> findPhoneNumberByCityContainingIgnoreCase(@Param("city") String city);
	
	
	@Query("SELECT u.username FROM Resource u WHERE LOWER(u.city) LIKE LOWER(CONCAT('%', :city, '%'))")
	List<String> findUsernameByCityContainingIgnoreCase(@Param("city") String city);

}
