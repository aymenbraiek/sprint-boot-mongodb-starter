package com.sampleapp.controller;

import com.sampleapp.entity.Student;
import com.sampleapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Collection<Student> index() {
        return studentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody Student student) {
        return studentService.save(student);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> show(@PathVariable int id) {
        return Optional
                .ofNullable(studentService.findOneById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @Valid @RequestBody Student student) {
        return Optional
                .ofNullable(studentService.findOneById(id))
                .map(s -> studentService.updateOneById(id, student))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return Optional
                .ofNullable(studentService.findOneById(id))
                .map(student -> {
                    studentService.deleteOneById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}