# medical-system
Distributed Medical System based on school assignment using: Java (Spring Boot), React, RabbitMQ, gRPC

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Clone the repository from github.com to your workspace folder:
```
$ git clone https://github.com/codrin23/medical-system.git
```

For the Web Client (medical.system.ui):

Navigate to the repository folder:
```
$ cd medical.system.ui
```

Install example dependencies
```
$ npm install
```

Run the server
```
$ npm start
```
Open [http://localhost:3000/](http://localhost:3000/) page in your browser.

For the Backend Web Server (medical.system):

```
$ cd medical.system
$ mvn clean install
$ mvn spring-boot:run
```

For sensor.reader module:

```
$ cd sensor.reader
$ mvn clean install
$ mvn spring-boot:run
```

### Prerequisites

* [Java 8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/download.cgi) (at least 3.5)
* MySQL
* npm

___

The **medical.system** and **medical.system.ui** module consists of an online platform designed to manage patients, caregivers and medication.

Functional requirements:

1. Users log in. Users are redirected to the page corresponding to their role.
2. Doctor Role:
    * CRUD operations on patients
    * CRUD operations on caregivers
    * CRUD operations on medication
    * Create medication plan for a patient, consisting of a list of medication and intake intervals needed to be taken daily, and the period of the treatment
3. Caregiver Role
    * A caregiver can view on his/her page all the patients associated in a list or table.
4. Patient Role
    * A patient can view in his/her page their accounts details and their medication plans
5. The users corresponding to one role will not be able to enter the pages corresponding to other roles (e.g. by log-in and then copy-paste the admin URL to the browser)

Implementation technologies:
  * REST services for backend application (Java Spring REST)
  * JavaScript-based frameworks for client application (ReactJS)
  
Non-functional requirements:
  * Security: use authentication in order to restrict users to access the administrator pages
(JWT)

___

The **sensor.reader** module consists of  a system based on a message broker middleware that gathers data from the sensors, pre-processes the data and then stores data into the database to the corresponding patient. If the queue consumer application that preprocesses the data detects an anomalous activity according to the following set of rules, it asynchronously notifies the caregiver application that a patient has problems:
    * R1: The sleep period longer than 12 hours;
    * R2: The leaving activity (outdoor) is longer than 12 hours;
    * R3: The period spent in bathroom is longer than 1 hour;

Functional requirements:
1. The message oriented middleware exposes a REST endpoint where the sensor system can send data tuples (patient_id, start_time, end_time, activity_label) in a JSON format
2. The message consumer component (submodule of medical.system module) of the system processes each message, applies the 3 rules defined (R1-R3) and notifies asynchronously using WebSockets the caregiver application

Implementation technologies:
    * RabbitMQ
    * WebSockets
    
___

The **pillcase.system** module will simulates for the patient an intelligent pill dispenser that can be programmed with the plan defined by the doctor. The pill dispenser will alert the patient when a medication has to be taken and when a patient did not take the medication in the corresponding time interval. The pill dispenser can communicate with the server using RPC. 

Functional requirements:
1. The pill dispenser client application shows the current time.
2. At the beginning of each new day (00:00:00) or at a predefined time during each day, it downloads the medication plan for the next 24 hours using RPC.
3. The pill dispenser client application displays a list of medication that have to be taken at the current time (the current time is within their intake interval), each medication from the list having associated a button labeled “Taken”, which, when pressed, deletes the medication from the list displayed and informs the server application using RPC that the person took the medication.
4. When the current time passes the end time of an intake interval, the server is informed using RPC that the person did not take the medication at the prescribed time. 

Implementation technologies:
  * gRPC


Changelog:
  * add initial the description of the project
  * add initial documentation of the project
  * include initial dependencies and how to build and run the project step

TODO:
  * include dependencies and how to build and run for pillcase.system module
  * include top level architectural UML
  * include in the TODO list code improvements to be made and update changelog with link to the commit for each improvement done
