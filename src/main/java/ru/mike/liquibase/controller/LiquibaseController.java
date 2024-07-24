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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mike.liquibase.domain.Passenger;
import ru.mike.liquibase.repo.PassengersRepo;

import java.util.List;
import java.util.stream.Collectors;

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
                       @RequestParam(value = "name", defaultValue = " ") String name,
                       @RequestParam(value = "survived", required = false) boolean survived,
                       Model model) {
        Page<Passenger> passengerPage;
        if (direction.equals("ASC")) {
            Pageable pageableASC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, pageSort));
            passengerPage = passengersRepo.findAllByNameContains(name, pageableASC);
        } else {
            Pageable pageableDESC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, pageSort));
            passengerPage = passengersRepo.findAllByNameContains(name, pageableDESC);
        }
        List<Passenger> passengerList = passengersRepo.findAllByNameContains(name);
        int survivedPassengers = (int) passengerList.stream()
                .filter(Passenger::isSurvived)
                .count();
        int hasRelatives = (int) passengerList.stream()
                .filter(p -> p.getParentsChildrenAboard() > 0 || p.getSiblingsSpousesAboard() > 0)
                .count();
        Double fareTotal = passengerList.stream().mapToDouble(Passenger::getFare).sum();

        int totalPages = passengerPage.getTotalPages();
        model.addAttribute("passengerList", passengerPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageSort", pageSort);
        model.addAttribute("direction", direction);
        model.addAttribute("name", name);
        model.addAttribute("survived", survived);
        model.addAttribute("survivedPassengers", survivedPassengers);
        model.addAttribute("hasRelatives", hasRelatives);
        model.addAttribute("fareTotal", fareTotal);
        return "index";
    }

    @PostMapping(path = "list")
    public String listSorted(@ModelAttribute Passenger passenger,
                             @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
                             @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                             @RequestParam(value = "pageSort", defaultValue = "id") String pageSort,
                             @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                             @RequestParam(value = "name", defaultValue = " ") String name,
                             @RequestParam(value = "survived", required = false) boolean survived,
                             Model model) {
        Page<Passenger> passengerPage;
        if (direction.equals("ASC")) {
            Pageable pageableASC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, pageSort));
            passengerPage = passengersRepo.findAllByNameContains(name, pageableASC);
        } else {
            Pageable pageableDESC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, pageSort));
            passengerPage = passengersRepo.findAllByNameContains(name, pageableDESC);
        }
        List<Passenger> passengerList = passengersRepo.findAllByNameContains(name);
        int survivedPassengers = (int) passengerList.stream()
                .filter(Passenger::isSurvived)
                .count();
        int hasRelatives = (int) passengerList.stream()
                .filter(p -> p.getParentsChildrenAboard() > 0 || p.getSiblingsSpousesAboard() > 0)
                .count();
        Double fareTotal = passengerList.stream().mapToDouble(Passenger::getFare).sum();

        int totalPages = passengerPage.getTotalPages();
        model.addAttribute("passengerList", passengerPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageSort", pageSort);
        model.addAttribute("direction", direction);
        model.addAttribute("name", name);
        model.addAttribute("survived", survived);
        model.addAttribute("survivedPassengers", survivedPassengers);
        model.addAttribute("hasRelatives", hasRelatives);
        model.addAttribute("fareTotal", fareTotal);
        return "index";
    }

    @PostMapping(path = "listFiltered")
    public String listFiltered(@ModelAttribute Passenger passenger,
                               @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
                               @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                               @RequestParam(value = "pageSort", defaultValue = "id") String pageSort,
                               @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                               @RequestParam(value = "name", defaultValue = " ") String name,
                               @RequestParam(value = "survived", required = false) boolean survived,
                               Model model) {
        Page<Passenger> passengerPage;
        if (direction.equals("ASC")) {
            Pageable pageableASC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, pageSort));
            passengerPage = passengersRepo.findAllByNameContains(name, pageableASC);
        } else {
            Pageable pageableDESC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, pageSort));
            passengerPage = passengersRepo.findAllByNameContains(name, pageableDESC);
        }
        int totalPages = passengerPage.getTotalPages();
        model.addAttribute("passengerList", passengerPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageSort", pageSort);
        model.addAttribute("direction", direction);
        model.addAttribute("name", name);
        model.addAttribute("survived", survived);
        return "index";
    }

}
