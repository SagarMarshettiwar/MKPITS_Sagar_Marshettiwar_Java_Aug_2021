package com.example.springjdbc.dao;

import com.example.springjdbc.Stud;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;

public class Studdaoimpl implements StudDao{
    JdbcTemplate template;

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void insert(Stud ob) {
        String query="insert into students (id,name,job) values (?,?,?)";
        int sts=this.template.update(query,ob.getId(),ob.getName(),ob.getJob());
        System.out.println("record inserted "+sts);


    }

    @Override
    public void update(Stud ob) {
        String query="update students set name=?,job=? where id=?";
        int sts=this.template.update(query,ob.getName(),ob.getJob(),ob.getId());
        System.out.println("record updated "+sts);
    }

    @Override
    public void delete(Stud ob) {
        String query="delete from  students where id=?";
        int sts=this.template.update(query,ob.getId());
        System.out.println("record deleted "+sts);
    }

    @Override
    public void displaybyid(int id) {
        String query="Select name from students where id=?";
        String s=this.template.queryForObject(query,new Object[]{id},String.class);
        System.out.println(s);
    }
}
