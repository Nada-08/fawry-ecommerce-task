# Fawry OOP Task – E-Commerce System

## Overview
This project is a Java-based e-commerce system developed as part of Fawry’s internship technical challenge. It demonstrates core Object-Oriented Programming (OOP) concepts such as encapsulation, composition, abstraction, and design patterns like Strategy.

---

## Features
- Define products with optional behaviors:
  - Some are **expirable** (e.g., Cheese)
  - Some are **shippable** (e.g., TV)
- Add products to a cart with quantity validation
- Perform checkout with:
  - Subtotal calculation
  - Shipping fee calculation
  - Balance check and deduction
  - Shipment notice for shippable items
- Handles edge cases:
  - Insufficient balance
  - Out-of-stock products
  - Expired products
  - Empty cart

---

## Technologies
- Java 17+
- No external libraries used

---

## Concepts Demonstrated
- Object-Oriented Design Principles
- Strategy Design Pattern
- Interface-based composition
- Separation of Concerns
- Optional behaviors using `Optional<>`

---

## How to Run
1. Clone the repo:
   ```bash
   git clone https://github.com/Nada-08/fawry-ecommerce-task.git
