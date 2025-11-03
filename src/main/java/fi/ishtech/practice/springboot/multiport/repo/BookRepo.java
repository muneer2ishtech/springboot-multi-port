package fi.ishtech.practice.springboot.multiport.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ishtech.practice.springboot.multiport.entity.Book;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface BookRepo extends JpaRepository<Book, Long> {

}
