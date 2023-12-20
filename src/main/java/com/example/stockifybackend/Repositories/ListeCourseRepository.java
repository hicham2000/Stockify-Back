package com.example.stockifybackend.Repositories;

import com.example.stockifybackend.Entities.ListeCourse;
import com.example.stockifybackend.Entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListeCourseRepository extends JpaRepository<ListeCourse,Long> {


}
