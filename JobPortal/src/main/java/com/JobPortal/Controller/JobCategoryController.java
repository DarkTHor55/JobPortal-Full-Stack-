package com.JobPortal.Controller;

import com.JobPortal.AllResources.JobCategoryResource;
import com.JobPortal.Configuration.JwtUtils;
import com.JobPortal.Model.JobCategory;
import com.JobPortal.Model.User;
import com.JobPortal.Repository.JobCategoryRepository;
import com.JobPortal.Response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobCategoryController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JobCategoryResource categoryResource;

    @PostMapping("/category/add")
    public ResponseEntity<UserResponse> addCategory(@RequestBody JobCategory category) {
        return categoryResource.addCategory(category);
    }

    @PutMapping("/category/update")
    public ResponseEntity<UserResponse> updateCategory(@RequestBody Long categoryId,@RequestBody JobCategory category) {
        return categoryResource.updateCategory(category, categoryId);
    }

    @GetMapping("/category/fetch/all")
    public ResponseEntity<List<JobCategory>> fetchAllCategory() {
        return categoryResource.fetchAllCategory();
    }

    @DeleteMapping("/category/delete")
    public ResponseEntity<UserResponse> deleteCategory(@RequestParam("categoryId") Long categoryId) {
        return categoryResource.deleteCategory(categoryId);
    }
    @GetMapping("/category/fetch")
    public ResponseEntity<JobCategory> fetchById(@RequestParam("categoryId") Long categoryId) {
        return categoryResource.fetchById(categoryId);
    }
}
