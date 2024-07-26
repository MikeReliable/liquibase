package ru.mike.liquibase.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mike.liquibase.domain.Passenger;

import java.util.List;

public interface PassengersRepo extends JpaRepository<Passenger, Integer> {

    List<Passenger> findAllByNameContains(String name);

}
