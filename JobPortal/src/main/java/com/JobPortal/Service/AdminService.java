package com.JobPortal.Service;

import com.JobPortal.Model.Admin;
import com.JobPortal.Request.AdminReqest;

public interface AdminService {
    Admin createAdmin(AdminReqest admin) throws Exception;
   Admin updateAdmin(Admin admin);
}
