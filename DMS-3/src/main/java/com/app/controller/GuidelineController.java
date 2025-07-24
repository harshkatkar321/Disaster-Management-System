package com.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.GuidelineDto;
import com.app.entity.Guideline;
import com.app.service.GuidelineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class GuidelineController {
	
	@Autowired
	private GuidelineService guidelineService;
	
	@PostMapping("/guidelines")
	public ResponseEntity<?> addGuideline(@RequestBody @Valid GuidelineDto dto,  BindingResult br){
		
		 if (br.hasErrors()) {
		        var errors = br.getFieldErrors().stream()
		            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
		            .toList();
		        return ResponseEntity.badRequest().body(Map.of("errors", errors));
		    }
		
		try {
			Guideline guideline = guidelineService.addGuideline(dto);
			return new ResponseEntity<>(guideline, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/guidelines/keyword/{keyword}")
	public ResponseEntity<?> getByKeyword(@PathVariable String keyword){
		try {
			List<Guideline> guideline = guidelineService.getByKeyword(keyword);
			return new ResponseEntity<>(guideline, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping("/guidelines/keyword")
	public ResponseEntity<?> getAllKeywords(){
		try {
			List<String> allKeywords = guidelineService.getAllKeywords();
			return new ResponseEntity<>(allKeywords, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
		}
	}
	
	 @GetMapping("/guidelines/messages/{keyword}")
	    public ResponseEntity<?> getMessages(@PathVariable String keyword) {
		 
		 try {
				List<String> messagesByKeyword = guidelineService.getMessagesByKeyword(keyword);
				return new ResponseEntity<>(messagesByKeyword, HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
			}
	    }

}
