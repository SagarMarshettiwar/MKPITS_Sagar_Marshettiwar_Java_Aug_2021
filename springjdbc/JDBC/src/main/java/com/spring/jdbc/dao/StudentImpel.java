package com.spring.jdbc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.spring.jdbc.entities.Student;

public class StudentImpel implements StudentDao{
	private JdbcTemplate jdbctemplate;
	@Override
	public int insert(Student student) {
		String query="Insert into Student (id,name,city) values (?,?,?)";
		int r = this.jdbctemplate.update(query,student.getId(),student.getName(),student.getCity());
		return r;
	}
	
	@Override
	public int update(Student student) {
		String query="update Student set name= ?,city=? where id=?";
		int r = this.jdbctemplate.update(query,student.getName(),student.getCity(),student.getId());
		return r;
	}
	
	@Override
	public int delete(Student student) {
		String query="Delete from student where id=?";
		int r = this.jdbctemplate.update(query,student.getId());
		return r;
	}
	
	@Override
	public Student getStudent(int Studentid) {
		String query="select * from Student where id=?";
		RowMapper<Student> rmi=new RowMapperImpl();
		Student student = this.jdbctemplate.queryForObject(query, rmi,Studentid);
		return student;
	}
	
	@Override
	public List<Student> getStudentlist() {
		String query="select * from Student";
		List<Student> list = this.jdbctemplate.query(query,new RowMapperImpl());
		return list;
	}
	
	public JdbcTemplate getJdbctemplate() {
		return jdbctemplate;
	}
	public void setJdbctemplate(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	
}
