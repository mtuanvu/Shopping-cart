# Shopping Cart Application

A simple shopping cart system built with **Spring Boot**, featuring product management, cart operations, and basic checkout functionality.

## Technologies
- Spring Boot, Spring Security, Spring Data JPA, Hibernate, MySQL, Maven

## Setup
1. Clone the repository:  
   `https://github.com/mtuanvu/Shopping-cart.git`
2. Configure MySQL in `application.properties`
3. Run:  
   `mvn spring-boot:run`

## Features
- **Product Management**: Add, update, delete, and view products.
- **Cart Management**: Add to cart, update quantities, remove items.
- **Checkout**: Calculate totals and handle basic checkout.

## API Endpoints
- **GET /api/products**: List products
- **POST /api/products**: Add product
- **PUT /api/products/{id}**: Update product
- **DELETE /api/products/{id}**: Delete product
- **GET /api/carts/{userId}**: View cart
- **POST /api/carts/{userId}/add**: Add to cart
- **PUT /api/carts/{userId}/update**: Update cart
- **DELETE /api/carts/{userId}/remove/{productId}**: Remove from cart

## Author
**mtuanvu** - [GitHub](https://github.com/mtuanvu)
