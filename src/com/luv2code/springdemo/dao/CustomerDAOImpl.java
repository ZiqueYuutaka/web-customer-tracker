package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query; //hibernate 5.2
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;

@Repository //used for component-scanning
public class CustomerDAOImpl implements CustomerDAO {
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	//@Transactional //handles session transactions Commented out since Service handles it
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create a query and sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName",
															  Customer.class);
		
		//execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		//return list of customers
		return customers;
	}

	@Override
	//Transaction handled by CustomerService
	public void saveCustomer(Customer theCustomer) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//save the customer to database
		//currentSession.save(theCustomer); //Insert NEW customer
		currentSession.saveOrUpdate(theCustomer); //session decides to save or update
		
		
	}

	@Override
	public Customer getCustomer(int id) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		//Use id to get customer from the database
		Customer theCustomer = currentSession.get(Customer.class, id);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int id) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		//Use id to delete customer from the database
		//1) create a query using the current session
		Query theQuery = 
				currentSession.createQuery("delete from Customer where id=:customerId");
		
		//2) set the parameter for the query
		theQuery.setParameter("customerId", id);
		
		//2)execute the query
		theQuery.executeUpdate();
		
	}

}
