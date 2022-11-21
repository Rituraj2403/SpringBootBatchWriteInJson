package com.springboot.batch.writeinjson;

import java.util.Date;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.springboot.batch.writeinjson.util.CourseUtilsDefaultTrigger;

@SpringBootApplication
public class SpringBootBatchJsonExampleApplication {
	// Read from MySql Database name GreatIndia with Product Table and Write to Json File(product.json)
	public static void main(String[] args) throws JobExecutionException, InterruptedException {
		ConfigurableApplicationContext appContext = SpringApplication.run(SpringBootBatchJsonExampleApplication.class, args);
		CourseUtilsDefaultTrigger trigger = appContext.getBean(CourseUtilsDefaultTrigger.class);
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addParameter("prodPath", new JobParameter("ProdDBJsonFile/product.json"));
		builder.addDate("date", new Date());
		trigger.runJobs(builder.toJobParameters());
	}

}
