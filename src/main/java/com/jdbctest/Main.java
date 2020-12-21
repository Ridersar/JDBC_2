package com.jdbctest;

import com.jdbctest.entity.Product;
import com.jdbctest.repository.ProductRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Configuration
@ComponentScan("com.jdbctest")
public class Main {
    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        ProductRepository productRepository = applicationContext.getBean(ProductRepository.class);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("Функции:");
            System.out.println("1 - ввести данные");
            System.out.println("2 - вывести данные");
            System.out.println("3 - поиск по id");
            System.out.println("4 - обновить данные");
            System.out.println("другое - завершить работу");
            int number = sc.nextInt();
            if (number == 1) {
                System.out.println("Введите название товара");
                String name = sc.next();
                insert(productRepository, name);
            } else if (number == 2) {
                printAll(productRepository);
            } else if (number == 3) {
                System.out.println("Введите id");
                Integer id = sc.nextInt();
                searchId(productRepository, id);
            } else if (number == 4) {
                System.out.println("Введите id");
                Integer id = sc.nextInt();
                System.out.println("Введите новое имя");
                String newName = sc.next();
                updateNameById(productRepository, id, newName);
            } else {
                break;
            }
        }
    }

    //вставка
    public static void insert(ProductRepository productRepository, String name) throws SQLException {
        Product product = productRepository.insertProduct(Product.builder().name(name).build());
        System.out.println("Товар " + product.getName() + " добавлен в базу данных");
    }

    //вывод всей информации
    public static void printAll(ProductRepository productRepository) throws SQLException {
        List<Product> listProduct = productRepository.getProducts();
        System.out.println("Все товары:");
        for (Product product : listProduct) {
            System.out.println("id = " + product.getId() + ", название = " + product.getName());
        }
    }

    //поиск по Id
    public static void searchId(ProductRepository productRepository, Integer id) throws SQLException {
        Product product = productRepository.getProductById(id);
        System.out.println("Товар под id = " + product.getId() + " имеет наименование - " + product.getName());
    }

    //обновление по Id
    public static void updateNameById(ProductRepository productRepository, Integer id, String newName) throws SQLException {
        Product product = productRepository.getProductById(id);
        product.setName(newName);
        productRepository.updateProduct(product);
        System.out.println("Товар под id = " + product.getId() + " теперь имеет наименование - " + product.getName());
    }
}