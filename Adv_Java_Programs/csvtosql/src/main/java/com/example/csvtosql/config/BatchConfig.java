package com.example.csvtosql.config;

import com.example.csvtosql.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<User> reader(){
        FlatFileItemReader<User> reader=new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("Mozambique_Data.csv"));
        reader.setLineMapper(getLineMapper());
        reader.setLinesToSkip(1);
        return reader;
    }

    private LineMapper<User> getLineMapper() {
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"Source","Date Uploaded","Time Uploaded","Summary","Date","Estimated Time","Killed","Injured","Number of Incidents","High Impact","Source/Info Rating","Location Accuracy","Longitude","Province","Location","Activity Type","Incident Type","Incident Sub-Type","Instigator","Sub-Type","Target","Sub-Type1","Criminal Charges","Physical_Context","Reason for Protest","Number of Protesters","PMU Faction","Tribal Group","Major Storm","Tactic","Unusual Social / Political Tension","Threat Level","Disease Outbreak","Drought"});
        lineTokenizer.setIncludedFields(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33});

        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public UserItemProcessor processor(){
        return new UserItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<User> writer(){
        JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
        writer.setSql("insert into mozambique_data(Source,Date Uploaded,Time Uploaded,Summary,Date,Estimated Time,Killed,Injured,Number of Incidents,High Impact,Source/Info Rating,Location Accuracy,Longitude,Province,Location,Activity Type,Incident Type,Incident Sub-Type,Instigator,Sub-Type,Target,Sub-Type1,Criminal Charges,Physical_Context,Reason for Protest,Number of Protesters,PMU Faction,Tribal Group,Major Storm,Tactic,Unusual Social / Political Tension,Threat Level,Disease Outbreak,Drought) values (:source,:dateUplode,:timeUplode,:summary,:date,:estimatedTime,:killed,:injury,:numberofinsident,:highImpact,:sourceinforating,:locationAccuracy,:longitude,:province,:location,:activityType,:incidentType,:incidentSubtype,:instigator,:subtype,:target,:subtype1,:criminalCharges,:physicalContext,:reasonforprotest,:numberofprotesters,:pmuFaction,:tribalGroup,:majorStorm,:tactic,:uspt,:threatLevel,:diseaseOutbreak,:drought)");
        writer.setDataSource(this.dataSource);
        return writer;
    }

    @Bean
    public Job importUserJob(){
        return this.jobBuilderFactory.get("USER-IMPORT-JOB")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .<User,User>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
