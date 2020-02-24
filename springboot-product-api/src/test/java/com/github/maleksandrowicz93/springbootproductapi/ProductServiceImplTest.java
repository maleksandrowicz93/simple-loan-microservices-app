package com.github.maleksandrowicz93.springbootproductapi;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Log4j
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductServiceImpl productService;

    private Product expectedProduct = new Product();
    private List<Product> productDBImage = new ArrayList<>();

    @BeforeAll
    static void beforeTests() {
        BasicConfigurator.configure();
        log.info("Starting ProductServiceImpl tests...");
    }

    @BeforeEach
    void setup() {
        expectedProduct.setProductName(ProductEnum.CONSOLIDATION);
        expectedProduct.setValue(1000);
        expectedProduct.setCreditId(1);
        productDBImage.addAll(prepareMockData());
    }

    private List<Product> prepareMockData() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(1, ProductEnum.CONSUMER, 3000);
        products.add(product1);
        Product product2 = new Product(2,ProductEnum.INVESTMENT, 40000);
        products.add(product2);
        Product product3 = new Product(2,ProductEnum.MORTGAGE, 500000);
        products.add(product3);
        Product product4 = new Product(3, ProductEnum.CARD, 500);
        products.add(product4);
        return products;
    }

    @Test
    void save_product() {
        //given
        mockSaveProduct();
        //when
        productService.saveProduct(new Product());
        //then
        assertTrue(productDBImage.contains(expectedProduct));
    }

    private void mockSaveProduct() {
        when(productRepo.save(any(Product.class))).thenAnswer(invocationOnMock ->  {
            productDBImage.add(expectedProduct);
            return expectedProduct;
        });
    }

    @Test
    void save_existing_product() {
        //given
        mockSaveProduct();
        //when
        productService.saveProduct(new Product());
        productService.saveProduct(new Product());
        //then
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            if (productDBImage.get(productDBImage.size()-1) == productDBImage.get(productDBImage.size()-2))
                throw new SQLIntegrityConstraintViolationException();
        });
    }

    @Test
    void get_products() {
        //given
        List<Integer> ids = Arrays.asList(1, 2);
        when(productRepo.findAll()).thenReturn(prepareMockData());
        //when
        List<Product> products = productService.getProducts(ids);
        //then
        assertEquals(3, products.size());
    }

    @Test
    void convert_from_dto() {
        //given
        ProductDto toConvert = new ProductDto(
                expectedProduct.getCreditId(),
                expectedProduct.getProductName(),
                expectedProduct.getValue()
        );
        //when
        Product converted = productService.convertFromDto(toConvert);
        //then
        assertTrue(expectedProduct.getProductName().equals(converted.getProductName())
                && expectedProduct.getValue().intValue() == converted.getValue().intValue()
                && expectedProduct.getCreditId().intValue() == converted.getCreditId().intValue());
    }

    @Test
    void convert_from_product_list() {
        //given
        List<Product> products = prepareMockData();
        //when
        List<ProductDto> productDtoList = productService.convertFromProductList(products);
        //then
        assertEquals(products.size(), productDtoList.size());
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            ProductDto productDto = productDtoList.get(i);
            assertTrue(productDto.getProductName().equals(p.getProductName())
                    && productDto.getValue() == p.getValue().intValue()
                    && productDto.getCreditId() == p.getCreditId());
        }
    }

}