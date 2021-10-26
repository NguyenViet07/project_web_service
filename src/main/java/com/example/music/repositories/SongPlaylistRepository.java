package com.example.music.repositories;


import com.example.music.model.SongPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, SongPlaylist.PlaylistSongId> {

    @Query(value = "SELECT * FROM song_playlist WHERE song_id = :songId", nativeQuery = true)
    SongPlaylist findAllById(Long songId);
}
