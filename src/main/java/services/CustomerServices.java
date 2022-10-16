package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.assignment.boosten.data.ICustomerDAO;
import com.capgemini.assignment.boosten.dto.CustomerDetailsDTO;
import com.capgemini.assignment.boosten.exception.AccountClosedException;
import com.capgemini.assignment.boosten.exception.CustomerDeactivatedException;
import com.capgemini.assignment.boosten.exception.CustomerNotFoundException;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.AccountStatus;
import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.model.CustomerStatus;
import com.capgemini.assignment.boosten.model.Transaction;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServices {
	private final ICustomerDAO customerDao;
	private final AccountServices accountServices;

	public List<Customer> getAllCustomers() {
		return customerDao.findAll();
	}

	public Customer getOneCustomer(Long customerId) {
		return customerDao.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
	}

	public Customer getOneCustomerWithAccounts(Long customerId) {
		return customerDao.findByIdWithAccounts(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));
	}

	public Customer createCustomer(String name, String surname) {
		Customer customer = new Customer(name, surname);
		
		return customerDao.save(customer);
	}

	public Customer deactivateCustomer(Long customerId, Long receiverId) {
		Customer customer = getOneCustomerWithAccounts(customerId);
		
		if (customer.getStatus() == CustomerStatus.DEACTIVATED) throw new CustomerDeactivatedException(customerId);
		
		Account receiverAccount = accountServices.getOneAccount(receiverId);

		if (receiverAccount.getStatus() == AccountStatus.CLOSED) throw new AccountClosedException(receiverId);
		
		for(Account account : customer.getAccounts()) {
			if (account.getStatus() == AccountStatus.OPEN) {
				accountServices.closeAccount(account, receiverAccount);
			}
		}
		
		customer.setStatus(CustomerStatus.DEACTIVATED);
		
		return customerDao.save(customer);
	}

	public Customer updateCustomer(Long customerId, String name, String surname) {
		Customer customer = getOneCustomer(customerId);
		customer.setName(name);
		customer.setSurname(surname);
		return customerDao.save(customer);
	}

	public CustomerDetailsDTO getCustomerDetails(Long customerId) {
		Customer customer = getOneCustomerWithAccounts(customerId);

		Collection<Transaction> transactions = new ArrayList<>();

		Collection<Account> accounts = customer.getAccounts();

		for (Account account : accounts) {
			Collection<Transaction> accountTransactions = account.getTransactions();

			for (Transaction transaction : accountTransactions) {
				transactions.add(transaction);
			}
		}

		return new CustomerDetailsDTO(customer.getName(), customer.getSurname(), customer.getBalance(), transactions);
	}

	public Collection<Account> getCustomerAccounts(Long customerId) {
		return getOneCustomerWithAccounts(customerId).getAccounts();
	}

	public Collection<Transaction> getCustomerTransactions(Long customerId) {
		Customer customer = getOneCustomerWithAccounts(customerId);

		Collection<Transaction> transactions = new ArrayList<>();

		Collection<Account> accounts = customer.getAccounts();

		for (Account account : accounts) {
			Collection<Transaction> accountTransactions = account.getTransactions();

			for (Transaction transaction : accountTransactions) {
				transactions.add(transaction);
			}
		}

		return transactions;
	}
}