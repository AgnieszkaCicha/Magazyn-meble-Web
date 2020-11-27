package pl.agnieszkacicha.magazyn.model;

public class Product {

    private String code;
    private String name;
    private int pieces;
    private double price;
    private Category category;

    public Product(String code, String name, int pieces, double price, Category category) {
        this.code = code;
        this.name = name;
        this.pieces = pieces;
        this.price = price;
        this.category = category;
    }

    public Product() {
    }

    public String getCode() { return code; }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public enum Category {
        FURNITURE,
        AGD
    }

    @Override
    public Object clone() {
        return new Product (this.code, this.name, this.pieces, this.price, this.category);
    }

}
