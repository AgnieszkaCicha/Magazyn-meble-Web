package pl.agnieszkacicha.magazyn.database.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pl.agnieszkacicha.magazyn.database.IProductRepository;
import pl.agnieszkacicha.magazyn.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JDBCProductRepositoryImpl implements IProductRepository {

    @Autowired
    Connection connection;

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tproduct";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                products.add(this.mapResultSetToProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getProductByCategory(Product.Category category) {
        List<Product> products = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tproduct WHERE category=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, category.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(this.mapResultSetToProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProductByCode(String code) {
        try {
            String SQL = "SELECT * FROM tproduct WHERE code=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1,code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return this.mapResultSetToProduct(resultSet);
            }else {
                return null;
            }
            } catch (SQLException e) {
            e.printStackTrace();
            }
        return null;
        }


    @Override
    public void addProduct(Product product) {

        try {
            String SQL = "INSERT INTO tproduct (code,name,price,pieces,category) VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getPieces());
            preparedStatement.setString(5, product.getCategory().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            }
        }

    @Override
    public void updateProduct(Product product) {
        try {
            String SQL = "UPDATE tproduct SET name=?, price=?, pieces=?, category=? WHERE code=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getPieces());
            preparedStatement.setString(5, product.getCategory().toString());

            preparedStatement.executeUpdate();

        }catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setCode(resultSet.getString("code"));
        product.setName(resultSet.getString("name"));
        product.setPieces(resultSet.getInt("pieces"));
        product.setPrice(resultSet.getDouble("price"));
        product.setCategory(Product.Category.valueOf(resultSet.getString("category")));

        return product;
    }
}

