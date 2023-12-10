package com.Elrearning.controllers;

import com.Elrearning.models.Course;
import com.Elrearning.models.CourseDTO;
import com.Elrearning.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/course")
@CrossOrigin("*")
public class CourseController {
@Autowired
private final CourseService courseService ;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/addcourse/{id_user}/{id_category}")
    public ResponseEntity<?> addcourse (@RequestParam("imageFile") MultipartFile imageFile, Course course , @PathVariable int id_user, @PathVariable int id_category ) {


        return  ResponseEntity.ok(courseService.addcourse(imageFile,course, id_user,id_category ));


    }

    @GetMapping("/getAllCours")
    public ResponseEntity<?> getAllCourses(){
        return  ResponseEntity.ok(courseService.getall());
    }

    @GetMapping("/getOneCours/{id}")
    public ResponseEntity<?> getOneCour(@PathVariable Long id){
        return  ResponseEntity.ok(courseService.getonecour(id));
    }

    @GetMapping("/getAllcourseOfTeacher/{id}")
    public ResponseEntity<?> getAllCoursesOfTeacher(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourssesOfTeacher(id));
    }
    @DeleteMapping("/deletecours/{id}")
    public ResponseEntity<?> deletecours(@PathVariable Long id ) {
        if (courseService.getonecour(id)!=null){
            courseService.deletecours(id);
            return  new ResponseEntity<>("DELETED", HttpStatus.OK);
        }else return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);

    }






}
