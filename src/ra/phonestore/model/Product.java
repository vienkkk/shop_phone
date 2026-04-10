package ra.phonestore.model;

public class Product {
    private int id;
    private String name;
    private int categoryId;
    private double price;
    private int stock;
    private String color;
    private String capacity;
    private String description; // 1. Khai báo biến này để hết lỗi "Cannot resolve symbol"

    // Constructor không tham số (Bắt buộc phải có)
    public Product() {}

    // Constructor đầy đủ tham số
    public Product(int id, String name, int categoryId, double price, int stock, String color, String capacity, String description) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
        this.color = color;
        this.capacity = capacity;
        this.description = description;
    }

    // --- GETTERS AND SETTERS ---

    // 2. Bổ sung getDescription và setDescription để hết lỗi "Cannot resolve method"
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
}