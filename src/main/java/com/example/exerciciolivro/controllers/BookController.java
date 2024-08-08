package com.example.exerciciolivro.controllers;

import com.example.exerciciolivro.dtos.BookRecordDTO;
import com.example.exerciciolivro.models.BookModel;
import com.example.exerciciolivro.repositories.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<BookModel>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findAll());
    }

    @GetMapping("/filter/{genre}")
    public ResponseEntity<List<BookModel>> getAllBooksByGenre(@PathVariable(value = "genre") String genre) {
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findAllByGenreIgnoreCase(genre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable(value = "id") UUID id) {
        Optional<BookModel> bookO = bookRepository.findById(id);

        if (bookO.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(bookO.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }

    @PostMapping
    public ResponseEntity<BookModel> createBook(@RequestBody @Valid BookRecordDTO bookDTO) {
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(bookDTO, bookModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(bookModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") UUID id, @RequestBody @Valid BookRecordDTO bookDTO) {
        Optional<BookModel> bookO = bookRepository.findById(id);

        if (bookO.isPresent()) {
            var bookModel = bookO.get();
            BeanUtils.copyProperties(bookDTO, bookModel);

            return ResponseEntity.status(HttpStatus.OK).body(bookRepository.save(bookModel));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable(value = "id") UUID id) {
        Optional<BookModel> bookO = bookRepository.findById(id);

        if (bookO.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }
}
