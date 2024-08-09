package com.example.StudentCrud.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.StudentCrud.domain.Student;
import com.example.StudentCrud.repository.StudentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Student> listAll() {
        return repo.findAll();
    }

    public void save(Student std) {
        repo.save(std);
    }

    public Student get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
        resetAutoIncrement();
    }

    @Transactional
    public void resetAutoIncrement() {
        Long maxId = (Long) entityManager.createQuery("SELECT COALESCE(MAX(s.id), 0) FROM Student s").getSingleResult();
        entityManager.createNativeQuery("ALTER TABLE student AUTO_INCREMENT = " + (maxId + 1)).executeUpdate();
    }
}
