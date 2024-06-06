package com.JobPortal.Response;

import com.JobPortal.Model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private User user;
    private String response;
    private boolean isSuccess;
}
