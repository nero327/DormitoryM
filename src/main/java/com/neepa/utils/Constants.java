package com.neepa.utils;

public interface Constants {

    enum UserType{
        STAFF("StaffRealm")
        ,STUDENT("StudentRealm");
        public String type;
        UserType(String type) {
            this.type=type;
        }
    };
    enum RepairStatus{
        Created(100,"工单已创建"),
        MChecked(101,"维修人员已确认工单"),
        Cancelled(104,"工单已取消"),
        Handled(200,"工单问题已处理"),
        SChecked(201,"学生已确认工单"),
        SReported(202,"学生申诉工单"),
        Finished(400,"工单已结束"),
        Withdrawn(404,"工单已撤回");
        public int statusCode;
        public String description;

        RepairStatus(int statusCode,String description) {
            this.statusCode =statusCode;
            this.description=description;
        }
        public int getStatusCode(){
            return this.statusCode;
        }
        public String getDescription(){
            return this.description;
        }
    };

    String StaffNameSession="loginStaffName";
    String StudentNameSession="loginStudentName";

    String StaffIdSession = "loginStaffId";
    String StudentIdSession = "loginStudentId";

    String StaffRole ="staffRole";
    String StudentRole ="studentRole";
}
