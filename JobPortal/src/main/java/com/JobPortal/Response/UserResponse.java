package com.JobPortal.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private String responseMessage;
    private boolean isSuccess;

}
