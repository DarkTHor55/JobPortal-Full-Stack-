package com.JobPortal.Response;

import com.JobPortal.Model.JobApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationResponse extends JobResponse {
    private List<JobApplication> applications = new ArrayList<>();
}
