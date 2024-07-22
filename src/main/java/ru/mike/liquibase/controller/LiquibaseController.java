package ru.mike.liquibase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mike.liquibase.domain.Passenger;
import ru.mike.liquibase.repo.PassengersRepo;

@Controller
public class LiquibaseController {

    @Autowired
    PassengersRepo passengersRepo;

    @GetMapping(path = "list")
    public String main(@ModelAttribute Passenger passenger,
                       @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
                       @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                       @RequestParam(value = "pageSort", defaultValue = "id") String pageSort,
                       @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                       Model model) {
        Page<Passenger> passengerList;
        if (direction.equals("ASC")) {
            Pageable pageableASC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, pageSort));
            passengerList = passengersRepo.findAll(pageableASC);
        } else {
            Pageable pageableDESC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, pageSort));
            passengerList = passengersRepo.findAll(pageableDESC);
        }
        int totalPages = passengerList.getTotalPages();
        model.addAttribute("passengerList", passengerList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageSort", pageSort);
        model.addAttribute("direction", direction);
        return "index";
    }

}
