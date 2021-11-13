package com.example.music.repositories;

import com.example.music.model.Like;
import com.example.music.model.Song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "SELECT * FROM song s WHERE album_id = :albumId ", nativeQuery = true)
    List<Song> getListSongByAlbumId(Long albumId);

    @Modifying
    @Query(value = "UPDATE song s set s.album_id = null WHERE song_id in :list ", nativeQuery = true)
    void deleteAlbum(List<Long> list);

    @Query(value = "SELECT s.song_id FROM song s WHERE album_id = :albumId ", nativeQuery = true)
    List<Long> getListSongId(Long albumId);

    @Query(value = " SELECT s.song_id, s.song_name, s.link, s.image, s.views, s.description, s.created, u.username " +
            " FROM (song s INNER JOIN users u ON s.user_id = u.user_id) INNER JOIN song_playlist sp ON s.song_id = sp.song_id " +
            " WHERE sp.playlist_id = :playlistId ", nativeQuery = true)
    List<Object[]> getListSongByPlaylistId(Long playlistId);

    @Query(value = " SELECT s.song_id, s.song_name, s.link, s.image, s.views, s.description, s.created, u.username FROM song s INNER JOIN users u ON s.user_id = u.user_id WHERE s.style = :style ", nativeQuery = true)
    List<Object[]> getListSongByStyle(Long style);

    @Query(value = " SELECT s.song_id, s.song_name, s.link, s.image, s.views, s.description, s.created, u.username FROM song s INNER JOIN users u ON s.user_id = u.user_id WHERE s.style is null or s.style = 7 ", nativeQuery = true)
    List<Object[]> getListSongByStyleIsNull();

    @Query(value = " SELECT s.song_id, s.song_name, s.link, s.image, s.views , s.description, s.created, u.username " +
            " FROM (song s INNER JOIN users u ON s.user_id = u.user_id) " +
            " INNER JOIN (SELECT COUNT(*) cc, song_id FROM like_song GROUP BY song_id ORDER BY cc DESC LIMIT 0, 10 ) " +
            " ls ON s.song_id = ls.song_id ", nativeQuery = true)
    List<Object[]> getListSongByLike();


    @Query(value = " SELECT s.song_id, s.song_name, s.link, s.image, s.views , s.description, s.created, u.username " +
            "FROM (song s INNER JOIN users u ON s.user_id = u.user_id) INNER JOIN (SELECT COUNT(*) cc, song_id " +
            " FROM comment GROUP BY song_id ORDER BY cc DESC LIMIT 0, 10 ) ls ON s.song_id = ls.song_id ", nativeQuery = true)
    List<Object[]> getListSongByComment();

    @Query(value = " SELECT s.song_id, s.song_name, s.link, s.image, s.views , s.description, s.created, u.username  " +
            " FROM (song s INNER JOIN users u ON s.user_id = u.user_id)   " +
            " INNER JOIN like_song ls ON s.song_id = ls.song_id " +
            " WHERE ls.user_id = :userId ", nativeQuery = true)
    List<Object[]> getListMySongByLike(Long userId);

}
