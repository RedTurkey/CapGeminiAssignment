package services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.assignment.boosten.data.ITransactionDAO;
import com.capgemini.assignment.boosten.exception.TransactionNotFoundException;
import com.capgemini.assignment.boosten.model.Account;
import com.capgemini.assignment.boosten.model.Transaction;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServices {
	private final ITransactionDAO transactionDao;
	private final AccountServices accountServices;

	public List<Transaction> getAllTransactions() {
		return transactionDao.findAll();
	}

	public Transaction getOneTransaction(Long transactionId) {
		return transactionDao.findById(transactionId) //
				.orElseThrow(() -> new TransactionNotFoundException(transactionId));
	}

	public Transaction createTransaction(double amount, String communication, Long creatorId, Long senderId,
			Long receiverId) {
		Account creatorAccount = accountServices.getOneAccount(creatorId);

		Account senderAccount = accountServices.getOneAccount(senderId);

		Account receiverAccount = accountServices.getOneAccount(receiverId);

		Transaction newTransaction = new Transaction(amount, communication, creatorAccount, senderAccount,
				receiverAccount);

		newTransaction = transactionDao.save(newTransaction);

		accountServices.updateAccountBalance(senderAccount, senderAccount.getBalance() - newTransaction.getAmount());
		accountServices.updateAccountBalance(receiverAccount,
				receiverAccount.getBalance() + newTransaction.getAmount());

		return newTransaction;
	}

	public Transaction createTransaction(double amount, String communication, Account creator, Account sender,
			Account receiver) {
		Transaction newTransaction = new Transaction(amount, communication, creator, sender, receiver);

		newTransaction = transactionDao.save(newTransaction);

		accountServices.updateAccountBalance(sender, sender.getBalance() - newTransaction.getAmount());
		accountServices.updateAccountBalance(receiver, receiver.getBalance() + newTransaction.getAmount());

		return newTransaction;
	}
}
