package com.example.music.repositories;


import com.example.music.model.Playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {


    Playlist findAllByPlaylistId(Long playListId);

    List<Playlist> findAllByUserId(Long userId);

}
