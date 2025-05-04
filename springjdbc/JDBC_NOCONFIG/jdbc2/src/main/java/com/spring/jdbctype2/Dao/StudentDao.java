package com.spring.jdbctype2.Dao;

import java.util.List;

import com.spring.jdbctype2.entities.Student;

public interface StudentDao {
	public int insert(Student student);
	public int update(Student student);
	public int delete(Student student);
	public Student getStudent(int Studentid);
	public List<Student> getStudentlist();
}
