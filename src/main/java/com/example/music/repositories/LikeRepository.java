package com.example.music.repositories;

import com.example.music.model.Like;
import com.example.music.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

}
