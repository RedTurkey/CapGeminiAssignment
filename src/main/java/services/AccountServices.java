package services;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.assignment.boosten.data.IAccountDAO;
import com.capgemini.assignment.boosten.exception.AccountClosedException;
import com.capgemini.assignment.boosten.exception.AccountNotFoundException;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.AccountStatus;
import com.capgemini.assignment.boosten.model.Customer;
import com.capgemini.assignment.boosten.model.Transaction;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServices {
	private final IAccountDAO accountDao;
	private final TransactionServices transactionServices;
	private final CustomerServices customerServices;

	public List<Account> getAllAccounts() {
		return accountDao.findAll();
	}

	public Account getOneAccount(Long accountId) {
		return accountDao.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	public Account createAccount(Long customerId, double initialCredit) {
		Customer customer = customerServices.getOneCustomer(customerId);
		Account newAccount = new Account(customer);

		newAccount = accountDao.save(newAccount);

		if (initialCredit != 0) {
			transactionServices.createTransaction(initialCredit, "Transfer on closure", newAccount.getId(), newAccount.getId(), newAccount.getId());

			newAccount.setBalance(newAccount.getBalance() + initialCredit);

			newAccount = accountDao.save(newAccount);
		}

		return newAccount;
	}
	
	public Account updateAccountBalance(Account account, double balance) {
		account.setBalance(balance);
		return accountDao.save(account);
	}

	public Collection<Transaction> getAccountTransactions(Long accountId) {
		Account account = getOneAccount(accountId);

		return account.getTransactions();
	}

	public void closeAccount(Long accountId, Long receiverId) {
		Account account = getOneAccount(accountId);
		
		if (account.getStatus() == AccountStatus.CLOSED) throw new AccountClosedException(accountId);

		Account receiverAccount = getOneAccount(receiverId);

		if (receiverAccount.getStatus() == AccountStatus.CLOSED) throw new AccountClosedException(receiverId);
		
		Transaction transaction = transactionServices.createTransaction(account.getBalance(), "Transfer on closure", account.getId(), account.getId(), receiverAccount.getId());

		account.setBalance(0);
		receiverAccount.setBalance(receiverAccount.getBalance() + transaction.getAmount());

		account.setStatus(AccountStatus.CLOSED);

		account = accountDao.save(account);
		receiverAccount = accountDao.save(receiverAccount);
	}

	public void closeAccount(Account account, Account receiver) {
		if (account.getStatus() == AccountStatus.CLOSED) throw new AccountClosedException(account.getId());
		if (receiver.getStatus() == AccountStatus.CLOSED) throw new AccountClosedException(receiver.getId());
		
		Transaction transaction = transactionServices.createTransaction(account.getBalance(), "Transfer on closure", account, account, receiver);

		updateAccountBalance(account, 0);
		updateAccountBalance(receiver, receiver.getBalance() + transaction.getAmount());

		account.setStatus(AccountStatus.CLOSED);
		
		accountDao.save(account);
	}
}
