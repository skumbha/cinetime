--liquibase formatted sql

-- Changeset Shrirang:1
-- Author: Shrirang
-- Description: Create User Table if not exists
CREATE TABLE IF NOT EXISTS User (
                                    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    username VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    phone_number VARCHAR(20),
    address VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Changeset Shrirang:2
-- Author: Shrirang
-- Description: Create Theater_Partner Table if not exists
CREATE TABLE IF NOT EXISTS Theater_Partner (
                                               partner_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                               name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    phone_number VARCHAR(20),
    address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    registration_date DATE,
    status VARCHAR(20),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Changeset Shrirang:3
-- Author: Shrirang
-- Description: Create Theater Table if not exists
CREATE TABLE IF NOT EXISTS Theater (
                                       theater_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       name VARCHAR(255),
    address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    phone_number VARCHAR(20),
    partner_id BIGINT,
    FOREIGN KEY (partner_id) REFERENCES Theater_Partner(partner_id),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Changeset Shrirang:4
-- Author: Shrirang
-- Description: Create Screen Table if not exists
CREATE TABLE IF NOT EXISTS Screen (
                                      screen_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      theater_id INT,
                                      screen_number INT,
                                      capacity INT,
                                      screen_type VARCHAR(50),
                                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                                    );

-- Changeset Shrirang:5
-- Author: Shrirang
-- Description: Create Seat Table if not exists
CREATE TABLE IF NOT EXISTS Seat (
                                    seat_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    screen_id INT,
                                    seat_number VARCHAR(10),
    seat_type VARCHAR(50),
    seat_location VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Changeset Shrirang:6
-- Author: Shrirang
-- Description: Create Movie Table if not exists
CREATE TABLE IF NOT EXISTS Movie (
                                     movie_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     title VARCHAR(255),
    genre VARCHAR(100),
    duration SMALLINT UNSIGNED,
    release_date DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Changeset Shrirang:7
-- Author: Shrirang
-- Description: Create Show Table if not exists
CREATE TABLE IF NOT EXISTS `Show` (
                                      show_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      screen_id BIGINT,
                                      movie_id BIGINT,
                                      start_time DATE,
                                      end_time DATE,
                                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      FOREIGN KEY (screen_id) REFERENCES Screen(screen_id),
    FOREIGN KEY (movie_id) REFERENCES Movie(movie_id)

    );

-- Changeset Shrirang:8
-- Author: Shrirang
-- Description: Create Pricing Table if not exists
CREATE TABLE IF NOT EXISTS Pricing (
                                       pricing_id INT PRIMARY KEY AUTO_INCREMENT,
                                       show_id BIGINT,
                                       seat_type VARCHAR(50),
                                        show_time DATE,
                                        price DECIMAL(10, 2),
                                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                        updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        FOREIGN KEY (show_id) REFERENCES `Show`(show_id)

    );

-- Changeset Shrirang:10
-- Author: Shrirang
-- Description: Create Promotion Table if not exists
CREATE TABLE IF NOT EXISTS Promotion (
                                         promotion_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         promotion_name VARCHAR(255),
    discount_percentage DECIMAL(5, 2),
    theater_id BIGINT,
    start_date DATE,
    end_date DATE,
    active_status TINYINT,
    conditions VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Changeset Shrirang:9
-- Author: Shrirang
-- Description: Create Booking Table if not exists
CREATE TABLE IF NOT EXISTS Booking (
                                       booking_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       show_id BIGINT,
                                       user_id BIGINT,
                                       seat_id BIGINT,
                                       booking_time DATETIME,
                                       total_amount DECIMAL(10, 2),
    discounted_amount DECIMAL(10, 2),
    promotion_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (show_id) REFERENCES `Show`(show_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (seat_id) REFERENCES Seat(seat_id),
    FOREIGN KEY (promotion_id) REFERENCES Promotion(promotion_id)
    );