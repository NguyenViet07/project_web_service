package com.example.music.repositories;

import com.example.music.model.Like;
import com.example.music.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeRepository extends JpaRepository<Like, Like.LikeUserSongId> {

    @Query(value = "SELECT * FROM like_song WHERE user_id = :userId and song_id = :songId", nativeQuery = true)
    Like findLikeById(Long userId, Long songId);

    @Modifying
    @Query(value = "DELETE FROM like_song WHERE user_id = :userId and song_id = :songId", nativeQuery = true)
    void deleteLikeAllById(Long userId, Long songId);

}
