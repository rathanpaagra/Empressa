package com.example.user.empressa;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.user.empressa.dataposting.ConnectingTask;
import com.example.user.empressa.dataposting.RecievingTask;
import com.example.user.empressa.uploadVideo.AndroidMultiPartEntity;
import com.example.user.empressa.uploadVideo.Config;
import com.example.user.empressa.uploadVideo.PicFromGalleryActivity;
import com.example.user.empressa.values.CustomVolleyRequest;
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
import java.io.InterruptedIOException;

import static android.content.ContentValues.TAG;

public class UserProfileActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private static final int SELECT_IMAGE = 1;
    public static final int REQUEST_PERMISSION = 2;
    private String selectedPath=null;
    static ProgressDialog dialog = null;
    TextView tName,tFname,tFoccupation,tEmail,tPhone,tAddress,tCity,tstate,tCountry,tZipcode,tDOB,tLevel,tDescription;

    private  String stUID,stName,stFname,stFoccupation,stEmail,stPhone,stAddress,stCity,ststate,stCountry,stZipcode,stDOB,stProfilePic, stLevel,stU_Description;
    Button btn_home_profile,btn_profile,btn_image_upload;
    ImageView profile_pic,CIV_UploadImage;
    LinearLayout ll_upload_image,ll_image_uploading_status,ll_image_uploaded;
    LinearLayout ll_user_profile,ll_submit;

    private ProgressBar image_progressBar;
    long totalSize = 0;
    TextView txt_image_Percentage;

    ConnectingTask task;
    DetailsValue detailsvalue;
    RecievingTask recievingTask;
    FunctionCalls functionCalls;
    Thread mythread,mythread2;

    //Success Images response
    String Upload_Succes_User_ID;
    Button btn_Home, btn_Status;

    // Image Fetching
    ImageLoader imageLoader;
    NetworkImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Bundle extras = getIntent().getExtras();
        stUID = extras.getString("User_ID");
        stEmail = extras.getString("Email_id");
        stName = extras.getString("Name");
        stFname = extras.getString("Fname");
        stFoccupation = extras.getString("Foccupation");
        stPhone = extras.getString("Mobile");
        stCity = extras.getString("City");
        ststate = extras.getString("State");
        stCountry = extras.getString("Country");
        stZipcode = extras.getString("Zipcode");
        stDOB = extras.getString("Dob");
        stAddress = extras.getString("Address");
        stProfilePic=extras.getString("Profile_Pic");
        stLevel=extras.getString("Level");
        stU_Description=extras.getString("U_Description");

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        Upload_Succes_User_ID=settings.getString("User_ID","");

        task=new ConnectingTask();
        detailsvalue = new DetailsValue();
        recievingTask= new RecievingTask();
        functionCalls= new FunctionCalls();

        //image Initialisation
        imageView = (NetworkImageView) findViewById(R.id.profile_pic2);
        imageLoader = CustomVolleyRequest.getInstance(UserProfileActivity.this).getImageLoader();

        tName=findViewById(R.id.tv_name);
        tFname=findViewById(R.id.tv_fname);
        tFoccupation=findViewById(R.id.tv_foccupation);
        tEmail=findViewById(R.id.tv_email);
        tPhone=findViewById(R.id.tv_mobile);
        tAddress=findViewById(R.id.tv_address);
        tCity=findViewById(R.id.tv_city);
        tstate=findViewById(R.id.tv_state);
        tCountry=findViewById(R.id.tv_country);
        tZipcode=findViewById(R.id.tv_zipcode);
        tDOB=findViewById(R.id.tv_DOB);
        tLevel=findViewById(R.id.tv_level);
        tDescription=findViewById(R.id.tv_Description);

        imageLoader.get(stProfilePic, ImageLoader.getImageListener(imageView, R.drawable.profile2,
                R.drawable.profile2));
        imageView.setImageUrl(stProfilePic, imageLoader);

        //Linearlayout
        ll_upload_image=findViewById(R.id.ll_upload_image);
        ll_image_uploading_status=findViewById(R.id.ll_image_uploading_status);
        ll_image_uploaded=findViewById(R.id.ll_image_uploaded);
        ll_user_profile=findViewById(R.id.ll_user_profile);
        ll_submit=findViewById(R.id.ll_submit);

        //progressbar
        txt_image_Percentage = (TextView) findViewById(R.id.txt_image_Percentage);
        image_progressBar = (ProgressBar) findViewById(R.id.image_progressBar);

        btn_home_profile=findViewById(R.id.btn_home_profile);

        btn_profile=findViewById(R.id.btn_profile);
        btn_Home=findViewById(R.id.btn_Home);
        btn_Status=findViewById(R.id.btn_application_status);
        btn_image_upload=findViewById(R.id.btn_image_upload);
        profile_pic=findViewById(R.id.profile_pic);
        CIV_UploadImage=findViewById(R.id.CIV_UploadImage);


        tName.setText(stName);
        tFname.setText(stFname);
        tFoccupation.setText(stFoccupation);
        tEmail.setText(stEmail);
        tPhone.setText(stPhone);
        tAddress.setText(stAddress);
        tCity.setText(stCity);
        tstate.setText(ststate);
        tCountry.setText(stCountry);
        tZipcode.setText(stZipcode);
        tDOB.setText(stDOB);
        tLevel.setText(stLevel);
        tDescription.setText(stU_Description);

        btn_home_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                intent.putExtra("User_ID", Upload_Succes_User_ID);
                //Toast.makeText(UserProfileActivity.this, "Email: "+stEmail , Toast.LENGTH_SHORT).show();
                //intent.putExtra("Application_ID", User_Application_ID);
                startActivity(intent);
                finish();
            }
        });

        CIV_UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(UserProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    //dialog.dismiss();
                    return;
                }
                chooseImage();
            }
        });

        btn_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (functionCalls.isInternetOn(UserProfileActivity.this)) {
                    ll_upload_image.setVisibility(View.INVISIBLE);
                    new UploadImageFileToServer().execute();
                    //ll_user_profile.setVisibility(View.INVISIBLE);
                    // ll_submit.setVisibility(View.INVISIBLE);
                    ll_image_uploading_status.setVisibility(View.VISIBLE);
                    mythread = null;
                    Runnable runnable = new UserProfileActivity.UploadImageTimer();
                    mythread = new Thread(runnable);
                    mythread.start();
                } else {
                    Toast.makeText(UserProfileActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (functionCalls.isInternetOn(UserProfileActivity.this)) {
                    ConnectingTask.Viewrofile login = task.new Viewrofile(Upload_Succes_User_Email_ID, detailsvalue);
                    login.execute();
                  *//*  dialog = ProgressDialog.show(LoginActivity.this, "", "Logging In please wait..", true);
                    dialog.setCancelable(true);*//*
                    mythread2 = null;
                    Runnable runnable = new UserProfileActivity.ViewProfile();
                    mythread2 = new Thread(runnable);
                    mythread2.start();
                } else {
                    Toast.makeText(UserProfileActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        btn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                intent.putExtra("User_ID", Upload_Succes_User_ID);
                //Toast.makeText(PicFromGalleryActivity.this, "Email: "+User_Email_ID , Toast.LENGTH_SHORT).show();
                //intent.putExtra("Application_ID", User_Application_ID);
                startActivity(intent);
                finish();
            }
        });

        btn_Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserStatusActivity.class);
                //intent.putExtra("User_ID", Upload_Succes_User_ID);
                //Toast.makeText(PicFromGalleryActivity.this, "Email: "+User_Email_ID , Toast.LENGTH_SHORT).show();
                //intent.putExtra("Application_ID", User_Application_ID);
                startActivity(intent);
                finish();
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Image "), SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                Toast.makeText(getApplicationContext(), "Image Path: " + selectedPath, Toast.LENGTH_LONG).show();
                ll_upload_image.setVisibility(View.VISIBLE);
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
            } else {
                // User refused to grant permission.
            }
        }
    }

    class UploadImageTimer implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (detailsvalue.isImageUploadSuccess()) {
                        detailsvalue.setImageUploadSuccess(false);
                        mythread.interrupt();
                        ll_image_uploading_status.setVisibility(View.GONE);
                        //ll_user_profile.setVisibility(View.VISIBLE);
                        //ll_submit.setVisibility(View.VISIBLE);
                        //ll_image_uploaded.setVisibility(View.VISIBLE);
                        Upload_Succes_User_ID = detailsvalue.getU_ID();

                        Toast.makeText(UserProfileActivity.this, "Profile Picture Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                    if (detailsvalue.isImageUploadFailure()) {
                        detailsvalue.setImageUploadFailure(false);
                        mythread.interrupt();
                        //dialog.dismiss();
                        Toast.makeText(UserProfileActivity.this, "Image Not Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    class ViewProfile implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork2();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doWork2() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (detailsvalue.isProfileRecievedSuccess()) {
                        detailsvalue.setProfileRecievedSuccess(false);
                        dialog.dismiss();
                        Intent intent = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                        intent.putExtra("Email_id", detailsvalue.getU_EmailID());
                        intent.putExtra("Name", detailsvalue.getUsername());
                        intent.putExtra("Fname", detailsvalue.getU_Fathername());
                        intent.putExtra("Foccupation", detailsvalue.getU_FatherAccupation());
                        intent.putExtra("Mobile", detailsvalue.getU_Phone());
                        intent.putExtra("Address", detailsvalue.getU_Address());
                        intent.putExtra("City", detailsvalue.getU_City());
                        intent.putExtra("State", detailsvalue.getU_State());
                        intent.putExtra("Country", detailsvalue.getU_Country());
                        intent.putExtra("Zipcode", detailsvalue.getU_Zipcode());
                        intent.putExtra("Dob", detailsvalue.getU_DOB());
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "Please select ur Role", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                    if (detailsvalue.isProfileRecievedFailure()) {
                        detailsvalue.setProfileRecievedFailure(false);
                        dialog.dismiss();
                        //Toast.makeText(MainActivity.this, "Roles not available", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }

                   /* if (detailsvalue.isNoAuditionListFound()) {
                        detailsvalue.setNoAuditionListFound(false);
                        dialog.dismiss();
                        ll_recyclerview.setVisibility(View.GONE);
                        ll_err_message.setVisibility(View.VISIBLE);
                        //Toast.makeText(MainActivity.this, "No Audition Found", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class UploadImageFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            image_progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            image_progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            image_progressBar.setProgress(progress[0]);

            // updating percentage value
            txt_image_Percentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.IMAGE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });

                File sourceFile = new File(selectedPath);
                // Adding file data to http body
                entity.addPart("profile_picture", new FileBody(sourceFile));
                entity.addPart("user_id",new StringBody(stUID));
                /*// Extra parameters if you want to pass to server
                entity.addPart("website",
						new StringBody("www.askdial.co.in"));*/
                //entity.addPart("user_email", new StringBody(stUID));


                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
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
            // showing the server response in an alert dialog
            //showAlert(result);
            // super.onPostExecute(result);
            recievingTask.RecieveImageUploadResult(result, detailsvalue);
        }
    }
}
