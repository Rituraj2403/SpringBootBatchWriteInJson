package com.springboot.batch.writeinjson.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.springboot.batch.writeinjson.model.ProductDTO;

import java.util.List;

@Component
public class JobListener extends JobExecutionListenerSupport {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
           // System.out.println("In Completion Listener ..");
           
//			List<ProductDTO> results = jdbcTemplate.query("SELECT p_FirstName,p_LastName,p_CompanyName FROM Product",
//                    (rs,rowNum)->{
//                        return new ProductDTO(rs.getString(1), rs.getString(2),rs.getString(3));
//                    }
//            );
//            results.forEach(System.out::println);
        }
    }
}
