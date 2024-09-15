package com.tourapi.mandi.domain.course.repository;

import com.tourapi.mandi.domain.course.entity.CompletedCourse;
import com.tourapi.mandi.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompletedCourseRepository extends JpaRepository<CompletedCourse, Long> {
    @Query("select cc from CompletedCourse cc join fetch cc.course where cc.user = :user")
    List<CompletedCourse> findByUser(@Param("user") User user);
}
