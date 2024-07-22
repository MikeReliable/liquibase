package ru.mike.liquibase.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mike.liquibase.domain.Passenger;

import java.util.List;

public interface PassengersRepo extends JpaRepository<Passenger, Integer> {
//    Page<Passenger> findAll(Pageable pageable);
    Page<Passenger> findAllByNameMatchesRegex(String nameRegex, Pageable pageable);
    Page<Passenger> findAllByNameContains(String nameRegex, Pageable pageable);


}
