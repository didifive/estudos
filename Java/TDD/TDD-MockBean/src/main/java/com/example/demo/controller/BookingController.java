package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BookingModel;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	
	@GetMapping()
	@ResponseBody
	public String getAll() {
		return "Ok";
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<BookingModel> save(@RequestBody BookingModel bookingModel) {
		return ResponseEntity.status(HttpStatus.OK).body(bookingModel);
	}
}
