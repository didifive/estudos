package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.BookingModel;

public interface BookingRepository extends JpaRepository<BookingModel, String>{

	Optional<BookingModel> findByReserveName(String name);
}
