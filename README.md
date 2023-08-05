# Software Engineering 2 Coursework 1 - Group C 

## Project Info
> NewBank is an idea for a new disrupter bank where customers can interact with their accounts via a simple command-line interface. The system produced some basic client-server code as well as a protocol for sending commands and receiving responses from the NewBank server. The system was further developed their base code - specifically, implementing the protocol to improve
interaction with the NewBank server as well as to add new services.

## Table of Contents
* [Getting Started](#getting-started)
* [NewBank Protocol](#newbank-protocol)
* [Contributors](#contributors)
* [Roadmap](#roadmap)

### Getting Started
- Run the `Main.java` to start the NewBank system
- Currently the system does not support new user registration, sample accounts can be used for the system

  1. **Username: bhagy, Password: myPassword123**
  2. **Username: cindy, Password: myPassword123**
  3. **Username: john, Password: myPassword123**

### NewBank Protocol
Users can type the following protocol to perform the service:

* `SHOWMYACCOUNTS` - Display your accounts' name and balance

* `NEWACCOUNT <new account name> <account type (savings/current)> <opening balance>` - Create new account

  e.g. `NEWACCOUNT Home savings 10000` create a 'Home' savings account with $10,000

* `MOVE <Amount> <From> <To>` - Transfer money from user's interal accounts

  e.g. `MOVE 5000 Main Home` transfer $5,000 from account Main to account Home

* `SUBTRACTMONEYFROMACCOUNT <From> <Amount>`

  e.g. `SUBTRACTMONEYFROMACCOUNT Main 625` subtract $625 from account Main

* `CREDITLIMITCHECK` - Apply for credit by completing a simple questionnaire, system will return a credit limit depends on the questionnaire results

* `GETLOANREQUESTS` - Return a list of loan requests

* `CREATELOANREQUEST <loan amount> <purpose>` - Create a loan reqeust

* `QUIT` - Quit the NewBank system


### Contributors
The project has three roles: **Scrum Master**, **Product Owner** and **Scrum Team Member**

The roles were rorated within the group C members:
* **Hannah McGowan Jones**
* **Karan Manhas**
* **Kitty Liu**
* **Phoebe Harris**

### Roadmap

- [x] Add Main Class for a single entry point
- [x] Add welcome message to list out user actions
- [x] Add protocols
    - [x] "SHOWMYACCOUNTS"
    - [x]  "NEWACCOUNT"
    - [x]  "MOVE"
    - [x]  "SUBTRACTMONEYFROMACCOUNT"
    - [x]  "CREDITLIMITCHECK"
    - [x]  "GETLOANREQUESTS"
    - [x]  "CREATELOANREQUEST"
    - [x]  "QUIT"
- [x] Fix Null values in Username and Password Input
- [x] Method for passwords validation
- [x] Method for providing the functionality to pay a person OR company
- [x] Methods for micro-loans
- [x] Method to allow the user to view their transaction history
- [ ] Add Common Validation Method
- [ ] Method for loan repayment
- [ ] Improve system reliability
- [ ] Improve system security and privacy protection
- [ ] Improve system performance
- [ ] Comply with relevant law & regulations
- [ ] Protocol for international transfers by offering a currency conversion feature
- [ ] Write Unit Tests


See the [Trello board](https://trello.com/b/keHtv5kf/softwareengineering2groupc) for a full list of proposed features (and known issues).
