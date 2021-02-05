package com.michaelklanica.listology.repos;

import com.michaelklanica.listology.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParent(Comment parent);
}
