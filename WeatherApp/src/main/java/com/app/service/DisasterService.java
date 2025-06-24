package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.model.DisasterEntity;
import com.app.repository.DisasterRepository;

@Service
public class DisasterService {

    @Autowired
    private DisasterRepository disasterRepo;

    public void fetchAndStoreDisasters() {
        String url = "https://eonet.gsfc.nasa.gov/api/v3/events/geojson";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> features = (List<Map<String, Object>>) response.get("features");

        for (Map<String, Object> feature : features) {
            Map<String, Object> geometry = (Map<String, Object>) feature.get("geometry");
            Map<String, Object> properties = (Map<String, Object>) feature.get("properties");

            if (geometry == null || properties == null) continue;

            List<Double> coords = (List<Double>) geometry.get("coordinates");
            if (coords == null || coords.size() < 2) continue;

            List<Map<String, Object>> categories = (List<Map<String, Object>>) properties.get("categories");
            if (categories == null || categories.isEmpty()) continue;

            String title = (String) properties.get("title");
            String category = (String) categories.get(0).get("title");
            String dateStr = (String) properties.get("date");

            LocalDateTime date = LocalDateTime.parse(dateStr.replace("Z", ""));

            DisasterEntity entity = new DisasterEntity();
            entity.setTitle(title);
            entity.setCategory(category);
            entity.setLatitude(coords.get(1));
            entity.setLongitude(coords.get(0));
            entity.setDate(date);
            entity.setSourceId((String) feature.get("id"));

            disasterRepo.save(entity);
        }
    }
    public List<DisasterEntity> getAllDisasters() {
        return disasterRepo.findAll();
    }
}
