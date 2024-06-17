package com.haru4cut.comment;

import com.haru4cut.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findCommentsByDiary(Diary diary);
}
