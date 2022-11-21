package com.springboot.batch.writeinjson.processor;

import org.springframework.batch.item.ItemProcessor;

import com.springboot.batch.writeinjson.model.Product;
import com.springboot.batch.writeinjson.model.ProductDTO;

//Took Data from DTO and Write to POJO
public class ProductProcessor implements ItemProcessor<ProductDTO, Product> {
	 
    @Override
    public Product process(final ProductDTO productDTO) throws Exception {
        System.out.println("Transforming ProductDTO(s) to Product(s)..");
        final Product product = new Product(productDTO.getP_FirstName(), productDTO.getP_LastName(),
                productDTO.getP_CompanyName());
      
        return product;
    }

}
