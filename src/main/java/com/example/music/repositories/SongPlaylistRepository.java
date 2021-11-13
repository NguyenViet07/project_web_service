package com.example.music.repositories;


import com.example.music.model.SongPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, SongPlaylist.PlaylistSongId> {

    @Query(value = "SELECT * FROM song_playlist WHERE song_id = :songId", nativeQuery = true)
    SongPlaylist findAllById(Long songId);

    @Query(value = "SELECT * FROM song_playlist WHERE song_id = :songId and playlist_id = :playlistId  ", nativeQuery = true)
    SongPlaylist findAllBySongIdAndPlId(Long songId, Long playlistId);

    @Query(value = "DELETE  FROM song_playlist WHERE playlist_id = :playlistId  ", nativeQuery = true)
    void deleteAllByPlaylistId(Long playlistId);
}
