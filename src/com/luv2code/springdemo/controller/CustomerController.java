package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//inject the DAO in to Controller
/*	@Autowired
	private CustomerDAO customerDAO;*/
	
	@Autowired
	private CustomerService customerService;
	
	//@RequestMapping("/list")
	@GetMapping("/list") //Spring 4.3 constrained to GET
	public String listCustomers(Model theModel){
		System.out.println("Calling listCustomers");
		
		//get customers from the service
		List<Customer> theCustomers = customerService.getCustomers();
		
		//add the customers to the model for use in the .jsp page
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFromForAdd(Model theModel){
		
		//Create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){
		
		//save the customer using our service
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFromForUpdate(@RequestParam("customerId") int id, Model theModel){
		
		//get the customer from the database
		Customer theCustomer = customerService.getCustomer(id);
		
		//set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		//send over to our form		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id){
		
		//Delete the customer
		customerService.deleteCustomer(id);
		
		return "redirect:/customer/list"; //because I want to go back to the list
	}
}
