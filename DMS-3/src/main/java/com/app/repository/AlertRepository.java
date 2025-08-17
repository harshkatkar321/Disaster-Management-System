package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Alert;
import com.app.entity.User;

@Repository
public interface AlertRepository extends JpaRepository<Alert, String> {
	
	//List<User> findByCityContainingIgnoreCase(String city);
	
	List<Alert> findByLocationContainingIgnoreCase(String location);

}
