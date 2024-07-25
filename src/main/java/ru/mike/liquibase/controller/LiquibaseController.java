package ru.mike.liquibase.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mike.liquibase.domain.Filter;
import ru.mike.liquibase.domain.Passenger;
import ru.mike.liquibase.repo.PassengersRepo;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Controller
public class LiquibaseController {

    @Autowired
    PassengersRepo passengersRepo;

    @GetMapping(path = "list")
    public String main(HttpServletRequest request,
                       @ModelAttribute Passenger passenger,
                       @ModelAttribute Filter filter,
                       @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
//                       @RequestParam(value = "passengerPage", required = false) Page<Passenger> passengerPage,
                       @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                       @RequestParam(value = "pageSort", defaultValue = "id") String pageSort,
                       @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                       @RequestParam(value = "name", defaultValue = "") String name,
                       @RequestParam(value = "survived", defaultValue = "false") boolean survived,
                       @RequestParam(value = "adult", defaultValue = "false") boolean adult,
                       @RequestParam(value = "males", defaultValue = "false") boolean males,
                       @RequestParam(value = "noRelatives", defaultValue = "false") boolean noRelatives,
                       Model model) {
        Page<Passenger> passengerPage;
        if (direction.equals("ASC")) {
            Pageable pageableASC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, pageSort));
            passengerPage = passengersRepo.findAllByNameContains(name, pageableASC);
        } else {
            Pageable pageableDESC = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, pageSort));
            passengerPage = passengersRepo.findAllByNameContains(name, pageableDESC);
        }
//        List<Passenger> finalListFilter = list;
//        Supplier<Stream<Passenger>> streamFilter1 = finalListFilter::stream;
//        if (survived) {
//            list = streamFilter1.get()
//                    .filter(Passenger::isSurvived).toList();
//        }
//        List<Passenger> listFilter2 = list;
//        Supplier<Stream<Passenger>> streamFilter2 = listFilter2::stream;
//        if (adult) {
//            listFilter2 = streamFilter2.get()
//                    .filter(p -> p.getAge() >= 16).toList();
//        }
//        List<Passenger> listFilter3 = listFilter2;
//        Supplier<Stream<Passenger>> streamFilter3 = listFilter3::stream;
//        if (males) {
//            listFilter3 = streamFilter3.get()
//                    .filter(p -> p.getSex().equals("male")).toList();
//        }
//        List<Passenger> listFilter4 = listFilter3;
//        Supplier<Stream<Passenger>> streamFilter4 = listFilter4::stream;
//        if (noRelatives) {
//            listFilter4 = streamFilter4.get()
//                    .filter(p -> p.getParentsChildrenAboard() < 1 && p.getSiblingsSpousesAboard() < 1).toList();
//        }
//        Page<Passenger> passengerPage = new PageImpl<>(listFilter4);
//        List<Passenger> passengerList = listFilter4;

        int survivedPassengers = (int) passengerPage.stream()
                .filter(Passenger::isSurvived)
                .count();
        int hasRelatives = (int) passengerPage.stream()
                .filter(p -> p.getParentsChildrenAboard() > 0 || p.getSiblingsSpousesAboard() > 0)
                .count();
        Double fareTotal = passengerPage.stream().mapToDouble(Passenger::getFare).sum();
        filter.setPassengers(passengerPage);
        int totalPages = (int) Math.ceil((double) passengerPage.getTotalElements() / pageSize);

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
        return "index";
    }

    @PostMapping(path = "list")
    public String listSorted(@ModelAttribute Passenger passenger,
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

        List<Passenger> listFilter1 = passengersRepo.findAllByNameContains(name);
        List<Passenger> finalListFilter = listFilter1;
        Supplier<Stream<Passenger>> streamFilter1 = finalListFilter::stream;
        if (survived) {
            listFilter1 = streamFilter1.get()
                    .filter(Passenger::isSurvived).toList();
        }
        List<Passenger> listFilter2 = listFilter1;
        Supplier<Stream<Passenger>> streamFilter2 = listFilter2::stream;
        if (adult) {
            listFilter2 = streamFilter2.get()
                    .filter(p -> p.getAge() >= 16).toList();
        }
        List<Passenger> listFilter3 = listFilter2;
        Supplier<Stream<Passenger>> streamFilter3 = listFilter3::stream;
        if (males) {
            listFilter3 = streamFilter3.get()
                    .filter(p -> p.getSex().equals("male")).toList();
        }
        List<Passenger> listFilter4 = listFilter3;
        Supplier<Stream<Passenger>> streamFilter4 = listFilter4::stream;
        if (noRelatives) {
            listFilter4 = streamFilter4.get()
                    .filter(p -> p.getParentsChildrenAboard() < 1 && p.getSiblingsSpousesAboard() < 1).toList();
        }
        Page<Passenger> passengerPage = new PageImpl<>(listFilter4);
        List<Passenger> passengerList = listFilter4;

        int survivedPassengers = (int) passengerList.stream()
                .filter(Passenger::isSurvived)
                .count();
        int hasRelatives = (int) passengerList.stream()
                .filter(p -> p.getParentsChildrenAboard() > 0 || p.getSiblingsSpousesAboard() > 0)
                .count();
        Double fareTotal = passengerList.stream().mapToDouble(Passenger::getFare).sum();
        filter.setPassengers(passengerPage);
        int totalPages = (int) Math.ceil((double) passengerPage.getTotalElements() / pageSize);
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
        return "index";
    }
}
