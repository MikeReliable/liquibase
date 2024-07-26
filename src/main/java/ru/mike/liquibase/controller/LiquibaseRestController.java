package ru.mike.liquibase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mike.liquibase.domain.Filter;
import ru.mike.liquibase.domain.Passenger;
import ru.mike.liquibase.repo.PassengersRepo;
import ru.mike.liquibase.service.LiquibaseService;

import java.util.List;

@RestController
public class LiquibaseRestController {

    @Autowired
    PassengersRepo passengersRepo;

    @Autowired
    LiquibaseService liquibaseService;

    @Cacheable(cacheNames = "passenger")
    @PostMapping(path = "list")
    public ResponseEntity<Page<Passenger>> listSorted(@ModelAttribute Passenger passenger,
                                                      @ModelAttribute Filter filter,
                                                      @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
                                                      @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(value = "pageSort", defaultValue = "id") String pageSort,
                                                      @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                      @RequestParam(value = "name", defaultValue = "") String name,
                                                      @RequestParam(value = "survived", defaultValue = "false") boolean survived,
                                                      @RequestParam(value = "adult", defaultValue = "false") boolean adult,
                                                      @RequestParam(value = "males", defaultValue = "false") boolean males,
                                                      @RequestParam(value = "noRelatives", defaultValue = "false") boolean noRelatives,
                                                      Model model) {

        //extracting passenger list from database
        List<Passenger> listFilter1 = passengersRepo.findAllByNameContains(name);

        //filter block
        List<Passenger> listFilter4 = liquibaseService.setFilters(listFilter1, survived, adult, males, noRelatives);

        //pagination
        int totalPages = (int) Math.ceil((double) listFilter4.size() / pageSize);
        Page<Passenger> passengerPage;
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, pageSort));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), listFilter4.size());
        passengerPage = new PageImpl<>(listFilter4.subList(start, end), pageRequest, listFilter4.size());

        //passenger statistics block
        int survivedPassengers = (int) listFilter4.stream()
                .filter(Passenger::isSurvived)
                .count();
        int hasRelatives = (int) listFilter4.stream()
                .filter(p -> p.getParentsChildrenAboard() > 0 || p.getSiblingsSpousesAboard() > 0)
                .count();
        Double fareTotal = listFilter4.stream().mapToDouble(Passenger::getFare).sum();

        model.addAttribute("passengerPage", passengerPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageSort", pageSort);
        model.addAttribute("direction", direction);
        model.addAttribute("name", name);
        model.addAttribute("survived", survived);
        model.addAttribute("adult", adult);
        model.addAttribute("males", males);
        model.addAttribute("noRelatives", noRelatives);
        model.addAttribute("survivedPassengers", survivedPassengers);
        model.addAttribute("hasRelatives", hasRelatives);
        model.addAttribute("fareTotal", fareTotal);
        return new ResponseEntity<>(passengerPage, HttpStatus.OK);
    }
}
