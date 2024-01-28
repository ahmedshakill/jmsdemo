package jms.app.service;

import jms.app.entity.Product;
import jms.app.entity.Sales;
import jms.app.repository.ProductRepository;
import jms.app.repository.SalesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class DummyDataService {
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    public DummyDataService(ProductRepository productRepository, SalesRepository salesRepository) {
        this.productRepository = productRepository;
        this.salesRepository = salesRepository;
    }

    public void generateAndStoreDummyData(){
        String[] productNames = {"Product1", "Product2", "Product3", "Product4", "Product5", "Product6", "Product7", "Product8", "Product9", "Product10"};
        String[] categories = {"Electronics", "Clothing", "Books", "Home Appliances", "Toys"};
        double[] prices = {50.0, 30.0, 20.0, 100.0, 10.0};

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName(productNames[i]);
            product.setCategory(categories[i % categories.length]);
            product.setPrice(prices[i % prices.length]);
            productRepository.save(product);
        }
    }

    public void generateAndStoreDummySales(){
        Random random = new Random();
        List<Product> products = productRepository.findAll();

        for(LocalDate date = LocalDate.now().minusMonths(12); date.isBefore(LocalDate.now()); date = date.plusDays(1)){
            for (Product product : products){
                Sales sales = new Sales();
                sales.setDate(date);
                sales.setProductId(product.getId());
                sales.setQuantity(random.nextInt(10)+1);
                salesRepository.save(sales);
            }
        }
    }

}
