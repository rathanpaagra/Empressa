package com.example.user.empressa.dataposting;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.user.empressa.adapter.Audition_ListAdapters;
import com.example.user.empressa.adapter.Audition_StatusListAdapter;
import com.example.user.empressa.uploadVideo.AndroidMultiPartEntity;
import com.example.user.empressa.uploadVideo.Config;
import com.example.user.empressa.values.DetailsValue;
import com.example.user.empressa.values.FunctionCalls;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.user.empressa.uploadVideo.Config.FILE_UPLOAD_URL;

/**
 * Created by User on 22-02-2018.
 */

public class ConnectingTask {
    SendingTask sendingTask = new SendingTask();
    RecievingTask recievingTask = new RecievingTask();

    FunctionCalls functionCalls = new FunctionCalls();

    long totalSize = 0;
    private ProgressBar progressBar;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, Context context, final View mProgressBar) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBar.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public class LoginData extends AsyncTask<String, String, String> {
        String result = "", Username, Password, Organization;
        DetailsValue details;
        //Double longitude, latitude;

        public LoginData(String username, String password, DetailsValue detail) {
            this.Username = username;
            this.Password = password;
            this.details = detail;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = sendingTask.PostLogin(Username, Password);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", "Result: " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            recievingTask.LoginDetails(result, details);
        }
    }


    public class VisitorDetails extends AsyncTask<String, String, String> {
        String SName, SFatherName, SFatherOccupation, SEmail, SMobile, SAddress, SCity, SState, SCountry, SZipcode, SDOB, SPassword,Description,Level;
        String results = "";
        String level_name;
        DetailsValue detailsValue;

        public VisitorDetails(String SName, String SFatherName, String SFatherOccupation, String SEmail, String SMobile, String SAddress, String SCity, String SState, String SCountry, String SZipcode, String SDOB, String SPassword, String Level,String Description, DetailsValue detailsValue) {
            this.SName = SName;
            this.SFatherName = SFatherName;
            this.SFatherOccupation = SFatherOccupation;
            this.SEmail = SEmail;
            this.SMobile = SMobile;
            this.SAddress = SAddress;
            this.SCity = SCity;
            this.SState = SState;
            this.SCountry = SCountry;
            this.SZipcode = SZipcode;
            this.SDOB = SDOB;
            this.SPassword = SPassword;
            this.Description=Description;
            this.Level=Level;
            this.detailsValue = detailsValue;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                if(Level.equals("1")){
                    level_name="Fresher";
                }else if(Level.equals("2")){
                    level_name="Experienced";
                }
                results = sendingTask.VisitorLogin(SName, SFatherName, SFatherOccupation, SEmail, SMobile, SAddress, SCity, SState, SCountry, SZipcode, SDOB, SPassword,level_name, Description);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", "Result: " + results);
            return results;
        }

        @Override
        protected void onPostExecute(String results) {
            recievingTask.RegisterDetails(results, detailsValue);
        }
    }

    // Upload Video


    /**
     * Uploading the file to server
     */
    public class UploadFileToServer extends AsyncTask<String, Integer, String> {
        String Email, VideoFile, Audition_List_id, Loaction;
        String results = "";
        DetailsValue detailsValue;
        Context context;
        View Progressbar;

        public UploadFileToServer(String email, String videoFile, String audition_List_id,/* String loaction,*/ DetailsValue detailsValue, Context context, View progressbar) {
            Email = email;
            VideoFile = videoFile;
            Audition_List_id = audition_List_id;
            /*Loaction = loaction;*/
            this.detailsValue = detailsValue;
            Progressbar = progressbar;
            this.context = context;
        }

        /*@Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            //showProgress(true, context, Progressbar);
            super.onPreExecute();
        }*/

        @Override
        protected void onPreExecute() {
            showProgress(true, context, Progressbar);
            // super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
          /*  return uploadFile();*/
            try {
                results = /*sendingTask.*/SendUploadFile(Email, VideoFile, Audition_List_id/*, Loaction*/);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", "Result: " + results);
            return results;
        }


        @SuppressWarnings("deprecation")
        private String SendUploadFile(String Email, String VideoFile, String Audition_List_id/*, String Loaction*/) {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            functionCalls.LogStatus("Connecting URL: " + FILE_UPLOAD_URL + Email + VideoFile + Audition_List_id);
            functionCalls.LogStatus("URL Connection 1");
            HttpPost httppost = new HttpPost(FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });

                File sourceFile = new File(VideoFile);
                functionCalls.LogStatus("URL Connection 3 Sourcefile: " + sourceFile);
                // Adding file data to http body
                entity.addPart("video_name", new FileBody(sourceFile));
                /*// Extra parameters if you want to pass to server
                entity.addPart("website",
						new StringBody("www.askdial.co.in"));*/
                functionCalls.LogStatus("URL Connection 5 User Email: " + Email);
                entity.addPart("user_email", new StringBody(Email));
                functionCalls.LogStatus("URL Connection 5 User Audition_List_id: " + Audition_List_id);
                entity.addPart("audition_list_id", new StringBody(Audition_List_id));

                functionCalls.LogStatus("URL Connection 6");

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    functionCalls.LogStatus("Status Code URL: " + statusCode);
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {

            Log.e(TAG, "Response from server: " + result);
            showProgress(false, context, Progressbar);
            recievingTask.RecieveUploadResult(result, detailsValue);
           /* // showing the server response in an alert dialog
            showAlert(result);*/

            // super.onPostExecute(result);
        }

    }

    public class MyRoles extends AsyncTask<String, String, String> {
        ArrayList<DetailsValue> arrayList;
        Audition_ListAdapters adapters;
        DetailsValue detailsValue;
        Context context;
        String Email_ID, result = "";

        public MyRoles(ArrayList<DetailsValue> arrayList, Audition_ListAdapters adapters, DetailsValue detailsValue, Context context,
                       String user_ID) {
            this.arrayList = arrayList;
            this.adapters = adapters;
            this.detailsValue = detailsValue;
            Email_ID = user_ID;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = sendingTask.sendMyRoles(Email_ID);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            recievingTask.ReciveRolesDetails(result, detailsValue, arrayList, adapters);
        }
    }

    public class MyStatus extends AsyncTask<String, String, String> {
        ArrayList<DetailsValue> arrayList;
        Audition_StatusListAdapter adapters;
        DetailsValue detailsValue;
        Context context;
        String Email_ID, result = "";

        public MyStatus(ArrayList<DetailsValue> arrayList, Audition_StatusListAdapter adapters, DetailsValue detailsValue, Context context,
                        String user_ID) {
            this.arrayList = arrayList;
            this.adapters = adapters;
            this.detailsValue = detailsValue;
            Email_ID = user_ID;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = sendingTask.sendMyStatus(Email_ID);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", result);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            recievingTask.RecivingStatusDetails(result, detailsValue, arrayList, adapters);
        }
    }


    public class Viewrofile extends AsyncTask<String, String, String> {
        String result = "", Emailid, Organization;
        DetailsValue details;
        //Double longitude, latitude;

        public Viewrofile(String username, DetailsValue details) {
            this.Emailid = username;
            this.details = details;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                result = sendingTask.SendProfile(Emailid);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("debug", "Result: " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            recievingTask.ProfileDetails(result, details);
        }
    }


    public class SMSOTP extends AsyncTask<String, String, String> {
        String Mobile, OTP, result = "";
        DetailsValue detailsValue;

        public SMSOTP(String mobile, String OTP, DetailsValue detailsvalue) {
            Mobile = mobile;
            this.OTP = OTP;
            this.detailsValue = detailsvalue;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                result = sendingTask.OTPGeneration(Mobile, OTP);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            recievingTask.SMSExpiry(result, detailsValue);
        }
    }
}
