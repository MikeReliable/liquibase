package ru.mike.liquibase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.mike.liquibase.domain.Passenger;
import ru.mike.liquibase.repo.PassengersRepo;

import java.util.List;

@Controller
public class LiquibaseController {

    @Autowired
    PassengersRepo passengersRepo;

    @GetMapping(path = "list")
    public String main( @ModelAttribute Passenger passenger,
                       Model model) {
        List<Passenger> passengerList = passengersRepo.findAll();
        model.addAttribute("passengerList", passengerList);
        return "index";
    }

}
