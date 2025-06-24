package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.model.DisasterEntity;
import com.app.service.DisasterService;

@Controller
public class DisasterController {

    @Autowired
    private DisasterService disasterService;

    @GetMapping("/disasters/fetch")
    @ResponseBody
    public String fetchDisasters() {
        disasterService.fetchAndStoreDisasters();
        return "Disaster data fetched and saved.";
    }

    @GetMapping("/api/disasters")
    @ResponseBody
    public List<DisasterEntity> getDisasters() {
        return disasterService.getAllDisasters();
    }
}

