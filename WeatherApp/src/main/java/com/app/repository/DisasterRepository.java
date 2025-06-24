package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.DisasterEntity;

@Repository
public interface DisasterRepository extends JpaRepository<DisasterEntity,Integer> {

}
