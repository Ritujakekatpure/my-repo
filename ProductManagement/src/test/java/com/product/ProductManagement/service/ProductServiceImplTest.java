package com.product.ProductManagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import com.product.ProductManagement.Entity.Product;
import com.product.ProductManagement.repository.ProductRepository;


class ProductServiceImplTest {
	
	
	@Mock
	private ProductRepository productRepo;
	
	private ProductService productService;
	
	AutoCloseable autoCloseable;
	
	Product product;

	@AfterEach
	 void tearDown() throws Exception {
		autoCloseable .close();
	}

	@BeforeEach
	void setUp() throws Exception {
		
		autoCloseable = MockitoAnnotations.openMocks(this);
		productService = new ProductServiceImpl(productRepo);
		product = new Product(1,"Laptop","1 year","123bhM" ,new Date(2023-03-07));
	}

	@Test
	void testSaveProduct() {
		mock(Product.class);
		mock(ProductRepository.class);
		
		when(productRepo.save(product)).thenReturn(product);
		assertThat( productService.saveProduct(product)).isEqualTo(product);
		
	}

	@Test
	void testGetAllProduct() {
		mock(Product.class);
		mock(ProductRepository.class);
		
		when(productRepo.findAll()).thenReturn(new ArrayList<Product>(Collections.singleton(product)));
		assertThat(productService.getAllProduct().get(0).getProductName()).isEqualTo(product.getProductName());
		
	}

	@Test
	void testGetProductById() {
		mock(Product.class);
		mock(ProductRepository.class);
		
		when(productRepo.findById(1)).thenReturn(Optional.ofNullable(product));
		assertThat(productService.getProductById(1).getProductName()).isEqualTo(product.getProductName());
		
	}

	@Test
    public void testDeleteProduct() {
        // Mock product
        Product product = new Product();
        product.setId(1);
        product.setProductName("Test Product");
        product.setWarranty("2 years");
        product.setBillNumber("12345");
        product.setDate(new Date(2023-07-03));

        // Mock repository behavior
        when(productRepo.findById(anyInt())).thenReturn(Optional.of(product));
        Mockito.doNothing().when(productRepo).delete(product);

        // Perform the test
        String result = productService.deleteProduct(1);

        // Verify the result
        assertEquals("Product deleted Succesfully", result);
	}

	@Test
	void testEditProduct() {
//		mock(Product.class);
//		mock(ProductRepository.class);
//		
//		when(productRepo.save(product)).thenReturn(product);
//		assertThat( productService.editProduct(product,1)).isEqualTo(product);
		Product product = new Product();
        product.setProductName("New Product");
        product.setWarranty("2 years");
        product.setBillNumber("12345");
        product.setDate(new Date(2023-07-02));

        Product oldProduct = new Product();
        oldProduct.setId(1);
        oldProduct.setProductName("Old Product");
        oldProduct.setWarranty("1 year");
        oldProduct.setBillNumber("67890");
        oldProduct.setDate(new Date(2023-07-02));

        // Mock repository behavior
        when(productRepo.findById(anyInt())).thenReturn(Optional.of(oldProduct));
        when(productRepo.save(oldProduct)).thenReturn(oldProduct);
        
        Product editedProduct = productService.editProduct(product, 1);

        // Verify the result
        assertEquals(product.getProductName(), editedProduct.getProductName());
        assertEquals(product.getWarranty(), editedProduct.getWarranty());
        assertEquals(product.getBillNumber(), editedProduct.getBillNumber());
        assertEquals(product.getDate(), editedProduct.getDate());

        // Verify that the repository methods were called appropriately
        Mockito.verify(productRepo).findById(1);
        Mockito.verify(productRepo).save(oldProduct);
        
        
		
		
	}

}
