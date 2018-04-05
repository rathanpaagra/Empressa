package com.example.user.empressa.values;

/**
 * Created by User on 22-02-2018.
 */

public class DetailsValue {

    String Username;
    String U_Fathername;
    String U_FatherAccupation;
    String U_EmailID;
    String U_ID;
    String U_Phone;
    String ProfileYesOrNo;

    String U_Address;
    String U_City;
    String U_State;
    String U_Country;
    String U_Zipcode;
    String U_DOB;
    String U_Profile_Pic;
    String U_Level;
    String U_Description;

    String Description;
    String Location;
    String Role;

    String Status_udition_location_address, Status_audition_location_time, Status_status, Status_audition_list_id, Status_audition_list_role, Status_audition_list_desc, Status_audition_list_city;
    public boolean StausReceivedSuccess;

    public boolean StausReceivedFailure;
    String Audition_ID;
    public boolean SMSOTPSuccces;
    public boolean SMSOTPFailure;
    public boolean SMSOTP_Invalid_mobile_number;

    public boolean isSMSOTPSuccces() {
        return SMSOTPSuccces;
    }

    public void setSMSOTPSuccces(boolean SMSOTPSuccces) {
        this.SMSOTPSuccces = SMSOTPSuccces;
    }

    public boolean isSMSOTPFailure() {
        return SMSOTPFailure;
    }

    public void setSMSOTPFailure(boolean SMSOTPFailure) {
        this.SMSOTPFailure = SMSOTPFailure;
    }

    public boolean isSMSOTP_Invalid_mobile_number() {
        return SMSOTP_Invalid_mobile_number;
    }

    public void setSMSOTP_Invalid_mobile_number(boolean SMSOTP_Invalid_mobile_number) {
        this.SMSOTP_Invalid_mobile_number = SMSOTP_Invalid_mobile_number;
    }

    public String getStatus_udition_location_address() {
        return Status_udition_location_address;
    }

    public void setStatus_udition_location_address(String status_udition_location_address) {
        Status_udition_location_address = status_udition_location_address;
    }

    public String getStatus_audition_location_time() {
        return Status_audition_location_time;
    }

    public void setStatus_audition_location_time(String status_audition_location_time) {
        Status_audition_location_time = status_audition_location_time;
    }

    public String getStatus_status() {
        return Status_status;
    }

    public void setStatus_status(String status_status) {
        Status_status = status_status;
    }

    public String getStatus_audition_list_id() {
        return Status_audition_list_id;
    }

    public void setStatus_audition_list_id(String status_audition_list_id) {
        Status_audition_list_id = status_audition_list_id;
    }

    public String getStatus_audition_list_role() {
        return Status_audition_list_role;
    }

    public void setStatus_audition_list_role(String status_audition_list_role) {
        Status_audition_list_role = status_audition_list_role;
    }

    public String getStatus_audition_list_desc() {
        return Status_audition_list_desc;
    }

    public void setStatus_audition_list_desc(String status_audition_list_desc) {
        Status_audition_list_desc = status_audition_list_desc;
    }

    public String getStatus_audition_list_city() {
        return Status_audition_list_city;
    }

    public void setStatus_audition_list_city(String status_audition_list_city) {
        Status_audition_list_city = status_audition_list_city;
    }

    public boolean isStausReceivedSuccess() {
        return StausReceivedSuccess;
    }

    public void setStausReceivedSuccess(boolean stausReceivedSuccess) {
        StausReceivedSuccess = stausReceivedSuccess;
    }

    public boolean isStausReceivedFailure() {
        return StausReceivedFailure;
    }

    public void setStausReceivedFailure(boolean stausReceivedFailure) {
        StausReceivedFailure = stausReceivedFailure;
    }

    public String getU_Profile_Pic() {
        return U_Profile_Pic;
    }

    public void setU_Profile_Pic(String u_Profile_Pic) {
        U_Profile_Pic = u_Profile_Pic;
    }


    private boolean LoginSuccess, LognFailure;
    private boolean Audition_Limit_Reached;

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public boolean isAudition_Limit_Reached() {
        return Audition_Limit_Reached;
    }

    public void setAudition_Limit_Reached(boolean audition_Limit_Reached) {
        Audition_Limit_Reached = audition_Limit_Reached;
    }

    private boolean SignUpSuccess, SignUpFailure;
    private boolean ProfileRecievedSuccess;


    private boolean ProfileRecievedFailure;

    private boolean ProfilePicExist;

    public boolean isProfilePicExist() {
        return ProfilePicExist;
    }

    public void setProfilePicExist(boolean profilePicExist) {
        ProfilePicExist = profilePicExist;
    }

    public boolean isProfilePicNotExist() {
        return ProfilePicNotExist;
    }

    public void setProfilePicNotExist(boolean profilePicNotExist) {
        ProfilePicNotExist = profilePicNotExist;
    }

    private boolean ProfilePicNotExist;
    private boolean VideoUploadSuccess;
    private boolean ImageUploadSuccess;

    public boolean isImageUploadSuccess() {
        return ImageUploadSuccess;
    }

    public void setImageUploadSuccess(boolean imageUploadSuccess) {
        ImageUploadSuccess = imageUploadSuccess;
    }

    public boolean isImageUploadFailure() {
        return ImageUploadFailure;
    }

    public void setImageUploadFailure(boolean imageUploadFailure) {
        ImageUploadFailure = imageUploadFailure;
    }

    private boolean ImageUploadFailure;

    private boolean RoleSelectedSuccess;
    private boolean NoAuditionListFound;

    public boolean isNoAuditionListFound() {
        return NoAuditionListFound;
    }

    public void setNoAuditionListFound(boolean noAuditionListFound) {
        NoAuditionListFound = noAuditionListFound;
    }

    public boolean isRoleSelectedSuccess() {
        return RoleSelectedSuccess;
    }

    public void setRoleSelectedSuccess(boolean roleSelectedSuccess) {
        RoleSelectedSuccess = roleSelectedSuccess;
    }

    public boolean isRoleSelectedFailure() {

        return RoleSelectedFailure;
    }

    public void setRoleSelectedFailure(boolean roleSelectedFailure) {
        RoleSelectedFailure = roleSelectedFailure;
    }

    public boolean isProfileRecievedSuccess() {
        return ProfileRecievedSuccess;
    }

    public void setProfileRecievedSuccess(boolean profileRecievedSuccess) {
        ProfileRecievedSuccess = profileRecievedSuccess;
    }

    public boolean isProfileRecievedFailure() {
        return ProfileRecievedFailure;
    }

    public void setProfileRecievedFailure(boolean profileRecievedFailure) {
        ProfileRecievedFailure = profileRecievedFailure;
    }

    private boolean RoleSelectedFailure;

    public String getAudition_ID() {
        return Audition_ID;
    }

    public void setAudition_ID(String audition_ID) {
        Audition_ID = audition_ID;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    String Audition_Applied_ID;

    public String getAudition_Applied_ID() {
        return Audition_Applied_ID;
    }

    public void setAudition_Applied_ID(String audition_Applied_ID) {
        Audition_Applied_ID = audition_Applied_ID;
    }


    public boolean isVideoUploadSuccess() {
        return VideoUploadSuccess;
    }

    public void setVideoUploadSuccess(boolean videoUploadSuccess) {
        VideoUploadSuccess = videoUploadSuccess;
    }

    public boolean isVideoUploadFailure() {
        return VideoUploadFailure;
    }

    public void setVideoUploadFailure(boolean videoUploadFailure) {
        VideoUploadFailure = videoUploadFailure;
    }

    private boolean VideoUploadFailure;


    public boolean isSignUpSuccess() {
        return SignUpSuccess;
    }

    public void setSignUpSuccess(boolean signUpSuccess) {
        SignUpSuccess = signUpSuccess;
    }

    public boolean isSignUpFailure() {
        return SignUpFailure;
    }

    public void setSignUpFailure(boolean signUpFailure) {
        SignUpFailure = signUpFailure;
    }

    private boolean EmailExist;

    public boolean isEmailExist() {
        return EmailExist;
    }

    public void setEmailExist(boolean emailExist) {
        EmailExist = emailExist;
    }


    public boolean isLoginSuccess() {
        return LoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        LoginSuccess = loginSuccess;
    }

    public boolean isLognFailure() {
        return LognFailure;
    }

    public void setLognFailure(boolean lognFailure) {
        LognFailure = lognFailure;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getU_Fathername() {
        return U_Fathername;
    }

    public void setU_Fathername(String u_Fathername) {
        U_Fathername = u_Fathername;
    }

    public String getU_FatherAccupation() {
        return U_FatherAccupation;
    }

    public void setU_FatherAccupation(String u_FatherAccupation) {
        U_FatherAccupation = u_FatherAccupation;
    }

    public String getU_EmailID() {
        return U_EmailID;
    }

    public void setU_EmailID(String u_EmailID) {
        U_EmailID = u_EmailID;
    }

    public String getProfileYesOrNo() {
        return ProfileYesOrNo;
    }

    public void setProfileYesOrNo(String profileYesOrNo) {
        ProfileYesOrNo = profileYesOrNo;
    }

    public String getU_Phone() {
        return U_Phone;
    }

    public void setU_Phone(String u_Phone) {
        U_Phone = u_Phone;
    }

    public String getU_Address() {
        return U_Address;
    }

    public void setU_Address(String u_Address) {
        U_Address = u_Address;
    }

    public String getU_City() {
        return U_City;
    }

    public void setU_City(String u_City) {
        U_City = u_City;
    }

    public String getU_State() {
        return U_State;
    }

    public void setU_State(String u_State) {
        U_State = u_State;
    }

    public String getU_Country() {
        return U_Country;
    }

    public void setU_Country(String u_Country) {
        U_Country = u_Country;
    }

    public String getU_Zipcode() {
        return U_Zipcode;
    }

    public void setU_Zipcode(String u_Zipcode) {
        U_Zipcode = u_Zipcode;
    }

    public String getU_DOB() {
        return U_DOB;
    }

    public void setU_DOB(String u_DOB) {
        U_DOB = u_DOB;
    }

    public String getU_Level() {
        return U_Level;
    }

    public void setU_Level(String u_Level) {
        U_Level = u_Level;
    }

    public String getU_Description() {
        return U_Description;
    }

    public void setU_Description(String u_Description) {
        U_Description = u_Description;
    }
}
