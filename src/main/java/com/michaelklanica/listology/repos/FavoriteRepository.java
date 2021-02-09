package com.michaelklanica.listology.repos;

import com.michaelklanica.listology.models.Favorite;
import com.michaelklanica.listology.models.Post;
import com.michaelklanica.listology.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    void deleteAllByUser(User user);
    void deleteAllByPost(Post post);
}
