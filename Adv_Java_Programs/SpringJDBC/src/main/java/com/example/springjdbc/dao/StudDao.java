package com.example.springjdbc.dao;

import com.example.springjdbc.Stud;

public interface StudDao {
    public void insert(Stud ob);

    public void update(Stud ob);

    public void delete(Stud ob);

    public void displaybyid(int id);
}
