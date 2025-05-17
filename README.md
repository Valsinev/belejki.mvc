# belejki.mvc

**belejki.mvc** belejki.mvc is a Spring Boot MVC application that serves as the frontend for the belejki.rest API. It allows users to manage reminders, recipes, wishlists, shopping lists, and friendships. Each user can manage personal data and optionally share wishlists with friends.

⚠️ Note: This application depends on the belejki.rest backend API. Make sure the backend is running and accessible before launching the frontend.

## ✨ Features

- **User Registration & Authentication** – Register, confirm via email, and log in securely.
- **Reminders** – Set reminders with expiration dates.
- **Recipes** – Store and retrieve cooking recipes.
- **Wishes** – Create wishlists with price estimates and item links.
- **Shopping List** – Manage personal shopping items.
- **Friendships** – Add friends and access their wishlists.


## ⚙️ Installation & Setup

### Requirements

- Java 17+
- Maven
- IntelliJ IDEA (recommended)

### 📥 Clone the Repository

bash
git clone https://github.com/Valsinev/belejki.mvc.git




### ⚙️ Environment Variables
Before running the application, set the following environment variables:
| Variable                   | Description                                                     | Default            |
| -------------------------- | --------------------------------------------------------------- | ------------------ |
| `SERVER_PORT`              | (Optional) Port to run the app on                               | `8080`             |
| `BACKEND_API_URL`          | Backend rest URL used in email confirmation links               | `http://localhost:8080` |
| `APP_JWT_SECRET`           | JWT secret key for the authentication of the users              | —                  |

💡 In IntelliJ, go to Run > Edit Configurations > Environment Variables to set these.


### 🚀 Running the Application
✅ In IntelliJ (Recommended)

1. Open the project in IntelliJ IDEA.

2. Set environment variables (see above).

3. Find the main class: Application.java.

4. Right-click it and choose Run.

The API will start and be available at: http://localhost:8080/

🧪 From Terminal (Alternative)

./mvnw spring-boot:run

Or package and run the JAR:

./mvnw package
java -jar target/mvc-0.0.1-SNAPSHOT.jar

Make sure environment variables are exported before running:

export APP_JWT_SECRET
...



## 🛠️ Technologies Used

- Java 17

- Spring Boot

- Spring Security

- Maven

- Bootstrap 4 (UI framework)

- Postman (for testing, backend)

## 🔗 Related Projects

- [belejki.rest](https://github.com/Valsinev/belejki.rest.git) – REST API backend for this frontend.
