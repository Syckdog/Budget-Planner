# My Personal Project: Personal Finance Tracker

## Description of Project
My personal project for CPSC 210 this term is a personal finance tracker that will allow users to track their spending
while also being able to budget how much they spend per month or year depending on their preference.  The app will allow
users to set categories such as "Groceries" or "Entertainment" and input their spending in each of their created
categories.  Users will also have the option to input how much they are allowed to spend on each category or in total
in order to better plan their future spending.  Under each category, users will be able to input which vendor they have 
purchased from, what they have purchased, when they have purchased said item and how much they spent on the item(s).
This app will be useful for anyone who would like to track their spending and budget how much they should be spending
to improve their personal finances.  This app is especially useful for college students who may be caring about their
finances for the first time.  I am interested in bringing this app to life because as a college student myself I am
finding myself needing to better track my spending habits so I ensure I have enough money each month for essentials
as well as for entertainment.  I think this app is especially useful for those that are living on their own for the 
first time and need an easy way to track their income as well as how much they should spend on groceries each week.

## User Stories
- As a user, I want to be able to see the amount I have spent in total
- As a user, I want to be able to add my transactions to a list to view in the future
- As a user, I want to be able to see my transactions in a list
- As a user, I want to be able to remove a transaction from my list
- As a user, I want to be able to sort by ascending/descending date
- As a user, I want to be able to enter my income
- As a user, I want to be able to save my transaction list to file
- As a user, I want to be able to load my transaction list from file

## Phase 4: Task 2
Sample:
Sun Mar 27 19:42:15 PDT 2022
Loaded Transaction List
Sun Mar 27 19:42:27 PDT 2022
You have added a new transaction: Item: T | Vendor: T | Date: 2000/01/20 | Amount: $3.00
Sun Mar 27 19:42:35 PDT 2022
You have removed a transaction: Item: T | Vendor: T | Date: 2000/01/20 | Amount: $3.00
Sun Mar 27 19:42:50 PDT 2022
Saved Transaction List

## Phase 4: Task 3
To refactor some of my code I would choose to use more abstract classes for my GUI.  As of now, everything is in the
GUI class including all the panels being made and how all the buttons and other features work.  If I were to refactor 
my project I would create an abstract class for a panel to use as the base for my other panels for "add transaction",
"show transaction list", "check amount" and "main menu".  This would make it so I don't have so much going on in my 
GUI class.  I could create one abstract class for panels and four different classes that utilize this panel.  I also
have the function to show transaction list embedded into my add transaction and load methods.  I could refactor these
methods by have a new method that just shows these transactions so that I could just load all this information without
repeating this code again in my load method and add transaction method.  As well, I also reached the max for lines in
my method so I could combine certain functions such as "sort date in descending order" and 
"sort date in ascending order" into one function that changes depending on which button is pushed.  This would make more 
room to have more potential features and cut down on the amount of methods in the GUI class.