CREATE DATABASE phone_store_db;
USE phone_store_db;

CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    is_deleted TINYINT(1) DEFAULT 0 
);

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    category_id INT,
    price DOUBLE NOT NULL,
    stock INT NOT NULL,
    color VARCHAR(50),
    capacity VARCHAR(50),
    description TEXT,
    is_flash_sale TINYINT(1) DEFAULT 0,
    sale_percent INT DEFAULT 0,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'CUSTOMER') NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    address VARCHAR(255)
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    total_amount DOUBLE,
    status ENUM('PENDING', 'SHIPPING', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_details (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    price_at_purchase DOUBLE,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

INSERT INTO categories (name) VALUES
                                  ('iPhone'),
                                  ('Samsung'),
                                  ('Xiaomi'),
                                  ('Oppo');

INSERT INTO products (name, category_id, price, stock, color, capacity, description, is_flash_sale, sale_percent) VALUES
                                                                                                                      ('iPhone 15 Pro Max', 1, 30000000, 10, 'Titan Tự Nhiên', '256GB', 'Chip A17 Pro mạnh mẽ nhất', 1, 5),
                                                                                                                      ('iPhone 13', 1, 13500000, 20, 'Blue', '128GB', 'Máy quốc tế chính hãng', 0, 0),
                                                                                                                      ('Samsung Galaxy S24 Ultra', 2, 28000000, 15, 'Xám Titanium', '512GB', 'Bút S-Pen thần thánh, AI đỉnh cao', 1, 10),
                                                                                                                      ('Samsung Galaxy A54', 2, 8500000, 30, 'Xanh Trendy', '128GB', 'Tầm trung đáng mua nhất', 0, 0),
                                                                                                                      ('Xiaomi 14', 3, 18900000, 5, 'Đen', '256GB', 'Ống kính Leica chuyên nghiệp', 0, 0),
                                                                                                                      ('Oppo Reno11 F', 4, 9000000, 12, 'Tím', '128GB', 'Chuyên gia chân dung', 0, 0);

INSERT INTO users (username, password, role, full_name, email, phone, address) VALUES
                                                                                   ('admin_shop', 'hashed_password_123', 'ADMIN', 'Quản Trị Viên', 'admin@phonestore.com', '0912345678', '123 Đường ABC, Hà Nội'),
                                                                                   ('khachhang01', 'password_abc', 'CUSTOMER', 'Nguyễn Văn A', 'vana@gmail.com', '0988776655', '456 Đường XYZ, TP.HCM'),
                                                                                   ('khachhang02', 'password_def', 'CUSTOMER', 'Trần Thị B', 'thib@gmail.com', '0977112233', '789 Đường LMN, Đà Nẵng');

INSERT INTO orders (user_id, total_amount, status) VALUES
    (2, 43500000, 'PENDING');

INSERT INTO order_details (order_id, product_id, quantity, price_at_purchase) VALUES
                                                                                  (1, 1, 1, 30000000),
                                                                                  (1, 2, 1, 13500000);