# CapGeminiAssignment
Assignment for CapGemini from Boosten Vincent

# Build and deploy
Once you have cloned the project, provided that you have gradle installed on your computer, go into the project folder in your terminal, and launch the gradle command to clean and build the project :  
```
gradle clean build
```
Once that is done, make sure you have tomcat version 10 installed on your computer.  
Since we are using tomcat 10, you have to locate your tomcat installation (under windows, it's C:\Program Files\Apache Software Foundation\Tomcat 10.0) and create a folder webapps-javaee if it does not exist.  
You can then put the generated war file in that folder and restart tomcat, which will then migrate the war file from Java EE into Jakarta EE.
  
The application should now be deployed and accessible on (or another port if you put your tomcat on something else than 8080) localhost:8080/CapGeminiAssignmentBoosten-0.0.1-SNAPSHOT  
Note that the application is a Rest Spring API, and as such, does not have a front-end interface, you have to use simple request to recover the data, here is a list in case you need it :  
```
// To recover all customers (GET)
/customers
```
```
// To recover a single customer (GET)
/customers/{customerId}
```
```
// To recover a customer's accounts (GET)
/customers/{customerId}/accounts
```
```
// To recover a customer's account (GET)
/customers/{customerId}/accounts/{accountId}
```
```
// To recover a customer's account's transactions (GET)
/customers/{customerId}/accounts/{accountId}/transactions
```
```
// To recover a customer's account's transaction (GET)
/customers/{customerId}/accounts/{accountId}/transactions/{transactionId}
```
```
// To create a new account with an initial credit (POST)
/customers/{customerId}/accounts/{initialCredit}
```
```
// To recover a single customer's details (name + surname + balance + transactions) (GET)
/customers/{customerId}/details
```
For example, on my computer, the command to create a new account for a customer with an initial credit of 10 is as follow :  
```
curl -X POST localhost:8080/CapGeminiAssignmentBoosten-0.0.1-SNAPSHOT/customers/1/accounts/10 -H 'Content-type:application/json' -d '{}' | json_pp
```