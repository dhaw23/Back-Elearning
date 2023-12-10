package com.Elrearning.services;

import com.Elrearning.LocalStorageConfig.FileStorageService;
import com.Elrearning.LocalStorageConfig.StorageService;
import com.Elrearning.models.Category;
import com.Elrearning.models.Course;
import com.Elrearning.models.User;
import com.Elrearning.repository.CategoryRepository;
import com.Elrearning.repository.CourseRepository;
import com.Elrearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Service
public class CourseService {


    @Autowired
    private  final CourseRepository courseRepository ;
    private final CategoryRepository categoryRepository;
    private  final UserRepository userRepository ;
    private final Path rootLocation = Paths.get("uploads");

    private final StorageService storageService;


    public CourseService(CourseRepository courseRepository, CategoryRepository categoryRepository, UserRepository userRepository, FileStorageService fileStorageService, StorageService storageService) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;

        this.storageService = storageService;
    }
    public List<Course> getall () {

return  courseRepository.findAll();

    }
    public Course getonecour (Long id) {

        return  courseRepository.findById(id).orElse(null);

    }
    public ResponseEntity<?> addcourse (MultipartFile imageFile, Course courseDTO, int id_user, int id_category ) {
        Category category = categoryRepository.findById(id_category).orElse(null);
        courseDTO.setCategory(category);
        User user = userRepository.findById(id_user).orElse(null);
        courseDTO.setUser(user);
        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().indexOf('.'), imageFile.getOriginalFilename().length());
            String name=imageFile.getOriginalFilename().substring(0,imageFile.getOriginalFilename().indexOf('.'));
            String original=name+fileName+ext;
            Files.copy(imageFile.getInputStream(), this.rootLocation.resolve(original));
            courseDTO.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
        return  ResponseEntity.ok(courseRepository.save(courseDTO)) ;
    }

    public List<Course> getCourssesOfTeacher(Long id){
        List<Course> courseResult = courseRepository.findBycourseAndTeacher(id);
        System.out.println("==============");
        for (Course course : courseResult) {
            // Log course details
            System.out.println("Course Description: " + course.getCourse_description());
            System.out.println("User ID: " + course.getUser().getUserId());
        }
        return courseRepository.findBycourseAndTeacher(id);
    }

    public void deletecours (Long id ) {
        courseRepository.deleteById(id);
    }

}
