package ru.mike.liquibase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mike.liquibase.domain.Passenger;
import ru.mike.liquibase.repo.PassengersRepo;

import java.util.List;

@Controller
public class LiquibaseController {

    @Autowired
    PassengersRepo passengersRepo;

    @GetMapping(path = "list")
    public String main(@ModelAttribute Passenger passenger,
                       @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
                       @RequestParam(value = "pageNumber",defaultValue = "0") Integer pageNumber,
//                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 50) Pageable pageable,
                       Model model) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<Passenger> passengerList = passengersRepo.findAll(pageable);
        int totalPages = passengerList.getTotalPages();
//        int pageNumber = pageable.getPageNumber();

        model.addAttribute("passengerList", passengerList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "index";
    }

}
