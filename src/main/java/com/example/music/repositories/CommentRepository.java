package com.example.music.repositories;


import com.example.music.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findAllByCommentId(Long commentId);

    @Query(value = " SELECT c.comment_id, c.description, c.created, u.username, u.image " +
            " FROM comment c INNER JOIN users u " +
            " ON c.user_id = u.user_id WHERE c.song_id = :songId ORDER BY c.created DESC ", nativeQuery = true)
    List<Object[]> findAllBySongId(Long songId);

    @Modifying
    void deleteAllByCommentIdAndUserId(Long commentId, Long UserId);
}
