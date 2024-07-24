package ru.mike.liquibase.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mike.liquibase.domain.Passenger;

public interface PassengersRepo extends JpaRepository<Passenger, Integer> {

    Page<Passenger> findAllByNameContains(String name, Pageable pageable);

    Page<Passenger> findAllByNameContainsAndSurvived(String name, boolean survived, Pageable pageable);

    Page<Passenger> findAllBySurvived(boolean survived, Pageable pageable);

    Page<Passenger> findAllBySexEquals(String male, Pageable pageable);

    Page<Passenger> findAllBySiblingsSpousesAboardEqualsAndParentsChildrenAboardEquals(int siblingsSpousesAboard, int parentsChildrenAboard, Pageable pageable);

}
