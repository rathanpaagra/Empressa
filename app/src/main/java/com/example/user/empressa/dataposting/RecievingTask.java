package com.example.user.empressa.dataposting;

import android.util.Log;

import com.example.user.empressa.adapter.Audition_ListAdapters;
import com.example.user.empressa.adapter.Audition_StatusListAdapter;
import com.example.user.empressa.values.DetailsValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 22-02-2018.
 */

public class RecievingTask {


    String Image_Url_Profile_Images = DataAPI.IMAGE_URL;

    public void LoginDetails(String result, DetailsValue details) {
        Log.d("debug", result);
        try {
            /*JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Response = jo.getString("message");*/
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Status = jo.getString("message");
                    if (Status.equals("Success")) {
                        //details= new DetailsValue();
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        String User_profile_Pic = jo.getString("profile_pic");
                        details.setProfileYesOrNo(User_profile_Pic);
                        String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);
                        String Audition_List_Id = jo.getString("audition_list_id");
                        details.setAudition_ID(Audition_List_Id);
                        String Audition_Role = jo.getString("audition_list_role");
                        details.setRole(Audition_Role);
                        String Audition_Description = jo.getString("audition_list_desc");
                        details.setAudition_ID(Audition_Description);
                        String Audition_City = jo.getString("audition_list_city");
                        details.setAudition_ID(Audition_City);
                        details.setLoginSuccess(true);

                    } else if (Status.equals("Failure")) {
                        details.setLognFailure(true);

                    } else if (Status.equals("No_Audition_List")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        String User_profile_Pic = jo.getString("profile_pic");
                        details.setProfileYesOrNo(User_profile_Pic);
                        String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);
                        details.setNoAuditionListFound(true);

                    } else if (Status.equals("Audition_Limit_Reached")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        String User_profile_Pic = jo.getString("profile_pic");
                        details.setProfileYesOrNo(User_profile_Pic);
                        details.setAudition_Limit_Reached(true);

                    }/*else if (Response.equals("Account Blocked")) {
                    details.setAccountblocked(true);
                } else {
                    details.setLoginExist(true);
                }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void RegisterDetails(String result, DetailsValue details) {
        try {
            /*JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Status = jo.getString("message");*/
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Status = jo.getString("message");
                    if (Status.equals("Success")) {
                        String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);
                        details.setSignUpSuccess(true);

                    } else if (Status.equals("Failure")) {
                        String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);
                        details.setSignUpFailure(true);

                    } else if (Status.equals("Email_Exists")) {
                        String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);
                        details.setEmailExist(true);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void RecieveUploadResult(String result, DetailsValue details) {
        try {
            /*JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Status = jo.getString("message");*/
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Status = jo.getString("message");
                    if (Status.equals("Success")) {
                    /*String EmailID = jo.getString("user_email");
                    details.setU_EmailID(EmailID);*/

                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);

                        String Audition_Applied_ID = jo.getString("audition_applied_id");
                        details.setAudition_Applied_ID(Audition_Applied_ID);
                        details.setVideoUploadSuccess(true);

                    } else if (Status.equals("Failure")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                    /*String EmailID = jo.getString("user_email");
                    details.setU_EmailID(EmailID);*/
                        details.setVideoUploadFailure(true);
                    }/* else if (Status.equals("Email_Exists")) {
                    String EmailID = jo.getString("user_email");
                    details.setU_EmailID(EmailID);
                    details.setEmailExist(true);
                }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void RecieveImageUploadResult(String result, DetailsValue details) {
        try {
            /*JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Status = jo.getString("message");*/
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Status = jo.getString("message");
                    if (Status.equals("Success")) {
                   /* String EmailID = jo.getString("user_email");
                    details.setU_EmailID(EmailID);*/

                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);

                        details.setImageUploadSuccess(true);

                    } else if (Status.equals("Failure")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                    /*String EmailID = jo.getString("user_email");
                    details.setU_EmailID(EmailID);*/
                        details.setVideoUploadFailure(true);
                    }/* else if (Status.equals("Email_Exists")) {
                    String EmailID = jo.getString("user_email");
                    details.setU_EmailID(EmailID);
                    details.setEmailExist(true);
                }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //recieving Roles Details
    public void ReciveRolesDetails(String result, DetailsValue details, ArrayList<DetailsValue> arrayList,
                                   Audition_ListAdapters adapters) {
        Log.d("debug", result);
        try {
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Response = jo.getString("message");
            /*JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Response = jo.getString("message");*/
                    if (Response.equals("Success")) {
                        //details= new DetailsValue();
                        Log.e(TAG, "Connect for fetching from server receving Roles Details");
                        details.setRoleSelectedSuccess(true);
                        details = new DetailsValue();

                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        /*String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);*/
                        String Audition_List_Id = jo.getString("audition_list_id");
                        details.setAudition_ID(Audition_List_Id);
                        String Audition_Role = jo.getString("audition_list_role");
                        details.setRole(Audition_Role);
                        String Audition_Description = jo.getString("audition_list_desc");
                        details.setDescription(Audition_Description);
                        String Audition_City = jo.getString("audition_list_city");
                        details.setLocation(Audition_City);
                        arrayList.add(details);
                        adapters.notifyDataSetChanged();

                    } else if (Response.equals("Failure")) {
                        details.setRoleSelectedFailure(true);

                    } else if (Response.equals("No_Audition_List")) {
                        details.setNoAuditionListFound(true);
                    } else if (Response.equals("Audition_Limit_Reached")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        /*String User_profile_Pic = jo.getString("profile_pic");
                        details.setProfileYesOrNo(User_profile_Pic);*/
                        details.setAudition_Limit_Reached(true);

                    }/* else {
                    details.setLoginExist(true);
                }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //receiving applied status details
    public void RecivingStatusDetails(String result, DetailsValue details, ArrayList<DetailsValue> arrayList,
                                      Audition_StatusListAdapter adapters) {
        Log.d("debug", result);
        try {
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Response = jo.getString("message");
            /*JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Response = jo.getString("message");*/
                    if (Response.equals("Success")) {
                        //details= new DetailsValue();
                        Log.e(TAG, "Connect for fetching from server receving Roles Details");
                        details.setStausReceivedSuccess(true);
                        details = new DetailsValue();
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        String Audition_Location = jo.getString("audition_location_address");
                        details.setStatus_udition_location_address(Audition_Location);
                        String Audition_Location_Time = jo.getString("audition_location_time");
                        details.setStatus_audition_location_time(Audition_Location_Time);
                        String Audition_Status = jo.getString("status");
                        details.setStatus_status(Audition_Status);
                        String Audition_List_Id = jo.getString("audition_list_id");
                        details.setStatus_audition_list_id(Audition_List_Id);
                        String Audition_Role = jo.getString("audition_list_role");
                        details.setStatus_audition_list_role(Audition_Role);
                        String Audition_Description = jo.getString("audition_list_desc");
                        details.setStatus_audition_list_desc(Audition_Description);
                        String Audition_City = jo.getString("audition_list_city");
                        details.setStatus_audition_list_city(Audition_City);
                        arrayList.add(details);
                        adapters.notifyDataSetChanged();

                    } else if (Response.equals("Failure")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        details.setStausReceivedFailure(true);

                    }/* else if (Response.equals("No_Audition_List")) {
                        details.setNoAuditionListFound(true);
                    } else if (Response.equals("Audition_Limit_Reached")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        *//*String User_profile_Pic = jo.getString("profile_pic");
                        details.setProfileYesOrNo(User_profile_Pic);*//*
                        details.setAudition_Limit_Reached(true);

                    }*//* else {
                    details.setLoginExist(true);
                }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //recieve Profile
    public void ProfileDetails(String result, DetailsValue details) {
        try {
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Status = jo.getString("message");
           /* JSONObject jo = new JSONObject(result);
            if (jo != null) {
                String Status = jo.getString("message");*/
                    if (Status.equals("Profile_Picture_Exist")) {

                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);

                        String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);
                        String user_dob = jo.getString("user_dob");
                        details.setU_DOB(user_dob);
                        String user_fathername = jo.getString("user_fathername");
                        details.setU_Fathername(user_fathername);
                        String user_father_ocupation = jo.getString("user_father_ocupation");
                        details.setU_FatherAccupation(user_father_ocupation);
                        String user_mobile = jo.getString("user_mobile");
                        details.setU_Phone(user_mobile);
                        String user_address = jo.getString("user_address");
                        details.setU_Address(user_address);
                        String user_city = jo.getString("user_city");
                        details.setU_City(user_city);
                        String user_state = jo.getString("user_state");
                        details.setU_State(user_state);
                        String user_country = jo.getString("user_country");
                        details.setU_Country(user_country);
                        String user_zipcode = jo.getString("user_zipcode");
                        details.setU_Zipcode(user_zipcode);
                        String user_level=jo.getString("level");
                        details.setU_Level(user_level);
                        String user_description=jo.getString("description");
                        details.setU_Description(user_description);
                        String user_Profile_pic = jo.getString("user_profile_pic");
                        String Profile_Photo = Image_Url_Profile_Images + user_Profile_pic;
                        details.setU_Profile_Pic(Profile_Photo);
                        String user_name = jo.getString("user_name");
                        details.setUsername(user_name);
                        details.setProfilePicExist(true);

                    } else if (Status.equals("No_Profile_Picture")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);
                        String user_dob = jo.getString("user_dob");
                        details.setU_DOB(user_dob);
                        String user_fathername = jo.getString("user_fathername");
                        details.setU_Fathername(user_fathername);
                        String user_father_ocupation = jo.getString("user_father_ocupation");
                        details.setU_FatherAccupation(user_father_ocupation);
                        String user_mobile = jo.getString("user_mobile");
                        details.setU_Phone(user_mobile);
                        String user_address = jo.getString("user_address");
                        details.setU_Address(user_address);
                        String user_city = jo.getString("user_city");
                        details.setU_City(user_city);
                        String user_state = jo.getString("user_state");
                        details.setU_State(user_state);
                        String user_country = jo.getString("user_country");
                        details.setU_Country(user_country);
                        String user_zipcode = jo.getString("user_zipcode");
                        details.setU_Zipcode(user_zipcode);
                        String user_level=jo.getString("level");
                        details.setU_Level(user_level);
                        String user_description=jo.getString("description");
                        details.setU_Description(user_description);
                        String user_Profile_pic = jo.getString("user_profile_pic");
                        details.setU_Profile_Pic(user_Profile_pic);
                        String user_name = jo.getString("user_name");
                        details.setUsername(user_name);
                        details.setProfilePicNotExist(true);
                    } else if (Status.equals("Failure")) {
                        String User_Id = jo.getString("user_id");
                        details.setU_ID(User_Id);
                        String EmailID = jo.getString("user_email");
                        details.setU_EmailID(EmailID);
                        details.setProfileRecievedFailure(true);
                    } /*else if (Status.equals("Email_Exists")) {
                    String EmailID = jo.getString("user_email");
                    details.setU_EmailID(EmailID);
                    details.setEmailExist(true);
                }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //RECEVING otp STATUS
    public void SMSExpiry(String result, DetailsValue details) {

        try {
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo != null) {
                    String Status = jo.getString("message");
                    if (Status.equals("Success")) {
                        details.setSMSOTPSuccces(true);
                    } else if (Status.equals("Failure")) {
                        details.setSMSOTPFailure(true);
                    } else if (Status.equals("Invalid_Mobile_Number")) {
                        details.setSMSOTP_Invalid_mobile_number(true);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
