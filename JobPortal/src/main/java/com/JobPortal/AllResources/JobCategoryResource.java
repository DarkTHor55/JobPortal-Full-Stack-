package com.JobPortal.AllResources;

import com.JobPortal.Model.Job;
import com.JobPortal.Model.JobCategory;
import com.JobPortal.Model.User;
import com.JobPortal.Response.JobResponse;
import com.JobPortal.Response.UserResponse;
import com.JobPortal.ServiceImpl.JobCategoryServiceImpl;
import com.JobPortal.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
@Component
public class JobCategoryResource {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JobCategoryServiceImpl jobCategoryService;
    public ResponseEntity<UserResponse> addCategory(JobCategory category) {
        UserResponse response = new UserResponse();

        if(category==null){
            response.setSuccess(false);
            response.setResponseMessage("Empty input");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        JobCategory cat = jobCategoryService.addJobCategory(category);
        if (cat==null){
            response.setSuccess(false);
            response.setResponseMessage("Category not added");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else{
            response.setSuccess(true);
            response.setResponseMessage("Category added");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<UserResponse> updateCategory(JobCategory category,Long catId) {
        UserResponse response = new UserResponse();
        JobCategory oldCategory = jobCategoryService.getJobCategoryById(catId);
        if(category==null){
            response.setSuccess(false);
            response.setResponseMessage("Empty input");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (oldCategory==null){
            response.setSuccess(false);
            response.setResponseMessage("Category not found");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        category.setId(oldCategory.getId());
        if(category.getName()==null){category.setName(oldCategory.getName());}
        if (category.getDescription()==null){category.setDescription(oldCategory.getDescription());}
        if (category.getStatus()==null){category.setStatus(oldCategory.getStatus());}
        jobCategoryService.deleteJobCategory(oldCategory.getId());
        JobCategory cat = jobCategoryService.updateJobCategory(category);
        if (cat==null){
            response.setSuccess(false);
            response.setResponseMessage("Category not added");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else{

            response.setSuccess(true);
            response.setResponseMessage("Category added");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }


    }

    public ResponseEntity<List<JobCategory>> fetchAllCategory() {
        UserResponse response = new UserResponse();
        List<JobCategory> categories = jobCategoryService.getAllCategories();
        if (categories.isEmpty()){
            response.setSuccess(false);
            response.setResponseMessage("No category found");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        else{
            response.setSuccess(true);
            response.setResponseMessage("Category found");
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }
    public ResponseEntity<JobCategory>fetchById(Long categoryId){
        JobCategory jobCategory = new JobCategory();
        jobCategory =jobCategoryService.getJobCategoryById(categoryId);
        if (jobCategory==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(jobCategory, HttpStatus.OK);
        }



    }
    public ResponseEntity<UserResponse> deleteCategory(Long categoryId) {
        UserResponse response=new UserResponse();
        JobCategory category=jobCategoryService.getJobCategoryById(categoryId);
        if(category==null){
            response.setSuccess(false);
            response.setResponseMessage("Category not found");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

        }
        else{
            jobCategoryService.deleteJobCategory(categoryId);
            response.setSuccess(true);
            response.setResponseMessage("Category deleted");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }
}
