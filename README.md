# 🛒 E-Commerce Web Application (Under Development)
  This is an E-Commerce Web Application currently under development, built using Spring Boot, Thymeleaf, and Spring Security. The project aims to provide users with a seamless shopping experience while implementing robust authentication and role-based access control.

# 🚧 Project Status: In Progress
We are actively working on adding new features and improving existing ones. Stay tuned for updates!

# 🚀 Features (In Progress)
✅ Currently Implemented:
  1) User Authentication & Registration
  2) Secure login using Spring Security.
  3) Password encryption with BCrypt.
  4) Profile image upload feature.
  5) Product Management
  6) Browse products by category.
  7) View detailed product information.
  8) Role-Based Access Control (RBAC)
  9) USER role for browsing and purchasing.
 10) ADMIN role for product and category management.
     
# 🔄 Upcoming Features:
  1. Cart and Checkout functionality.
  2. Order Management System.
  3. Payment Integration.
  4. User Dashboard for order history.
  5. Admin dashboard with analytics.
  6. Email notifications for order confirmations.
  7. Improved UI with enhanced UX.
     
# 🛠️ Technologies Used
 1. Backend: Spring Boot, Spring Security, Hibernate, MySQL
 2. Frontend: Thymeleaf, Bootstrap, HTML, CSS, JavaScript
 3. Database: MySQL
 4. Tools: IntelliJ IDEA, MySQL Workbench, Git, Postman

# ⚙️ Setup Instructions
  1. Clone the repository:
     
    git clone https://github.com/yourusername/e-commerce-app.git cd e-commerce-app

 2. Configure Database:
    Create a MySQL database named ecomm_db.
    Update src/main/resources/application.properties with your MySQL credentials.
    
 3. Run the application:

        mvn spring-boot:run

 4. Access the application:
    Open http://localhost:8080/ in your web browser.


   # 📂 Project Structure

    E-Commerce
    │-- src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com.example.ecomm
    │   │   │       ├── config         # Security Configuration
    │   │   │       ├── controller     # Controllers handling web requests
    │   │   │       ├── model          # Database entity models
    │   │   │       ├── repository     # Repository interfaces for database operations
    │   │   │       ├── service        # Business logic services
    │   │   │       ├── ECommApplication.java  # Main Spring Boot application entry point
    │   │   ├── resources
    │   │   │   ├── static             # CSS, JS, and image files
    │   │   │   ├── templates          # Thymeleaf templates
    │   │   │   ├── application.properties  # Database and app configuration
    │   ├── test
    │   ├── pom.xml                    # Project dependencies
    │-- README.md


# 🤝 Contribution Guidelines
  
  Contributions are welcome! If you'd like to contribute, follow these steps:

  1. Fork the repository.
  2. Create a new branch (feature/your-feature).
  3. Commit your changes.
  4. Push to your fork and submit a pull request.

# 🏗️ Development Progress:
  We are committed to making this project better! Follow the repository to get the latest updates and improvements.    


