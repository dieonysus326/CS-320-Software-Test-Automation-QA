# CS-320-Software-Test-Automation-QA

CS 320: Software Testing, Automation, and Quality Assurance

This repository contains portfolio work from CS 320 at Southern New Hampshire University.

Contents
Project One: Contact Service
Contact.java defines a contact object with a unique, non-updatable ID (max 10 characters), first and last name (max 10 characters each), phone number (exactly 10 digits), and address (max 30 characters). None of the fields may be null.
ContactService.java adds contacts with unique IDs, deletes contacts by ID, and updates the first name, last name, phone number, and address.
ContactTest.java and ContactServiceTest.java contain JUnit 5 tests that verify each requirement, including boundary values, null inputs, and invalid formats.
Project Two: Summary and Reflections Report
Analyzes the unit testing approach used across the contact, task, and appointment services, the testing techniques applied, and the mindset needed to test software effectively.
Reflection
How can I ensure that my code, program, or software is functional and secure?

I ensure my code is functional by writing unit tests that tie directly back to each requirement and by testing both valid and invalid input. For the contact service, that meant checking boundary values such as a 10-character ID versus an 11-character ID, phone numbers that were too short, too long, or contained letters, and null values for every field. Running coverage in Eclipse showed me which branches were never exercised so I could close those gaps. Security comes from the same discipline: validating every input in the constructor and setters, throwing exceptions instead of accepting bad data, and keeping fields like the contact ID immutable so they cannot be changed after creation. Treating validation as the first line of defense keeps bad data from ever reaching the rest of the program.

How do I interpret user needs and incorporate them into a program?

I start by breaking the requirements into small, testable statements. Each rule, such as "the address field shall be no longer than 30 characters," becomes both a piece of validation logic and at least one test case. If a requirement is vague, I look for the boundaries and edge cases hidden in it before writing any code. This approach comes naturally from my work in retail operations, where understanding exactly what a customer or team needs is the difference between solving the problem and creating a new one. Writing tests against the requirements gives me proof that the program does what the user asked, not just what I assumed they wanted.

How do I approach designing software?

I approach design by keeping each class focused on one responsibility. In this project, Contact owns its own data and validation, while ContactService manages the collection of contacts and the operations performed on them. That separation made the code easier to test, since each class could be verified on its own, and easier to extend when the task and appointment services followed the same pattern. I also design with testing in mind from the start, asking how I will prove each piece works before I finish writing it. That habit leads to simpler methods, clearer error handling, and code that is easier for someone else to maintain.
