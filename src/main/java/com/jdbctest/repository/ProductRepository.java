package com.jdbctest.repository;

import com.jdbctest.entity.Product;
import com.jdbctest.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Component
public class ProductRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Product getProductById(Integer id){
        return jdbcTemplate.queryForObject("SELECT * from product where id = ?",new ProductMapper(), id);
    }

    public String getProductNameById(Integer id){
        return jdbcTemplate.queryForObject("SELECT name from product where id = ?",String.class,id);
    }

    public List<Map<String,Object>> getProductPlain(){
        return jdbcTemplate.queryForList("SELECT * from product");
    }

    public List<Product> getProducts(){
        return jdbcTemplate.query("SELECT * from product", new ProductMapper());
    }

    public void updateProduct(Product product){
        String query = "UPDATE product SET name = ?";
        jdbcTemplate.update(query, product.getName());
    }

    public Product insertProduct(Product product){
        String query = "INSERT INTO Product (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            return preparedStatement;
        }, keyHolder);
        return getProductById(keyHolder.getKey().intValue());
    }
}
