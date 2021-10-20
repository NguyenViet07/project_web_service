package com.example.music.repositories;

import com.example.music.model.Song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Song findAllBySongId(Long id);

    Page<Song> findAllByUserId(Long id, Pageable page);
}
