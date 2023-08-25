package com.product.ProductManagement.Controller;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.product.ProductManagement.Entity.Product;
import com.product.ProductManagement.service.ProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	Product product_1;
	Product product_2;

	List<Product> productList = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		product_1 = new Product(1, "Laptop", "1 year", "123bhM", new Date(2023 - 03 - 07));
		product_2 = new Product(2, "PC", "2 year", "125bhM", new Date(2023 - 04 - 23));
		productList.add(product_1);
		productList.add(product_2);

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSaveProduct() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(product_1);

		when(productService.saveProduct(product_1)).thenReturn(product_1);
		this.mockMvc.perform(post("/saveProduct").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	void testGetAllProduct() throws Exception {
		when(productService.getAllProduct()).thenReturn(productList);
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testGetProductById() throws Exception {
		when(productService.getProductById(1)).thenReturn(product_1);
		this.mockMvc.perform(get("/"+"1")).andDo(print()).andExpect(status().isOk());

	}

	@Test
	void testDeleteProduct() throws Exception {
		when(productService.deleteProduct(1)).thenReturn("Product deleted Succesfully");
		this.mockMvc.perform(delete("/deleteProduct/" + "1")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testEditProduct() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(product_1);

        when(productService.editProduct(product_1,1))
                .thenReturn(product_1);
        this.mockMvc.perform(put("/editProduct/"+"1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isCreated());
	}

}
