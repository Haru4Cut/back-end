package com.haru4cut.diary;

import com.haru4cut.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findDiaryById(Long diaryId);
    List<Diary> findDiariesByUsers(Users users);
    Diary findDiaryByUsersAndDate(Users users, String date);
}
