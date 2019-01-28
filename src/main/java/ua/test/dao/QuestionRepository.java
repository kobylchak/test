package ua.test.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.module = :module")
    List<Question> findByModule(@Param("module") Module module, Pageable pageable);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.module = :module")
    long countByModule(@Param("module") Module module);

    @Query("SELECT q FROM Question q WHERE LOWER(q.question) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Question> findByPattern(@Param("pattern") String pattern, Pageable pageable);
}
