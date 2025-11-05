# Personal Finance Tracker

## Motivation
This project was originally a command-line application written in Java. After I gained a decent proficiency in Java, I wanted to learn about backend development in Java using Spring Boot. I've always been curious about it, and I know that Java + Spring Boot is used by a lot of enterprises, so I decided to pick it up. 

Then, this project was turned into a backend API. I learned as I was building. Soon, I was able to build a simple backend API for managing transactions, using H2 (in-memory database) for data persistency. I used Bruno for API testing. I was able to build API endpoints for creating a transaction, updating a transaction, and deleting a transaction. Nothinig special, just your standard CRUD backend. 

After I built the simple CRUD backend API, I decided to go one step further. I wanted to make it a full-stack application (my first ever!), not just a backend API. Because I thought, as the backend grows, testing becomes increasingly more difficult without a frontend, and I wanted to brush up on my frontend skills. In my first ever internship, I was a frontend developer. I worked with technologies such as JavaScript/TypeScript, React.js, and Material-UI. So I decided that I will build a frontend using these technologies. 

At the time of writing this, I have already built a simple frontend for this application, and I'm working on integrating the user feature. 

## Goals
I want to use this project as a learning experience, to build a solid foundation for the future, if I have any good ideas of what I want to build. So, even though I know that there are already many other finance trackers out there, I still want to dig deep and try my best to make it a real-world application. Right now, I'm working on add the user feature to this application. In the future, I want to display graphs and charts on the dashboard of the user's finances. For example, how much the user has made vs how much the user has spent, a more detailed summary, and maybe play around with AI a little bit... After I finish all the core functionalities of the project, I want to start thinking about system design. I think some hands-on experience with systems design will enhance my understanding of it. 

## How to Run
If you ever get curious, and want to run this project yourself, here is how to run this full stack application (If you haven't already, first clone the repository): 

### Running the Backend (Java + Spring Boot)
1. Navigate to the `backend/` folder and create your own `application.properties` file in `src/main/resources/application.properties`.
2. Add your own database configuration (e.g., PostgreSQL or H2), your jwt secret in Base64 encoded format, issuer, and expiration.
3. Run the backend using this command: `mvn spring-boot:run`

### Running the Frontend (React.js + JavaScript)
1. Navigate to the `frontend/` folder
2. Install all dependencies: `npm install`
3. Run local server: `npm run dev`
