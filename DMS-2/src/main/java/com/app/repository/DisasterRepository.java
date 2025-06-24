package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Disaster;

@Repository
public interface DisasterRepository extends JpaRepository<Disaster, String> {
	
	List<Disaster> findByLocationContainingIgnoreCase(String location);
	
	List<Disaster> findByLocation(String location);
	
	List<Disaster> findByIsVerifiedFalseAndLocationContainingIgnoreCase(String location);

}
