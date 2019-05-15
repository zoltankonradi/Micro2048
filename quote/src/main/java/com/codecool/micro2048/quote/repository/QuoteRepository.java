package com.codecool.micro2048.quote.repository;


import com.codecool.micro2048.quote.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

}
