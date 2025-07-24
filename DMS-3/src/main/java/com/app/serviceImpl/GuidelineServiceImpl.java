package com.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.GuidelineDto;
import com.app.entity.Guideline;
import com.app.repository.GuidelineRepository;
import com.app.service.GuidelineService;

@Service
public class GuidelineServiceImpl implements GuidelineService {
	
	@Autowired
	private GuidelineRepository guidelineRepository;

	@Override
	public Guideline addGuideline(GuidelineDto dto) {
		// TODO Auto-generated method stub
		Guideline guideline = new Guideline();
		guideline.setMessage(dto.getMessage());
		guideline.setKeyword(dto.getKeyword());
		return guidelineRepository.save(guideline);
	}

	@Override
	public List<Guideline> getByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return guidelineRepository.findByKeywordContainingIgnoreCase(keyword);
	}

	@Override
	public List<String> getAllKeywords() {
		// TODO Auto-generated method stub
		return guidelineRepository.findAllDistinctKeywords();
	}

	@Override
	public List<String> getMessagesByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return guidelineRepository.findMessagesByKeyword(keyword);
	}

}
