# CapGeminiAssignment
Assignment for CapGemini from Boosten Vincent

# Build
Once you have cloned the project, open a terminal and go in the project folder and type this to build the project :  
```
./gradlew clean build
```
The code will be build and a war file will be generated in build/libs in the project folder (not the file finishing with -plain).  
  
# Test
To launch the tests for the project, simply enter this command :  
```
./gradlew test
```
The result are accessible through various mean if you want more details, you can see them by opening build/reports/tests/test/index.html if you want.  
  
# Deploy
Make sure you have Tomcat version 10 installed on your computer.  
Since we are using Tomcat 10, you have to locate your Tomcat installation (under windows, it's "C:\Program Files\Apache Software Foundation\Tomcat 10.0" by default) and create a folder webapps-javaee if it does not exist.  
You can then put the generated war file in that folder and restart Tomcat, which will then migrate the war file from Java EE into Jakarta EE.  
  
The application should now be deployed and accessible on (or another port if you put your Tomcat on something else than 8080) localhost:8080/CapGeminiAssignmentBoosten-0.0.1-SNAPSHOT  

# Use
Note that the application is a Rest Spring API, and as such, does not have a front-end interface, you have to use simple request to recover the data, here is a list in case you need it (note that all those path are prefixed by localhost:8080/CapGeminiAssignmentBoosten-0.0.1-SNAPSHOT) :  
#### Root :  
```
// Root path to see the others (GET)
/
```
#### Customers :  
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
// To recover a customer's transactions (GET)
/customers/{customerId}/transactions
```
```
// To recover a customer's details (name + surname + balance + transactions) (GET)
/customers/{customerId}/details
```
```
// To create a new customer (POST) ({name: String, surname: String})
/customers
```
```
// To update a customer's informations (PUT) ({name: String, surname: String})
/customers/{customerId}
```
```
// To deactivate a customer and send its balance to another account (PUT) ({receiverId: number})
/customers/{customerId}/deactivate
```
#### Accounts :  
```
// To recover all accounts (GET)
/accounts
```
```
// To recover a single account (GET)
/accounts/{accountId}
```
```
// To recover an account's transactions (GET)
/accounts/{accountId}/transactions
```
```
// To create a new account with an initial credit (POST)  
// ({initialCredit: number, customerId: number})
/accounts
```
```
// To close an account and send its balance to another account (PUT) ({receiverId: number})
/accounts/{accountId}/close
```
#### Transactions :  
```
// To recover all transactions (GET)
/transactions
```
```
// To recover a single transaction (GET)
/transactions/{transactionId}
```
```
// To create a new transaction (POST)  
// ({amount: number, communication: String, creatorId: number, senderId: number, receiverId: number})
/transactions
```
  
For example, the command to create a new account for a customer with an initial credit of 10 is as follow :  
```
curl -X POST localhost:8080/CapGeminiAssignmentBoosten-0.0.1-SNAPSHOT/accounts -H 'Content-type:application/json' -d '{"customerId": 2,"initialCredit": 10}' | json_pp
```
  
Do note though that this API follow the HAL-FORMS standard, and as such, you can recover all those path by following the requests return information.