package com.adesso.contract.producer.controller;

import com.adesso.contract.producer.model.LeapYearResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeapYearController {

	@GetMapping(value = "/leap-year", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public LeapYearResponse checkLeap(@RequestParam(value = "year") int year) {
		final LeapYearResponse response = new LeapYearResponse();
		final boolean isLeap = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
		response.setLeap(isLeap);
		return response;
	}

}
