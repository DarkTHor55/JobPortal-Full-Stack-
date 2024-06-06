package com.JobPortal.AllResources;

public class Constants {
    public enum ActiveStatus {
        ACTIVE("Active"), DEACTIVATED("Deactivated");

        private String status;

        private ActiveStatus(String status) {
            this.status = status;
        }

        public String value() {
            return this.status;
        }
    }
    public enum UserRole {
        ROLE_EMPLOYEE("Employee"), ROLE_ADMIN("Admin"), ROLE_EMPLOYER("Employer");

        private String role;

        private UserRole(String role) {
            this.role = role;
        }

        public String value() {
            return this.role;
        }
    }
    public enum UserProfileUpdateType {
        UPDATE_DETAIL("UpdateDetail"),
        UPDATE_SKILLS("UpdateSkill"),
        UPDATE_WORK_EXPERIENCE("UpdateWorkExperience"),
        UPDATE_EDUCATION_DETAIL("UpdateEducation");

        private String type;

        private UserProfileUpdateType(String type) {
            this.type = type;
        }

        public String value() {
            return this.type;
        }
    }
}
