package com.Elrearning.repository;

import com.Elrearning.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CourseRepository  extends JpaRepository<Course,Long> {

    @Query("select u from  Course  u  where u.user.userId= :id")
    List<Course> findBycourseAndTeacher(@Param("id") Long userId) ;

}
