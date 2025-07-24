package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.KafkaService;

@RestController
@RequestMapping("/location")
public class LocationController {
	
	@Autowired
	private KafkaService kafkaService;
	
	
	
	@PostMapping("/update")
	public ResponseEntity<?> updateLocation(){
		
		
		for(int i=1; i<=10000000;i++) {
		this.kafkaService.updatelocation("("+ Math.round(Math.random() * 100) +" ," + Math.round(Math.random() * 100) +" "+")");
		}
		return new ResponseEntity<>(Map.of("message", "Location updated"), HttpStatus.OK);
	}
	
	
	
	
	

}
