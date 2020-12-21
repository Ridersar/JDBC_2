package com.jdbctest.mapper;

import com.jdbctest.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        return Product.builder().
                id(resultSet.getInt(1)).
                name(resultSet.getString(2)).
                build();
    }
}
