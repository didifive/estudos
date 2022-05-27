package com.example.demo.service;

import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.BookingModel;
import com.example.demo.repository.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
	BookingRepository bookingRepository;
	

	public int daysCalculatorWithDatabase(String name) {
		Optional<BookingModel> bookingModelOptional = bookingRepository.findByReserveName(name);
		return Period.between(bookingModelOptional.get().getCheckIn(), bookingModelOptional.get().getCheckOut()).getDays();
	}

}
