package com.aivle.bookapp.repository;

import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUser_UserIdAndBook_Id(String userId, Long bookId);
    boolean existsByUser_UserIdAndBook_id(String userId, Long bookId);
    long countByBook_Id(Long bookId);
    void deleteByBook(Book book);

    @Query("select l.book.id from Likes l where l.user.userId = :userId")
    List<Long> findBookIdsByUserId(@Param("userId") String userId);
}
