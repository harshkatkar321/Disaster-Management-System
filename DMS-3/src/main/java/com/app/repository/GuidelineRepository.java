package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Guideline;

@Repository
public interface GuidelineRepository extends JpaRepository<Guideline, Integer> {
	
	List<Guideline> findByKeywordContainingIgnoreCase(String keyword);

//	List<String> findDistinctKeyword();
	 @Query("SELECT DISTINCT g.keyword FROM Guideline g")
	 List<String> findAllDistinctKeywords();
	 
	 @Query("SELECT g.message FROM Guideline g WHERE g.keyword = :keyword")
	 List<String> findMessagesByKeyword(@Param("keyword") String keyword);
	 
	 List<String> findMessageByKeywordContainingIgnoreCase(String keyword);
}
