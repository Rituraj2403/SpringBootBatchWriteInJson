package com.springboot.batch.writeinjson.config;

import com.springboot.batch.writeinjson.listener.JobListener;
import com.springboot.batch.writeinjson.model.Product;
import com.springboot.batch.writeinjson.model.ProductDTO;
import com.springboot.batch.writeinjson.processor.ProductProcessor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

// Read from Database and Write to Json File
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

	
    
    @Bean
    @StepScope
    public JsonFileItemWriter<Product> writer(@Value("#{jobParameters['prodPath']}") String prodPath) {
        Resource productResource = new FileSystemResource(prodPath);

        return new JsonFileItemWriterBuilder<Product>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>()) // use jackson for marshalling or converting object into json or xml format
                .resource(productResource)
                .name("jsonFileItemWriter")
                .build();
    }
    
    
    @Bean
    public ProductProcessor processor() {
        return new ProductProcessor();
    }
    
    @Bean(destroyMethod="")
    public JdbcCursorItemReader<ProductDTO> itemReader()  throws ItemStreamException{
    	return new JdbcCursorItemReaderBuilder<ProductDTO>()
    			.dataSource(this.dataSource)
    			.name("creditReader")
    			.sql("select p_FirstName,p_LastName,p_CompanyName from Product")
    			.rowMapper(new ProductRowMapper())
    			.build();

    }
    
    // Read from H2 Database all records
    public class ProductRowMapper implements RowMapper<ProductDTO> {

        public static final String FNNAME_COLUMN = "p_FirstName";
        public static final String lNAME_COLUMN = "p_LastName";
        public static final String COMPNAME_COLUMN = "p_CompanyName";

        public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ProductDTO productDTO = new ProductDTO();

        	productDTO.setP_FirstName(rs.getString(FNNAME_COLUMN));
        	productDTO.setP_LastName(rs.getString(lNAME_COLUMN));
        	productDTO.setP_CompanyName(rs.getString(COMPNAME_COLUMN));

            return productDTO;
        }
    }



    @Bean
    public Job importUserJob(JobListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<ProductDTO, Product> chunk(10)
                .reader(itemReader()) //Read from Mysql Database and Put in Pojo
                .processor(processor()) // from Pojo put into DTO
                .writer(writer("")) //Took from DTO and Write to Json File
                .build();
    }

}

//jobParameters  is properties files automatic build


//Note:--
//Name of DTO in interface give as from developer point of view :--
// it help to understand interface work easy as we need DTO help to easily distinguish steps because earlier any name interface bind to any DTO so Difficult know, 

//secondly DTO is common between Class and Interface so reusable led to save memory also. as Business Point Of View
