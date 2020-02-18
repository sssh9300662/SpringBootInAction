package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository 18 methods for performing common persistence operations
 * interface is parameterized with two parameters: the domain type that the repository will work with, and the type of its ID property
 * @author User
 *
 */
public interface ReadingListRepository extends JpaRepository<Book, Long> {
	List<Book> findByReader(String reader);

}
