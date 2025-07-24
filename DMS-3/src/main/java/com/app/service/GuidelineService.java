package com.app.service;

import java.util.List;

import com.app.dto.GuidelineDto;
import com.app.entity.Guideline;

public interface GuidelineService {
	
	Guideline addGuideline(GuidelineDto dto);
	
	List<Guideline> getByKeyword(String keyword);
	
	List<String> getAllKeywords();
	
	public List<String> getMessagesByKeyword(String keyword);

}
