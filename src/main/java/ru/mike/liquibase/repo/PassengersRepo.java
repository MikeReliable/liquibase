package ru.mike.liquibase.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mike.liquibase.domain.Passenger;

public interface PassengersRepo extends JpaRepository<Passenger, Integer> {

}
