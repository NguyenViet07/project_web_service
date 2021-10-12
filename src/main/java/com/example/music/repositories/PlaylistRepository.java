package com.example.music.repositories;

import com.example.music.model.Playlist;
import com.example.music.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}
