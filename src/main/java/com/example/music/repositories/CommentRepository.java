package com.example.music.repositories;


import com.example.music.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findAllByCommentId(Long commentId);

    Page<Comment> findAllBySongId(Long songId, Pageable pageable);

    @Modifying
    void deleteAllByCommentIdAndUserId(Long commentId, Long UserId);
}
