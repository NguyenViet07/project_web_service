package com.example.music.repositories;

import com.example.music.model.Like;
import com.example.music.model.Song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Song findAllBySongId(Long id);

    Page<Song> findAllByUserId(Long id, Pageable page);

    @Query(value = "SELECT * FROM song s ORDER BY s.CREATEd DESC LIMIT 0, 6 ", nativeQuery = true)
    List<Song> getListSongCreated();

    @Query(value = "SELECT * FROM song s ORDER BY s.views DESC LIMIT 0, 6 ", nativeQuery = true)
    List<Song> getListSongView();
}
