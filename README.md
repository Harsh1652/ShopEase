# ShopEase

ShopEase is an e-commerce web application built using Spring Boot, Thymeleaf, and Spring Security. It offers users a seamless shopping experience with secure authentication, role-based access control, and a variety of features for browsing, purchasing, and managing products.

## Project Status
The project is currently under active development. Most core features are implemented, with enhancements and new features being added regularly.

## Features

### User Authentication & Registration
- Secure login and registration using Spring Security
- Password encryption with BCrypt
- Profile image upload feature during registration or profile update

### Product Management
- Browse and search products by category
- View detailed product descriptions and images
- Add, edit, and delete products (ADMIN role)

### Cart and Checkout
- Add items to a shopping cart
- View cart summary and update item quantities
- Proceed to secure checkout

### Order Management
- Place orders after successful checkout
- View order history and order details


### Role-Based Access Control (RBAC)
- **USER Role:** Can browse products, add items to the cart, place orders, and make payments
- **ADMIN Role:** Can manage products, categories, and orders

### Upcoming Feature
- User dashboard for order tracking and personalized recommendations
- Integration with payment gateway for secure transactions


## Project Structure
The project follows a standard Spring Boot structure:

```
ShopEase/
  |-- src/main/java
  |-- src/main/resources
  |-- src/test/java
  |-- pom.xml
```

- **src/main/java:** Contains the Java source code
- **src/main/resources:** Includes application properties and Thymeleaf templates
- **src/test/java:** Houses test cases

## Getting Started

To set up the project locally, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Harsh1652/ShopEase.git
   cd ShopEase
   ```

2. **Build the project:**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. Access the application at `http://localhost:8080`.

## Contributing
Contributions are welcome! To contribute:

1. Fork the repository
2. Create a new branch for your feature or bug fix
3. Submit a pull request

Please ensure that your contributions adhere to the project's coding standards and include appropriate tests.
