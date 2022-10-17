package com.capgemini.assignment.boosten.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.capgemini.assignment.boosten.data.IAccountDAO;
import com.capgemini.assignment.boosten.exception.AccountClosedException;
import com.capgemini.assignment.boosten.exception.AccountNotFoundException;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.AccountStatus;
import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.model.Transaction;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServices {
	private final IAccountDAO accountDao;
	private TransactionServices transactionServices;
	private CustomerServices customerServices;

	@Lazy
	@Autowired
	public void setTransactionServices(TransactionServices transactionServices) {
		this.transactionServices = transactionServices;
	}

	@Lazy
	@Autowired
	public void setCustomerServices(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}

	public List<Account> getAllAccounts() {
		return accountDao.findAll();
	}

	public Account getOneAccount(Long accountId) {
		return accountDao.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	public void checkAccountStatus(Account account) {
		if (account.getStatus() == AccountStatus.CLOSED)
			throw new AccountClosedException(account.getId());
	}

	public Account createAccount(Long customerId, double initialCredit) {
		Customer customer = customerServices.getOneCustomer(customerId);

		customerServices.checkCustomerStatus(customer);

		Account newAccount = new Account(customer);

		newAccount = accountDao.save(newAccount);

		if (initialCredit != 0) {
			transactionServices.createTransaction(initialCredit, "Transfer on closure", newAccount.getId(),
					newAccount.getId(), newAccount.getId());

			newAccount.setBalance(newAccount.getBalance() + initialCredit);

			newAccount = accountDao.save(newAccount);
		}

		return newAccount;
	}

	public Account updateAccountBalance(Account account, double balance) {
		checkAccountStatus(account);
		account.setBalance(balance);
		return accountDao.save(account);
	}

	public Collection<Transaction> getAccountTransactions(Long accountId) {
		Account account = getOneAccount(accountId);

		return account.getTransactions();
	}

	public Account closeAccount(Long accountId, Long receiverId) {
		Account account = getOneAccount(accountId);

		checkAccountStatus(account);

		Account receiverAccount = getOneAccount(receiverId);

		checkAccountStatus(receiverAccount);

		Transaction transaction = transactionServices.createTransaction(account.getBalance(), "Transfer on closure",
				account.getId(), account.getId(), receiverAccount.getId());

		account.setBalance(0);
		account.setStatus(AccountStatus.CLOSED);

		updateAccountBalance(receiverAccount, receiverAccount.getBalance() + transaction.getAmount());

		return accountDao.save(account);
	}

	public Account closeAccount(Account account, Account receiver) {
		checkAccountStatus(account);
		checkAccountStatus(receiver);

		Transaction transaction = transactionServices.createTransaction(account.getBalance(), "Transfer on closure",
				account, account, receiver);

		account.setBalance(0);
		account.setStatus(AccountStatus.CLOSED);

		updateAccountBalance(receiver, receiver.getBalance() + transaction.getAmount());

		return accountDao.save(account);
	}
}
