package com.cinema.web;

import com.cinema.domain.Customer;
import com.cinema.domain.CustomerRepository;
import com.cinema.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

/**
 * Created by Patryk on 2017-04-19.
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerRepository customerRepository){
        customerService = new CustomerService(customerRepository);
    }

    @GetMapping
    public String get(Model model){
        Collection<Customer> customers = customerService.findAll();
        model.addAttribute("customers",customers);
        return "customers/all";
    }

    @GetMapping("/new")
    public String initCreationForm(Model model){
        model.addAttribute("customer", new Customer());
        return "customers/create";
    }

    @PostMapping("/new")
    public String processCreationForm(@ModelAttribute Customer customer, BindingResult result, RedirectAttributes redir){
        if (result.hasErrors()) {
            return "customers/create";
        } else {
            customerService.save(customer);
            redir.addFlashAttribute("message","New customer added!");
            return "redirect:/customers/";
        }
    }

    @GetMapping("/edit/{customerId}")
    public String initEditForm(@PathVariable Long customerId,Model model){

        model.addAttribute("customer", customerService.findById(customerId));
        return "customers/edit";
    }

    @PostMapping("/edit/{customerId}")
    public String processEditForm(@PathVariable Long customerId, @ModelAttribute Customer customer, BindingResult result, RedirectAttributes redir){
        if (result.hasErrors()) {
            return "customers/edit";
        } else {
            customerService.save(customer);
            redir.addFlashAttribute("message","Customer edited!");
            return "redirect:/customers/";
        }
    }

    @GetMapping("/delete/{customerId}")
    public String removeCustomer(@PathVariable Long customerId, RedirectAttributes redir){
        customerService.delete(customerId);
        redir.addFlashAttribute("message","Customer deleted!");
        return "redirect:/customers/";
    }
}
