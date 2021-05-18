package com.adesso.contract.consumer.controller;

import com.adesso.contract.consumer.model.LeapYearResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.annotation.PostConstruct;

@RestController
public class YearCheckerController {

	private final Logger logger = LoggerFactory.getLogger(YearCheckerController.class);
	private WebClient webClient;

	@PostConstruct
	private void initWebClient() {
		webClient = WebClient.builder()
				.baseUrl("http://localhost:7000")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	@GetMapping(value = "/check-leap-year", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LeapYearResponse> checkLeap(@RequestParam(value = "year", required = false) Integer year) {
		try {
			final LeapYearResponse leapYearResponse = webClient.get()
					.uri(uriBuilder -> {
						uriBuilder = uriBuilder.path("/leap-year");
						if (year != null) {
							uriBuilder = uriBuilder.queryParam("year", year);
						}
						return uriBuilder.build();
					})
					.retrieve()
					.bodyToMono(LeapYearResponse.class)
					.block();
			return ResponseEntity.ok(leapYearResponse);
		} catch (WebClientResponseException e) {
			logger.warn("Could not receive response while checking leap year", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
