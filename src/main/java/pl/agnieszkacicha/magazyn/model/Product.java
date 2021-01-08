package pl.agnieszkacicha.magazyn.model;

import javax.persistence.*;

@Entity(name = "tproduct")
public class Product {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private String name;
    private int pieces;
    private double price;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Product(int id, String code, String name, int pieces, double price, Category category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.pieces = pieces;
        this.price = price;
        this.category = category;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        return new Product (this.id,this.code, this.name, this.pieces, this.price, this.category);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Product{")
                .append("id=")
                .append(id)
                .append(", code='")
                .append(code)
                .append(", name='")
                .append(name)
                .append(", pieces=")
                .append(pieces)
                .append(", price=")
                .append(price)
                .append(", category=")
                .append(category)
                .append('}').toString();
    }

}
