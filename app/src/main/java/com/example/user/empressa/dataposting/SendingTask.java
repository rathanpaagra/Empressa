package com.example.user.empressa.dataposting;

import android.util.EventLogTags;

import com.example.user.empressa.values.FunctionCalls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by User on 22-02-2018.
 */

public class SendingTask {
    String BASE_URL = DataAPI.BASE_URL;
    FunctionCalls functionCalls = new FunctionCalls();

    public String PostLogin(String Username, String Password) {
        String responsestr = "";
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_email", Username);
        datamap.put("user_password", Password);
        try {
            responsestr = UrlPostConnection("Checklogin", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }

    public String VisitorLogin(String Name, String FatherName, String FatherOccupation, String Email, String Mobile, String Address, String City, String State, String Country, String Zipcode, String DOB, String Password, String Level, String Description) {

        String responsestr = "";
        // HashMap<String, String> datamap = new HashMap<>();
        HashMap<String, String> datamap = new HashMap<>();
        try {
            datamap.put("user_name", Name);
            datamap.put("user_fathername", FatherName);
            datamap.put("user_father_ocupation", FatherOccupation);
            datamap.put("user_email", Email);
            datamap.put("user_mobile", Mobile);
            datamap.put("user_address", Address);
            datamap.put("user_city", City);
            datamap.put("user_state", State);
            datamap.put("user_country", Country);
            datamap.put("user_zipcode", Zipcode);
            datamap.put("user_dob", DOB);
            datamap.put("user_password", Password);
            //3rd Phase
            datamap.put("level", Level);
            datamap.put("description", Description);

            responsestr = UrlPostConnection("Signup", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }


    private String UrlPostConnection(String Post_Url, HashMap<String, String> datamap) throws IOException {
        String response = "";
        functionCalls.LogStatus("Connecting URL: " + BASE_URL + Post_Url);
        URL url = new URL(BASE_URL + Post_Url);
        functionCalls.LogStatus("URL Connection 1");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        functionCalls.LogStatus("URL Connection 2");
        conn.setReadTimeout(15000);
        functionCalls.LogStatus("URL Connection 3");
        conn.setConnectTimeout(15000);
        functionCalls.LogStatus("URL Connection 4");
        conn.setRequestMethod("POST");
        functionCalls.LogStatus("URL Connection 5");
        conn.setDoInput(true);
        functionCalls.LogStatus("URL Connection 6");
        conn.setDoOutput(true);
        functionCalls.LogStatus("URL Connection 7");

        OutputStream os = conn.getOutputStream();
        functionCalls.LogStatus("URL Connection 8");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        functionCalls.LogStatus("URL Connection 9");
        writer.write(getPostDataString(datamap));
        functionCalls.LogStatus("URL Connection 10");
        writer.flush();
        functionCalls.LogStatus("URL Connection 11");
        writer.close();
        functionCalls.LogStatus("URL Connection 12");
        os.close();
        functionCalls.LogStatus("URL Connection 13");
        int responseCode = conn.getResponseCode();
        functionCalls.LogStatus("URL Connection 14");
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            functionCalls.LogStatus("URL Connection 15");
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            functionCalls.LogStatus("URL Connection 16");
            while ((line = br.readLine()) != null) {
                response += line;
            }
            functionCalls.LogStatus("URL Connection 17");
        } else {
            response = "";
            functionCalls.LogStatus("URL Connection 18");
        }
        functionCalls.LogStatus("URL Connection Response: " + response);
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            functionCalls.LogStatus(result.toString());
        }

        return result.toString();
    }

    //sending Roles
    public String sendMyRoles(String Username) {
        String responsestr = "";
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_id", Username);
        //datamap.put("user_email", Username);
        try {
            responsestr = UrlPostConnection("Available_roles", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }

    //sending FOR STATUS
    public String sendMyStatus(String Username) {
        String responsestr = "";
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_id", Username);
        //datamap.put("user_id", "4");
        try {
            responsestr = UrlPostConnection("Applied_role_status", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }

//view profile
    public String SendProfile(String Username) {
        String responsestr = "";
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_id", Username);
        //datamap.put("user_email", Username);

        try {
            responsestr = UrlPostConnection("User_profile", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }


    public String SendUploadFile(String Username, String VideoFile, String Audition_List_id,String  Loaction) {
        String responsestr = "";
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_id", Username);
        //datamap.put("user_email", Email);
        datamap.put("video_name", VideoFile);
        datamap.put("audition_list_id", Audition_List_id);
        datamap.put("user_location", Loaction);


        try {
            responsestr = UrlPostConnection("Checklogin", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responsestr;
    }

    public String OTPGeneration(String Mobile, String OTP) {
        String response = "";
        String Mob= "+91"+Mobile;
        HashMap<String, String> datamap = new HashMap<>();
        datamap.put("user_mobile", Mob);
        datamap.put("mobile_otp", OTP);

        try {
            response = UrlPostConnection("Send_otp", datamap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
