package com.example.exerciciolivro.repositories;

import com.example.exerciciolivro.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookModel, UUID> {
    List<BookModel> findAllByGenreIgnoreCase(String genre);
}
