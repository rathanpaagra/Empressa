package com.example.user.empressa.uploadVideo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.user.empressa.MainActivity;
import com.example.user.empressa.R;
import com.example.user.empressa.dataposting.ConnectingTask;
import com.example.user.empressa.dataposting.RecievingTask;
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

public class PicFromGalleryActivity extends Activity {

    // LogCat tag
    private static final String TAG = PicFromGalleryActivity.class.getSimpleName();

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int REQUEST_PERMISSION = 1;


    private Uri fileUri; // file url to store image/video

    private Button btnCapturePicture, btnRecordVideo, btnUpload;
    boolean doubleBackToExitPressedOnce = false;
    static ProgressDialog dialog = null;
    private static final int SELECT_VIDEO = 2;
    private String selectedPath=null;
    private long sizeInMb;
    private TextView textView;

    public String Email,User_ID , SharedUser_ID;
    private String Audition_List_id;
    FunctionCalls functionCalls;
    //pic gallery Layout
    LinearLayout picVideo;
    Button UploadVideo, ButtonHome;
    TextView textView_Audition_role,textView_Desc;


    //uploading Layout
    LinearLayout ll_uploading_status, ll_pic_status;
    LinearLayout ll_Applied_status;
    private TextView txtPercentage;
    private ImageView imgPreview;
    private ProgressBar progressBar;
    Button Ok,btnUploadcancel;
    long totalSize = 0;

    ConnectingTask connectingTask;
    DetailsValue detailsValue;
    RecievingTask recievingTask;
    Thread mythread;

    String Received_User_ID, User_Application_ID, Email_ID;
    TextView textview_ApplicationID, textview_Email_ID, textView_Role, textView_City;

    String AuditionID,Role,Description,City;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_from_gallery);

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            dialog.dismiss();
            return;
        }*/

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        Email_ID=settings.getString("Email_id","");
        SharedUser_ID=settings.getString("User_ID","");


        connectingTask = new ConnectingTask();
        detailsValue = new DetailsValue();
        recievingTask = new RecievingTask();
        functionCalls = new FunctionCalls();

        Bundle extras = getIntent().getExtras();
        Email = extras.getString("Email");
        User_ID = extras.getString("User_ID");
        AuditionID = extras.getString("AuditionId");
        Role = extras.getString("Role");
        Description = extras.getString("Description");
        City = extras.getString("City");

       //Toast.makeText(getApplicationContext(), "Email ID: " + Email, Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "Role: " + Role, Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "Description: " + Description, Toast.LENGTH_LONG).show();

        // Changing action bar background color
        // These two lines are not needed
        // getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));

        // btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        //pic gallery Layout
        //btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
        //btnUpload = (Button) findViewById(R.id.btnUploadVideo);

        textView = (TextView) findViewById(R.id.textView);
        picVideo = findViewById(R.id.ll_UploadVideo);
        UploadVideo = findViewById(R.id.btnUploadVideo);
        ButtonHome=findViewById(R.id.btn_home);
        textView_Audition_role=findViewById(R.id.textView_audion_role);
        textView_Desc=findViewById(R.id.textView_Description);
        textView_Audition_role.setText(Role);
        textView_Desc.setText(Description);


        //Upload Gallery Layout
        ll_pic_status = findViewById(R.id.ll_pic_status);
        ll_uploading_status = findViewById(R.id.ll_uploading_status);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Ok = (Button) findViewById(R.id.Ok);
        btnUploadcancel=findViewById(R.id.btnUploadcancel);

        ll_Applied_status = findViewById(R.id.ll_Applied_status);
        textview_Email_ID = findViewById(R.id.txt_Email_ID);
        textview_ApplicationID = findViewById(R.id.txt_Application_ID);
        textView_Role=findViewById(R.id.textView_Role);

        textView_City=findViewById(R.id.txt_City);


        picVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // record video
                //recordVideo();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(PicFromGalleryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PicFromGalleryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    //dialog.dismiss();
                    return;
                }
                chooseVideo();
            }
        });

        UploadVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (functionCalls.isInternetOn(PicFromGalleryActivity.this)) {
                    if (sizeInMb <= 200) {
                        if (selectedPath != null && !selectedPath.isEmpty()) {
                            ll_pic_status.setVisibility(View.INVISIBLE);
                            ll_uploading_status.setVisibility(View.VISIBLE);
                            new UploadFileToServer().execute();
                            //ConnectingTask.UploadFileToServer uploadFileToServer= connectingTask.new UploadFileToServer(Email,selectedPath,Audition_List_id,detailsValue, PicFromGalleryActivity.this,progressBar);
                            //uploadFileToServer.execute();
                            mythread = null;
                            Runnable runnable = new PicFromGalleryActivity.UploadTimer();
                            mythread = new Thread(runnable);
                            mythread.start();
                        } else {
                            Toast.makeText(PicFromGalleryActivity.this, "Please choose the Video from the gallery", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(PicFromGalleryActivity.this, "Please select video file below 200 MB", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PicFromGalleryActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
                }
                // record video
                //recordVideo();
                //launchUploadActivity2();
            }
        });

        Ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PicFromGalleryActivity.this, MainActivity.class);
                intent.putExtra("User_ID", Received_User_ID);
                intent.putExtra("Application_ID", User_Application_ID);
                startActivity(intent);
                PicFromGalleryActivity.this.finish();
            }
        });

        ButtonHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PicFromGalleryActivity.this, MainActivity.class);
                intent.putExtra("User_ID", User_ID);
                //Toast.makeText(PicFromGalleryActivity.this, "Email: "+User_Email_ID , Toast.LENGTH_SHORT).show();
                //intent.putExtra("Application_ID", User_Application_ID);
                startActivity(intent);
                PicFromGalleryActivity.this.finish();
            }
        });

        btnUploadcancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PicFromGalleryActivity.this, MainActivity.class);
                intent.putExtra("User_ID", SharedUser_ID);
                //intent.putExtra("Application_ID", User_Application_ID);
                startActivity(intent);
                PicFromGalleryActivity.this.finish();
            }
        });


       /* //  Record video button click event
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // record video
                //recordVideo();
                chooseVideo();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // record video
                //recordVideo();
                launchUploadActivity2();
            }
        });*/
    }


    public void onBackPressed() {
        Toast.makeText(this, "Press Home to go back! ", Toast.LENGTH_SHORT).show();
        //super.onBackPressed();
    }


    private void launchUploadActivity2() {

        Intent i = new Intent(PicFromGalleryActivity.this, VideoUploadActivity.class);
        i.putExtra("filePath", selectedPath);
        i.putExtra("email", Email);
        Log.i("Path of saved image.", selectedPath);
        System.err.print("Path of saved image." + selectedPath);
        startActivity(i);

    }

    private void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                try
                {
                    //File Object
                    File objFile=new File(selectedPath);

                    long sizeInBytes = objFile.length();
                    //transform in MB
                    sizeInMb = sizeInBytes / (1024 * 1024);
                    //getting filesizein megabytes
                    System.out.println("File size is: " + sizeInMb + " MB.");

                    //getting file path
                    System.out.println("File path is: " + objFile.getAbsolutePath());
                    //getting filesize
                    System.out.println("File size is: " + objFile.length() + " bytes.");
                }
                catch (Exception Ex)
                {
                    System.out.println("Exception: "+ Ex.toString());
                }
               /* try{
                    long length = selectedPath.length();
                    length = length/1024;
                    System.out.println("File Path : " +selectedPath + ", File size : " + length +" KB");
                    Toast.makeText(PicFromGalleryActivity.this, " File size : " + length +" KB", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    System.out.println("File not found : " + e.getMessage() + e);
                }*/
                textView.setText(selectedPath);
            }
        }
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

   /* public String getPath(Uri uri) {
        // can post image
        String [] proj={MediaStore.Video.Media.DATA};
        Cursor cursor = managedQuery( uri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }*/

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Video.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    class UploadTimer implements Runnable {

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
                    if (detailsValue.isVideoUploadSuccess()) {
                        detailsValue.setVideoUploadSuccess(false);
                        mythread.interrupt();
                        progressBar.setVisibility(View.GONE);
                        /*User_Email_ID = detailsValue.getU_EmailID();
                        textview_Email_ID.setText(User_Email_ID);*/
                        Received_User_ID=detailsValue.getU_ID();

                        textview_Email_ID.setText(Email_ID);
                        //functionCalls.LogStatus("ID: " + User_Email_ID);
                        User_Application_ID = detailsValue.getAudition_Applied_ID();
                        textview_ApplicationID.setText(User_Application_ID);
                        textView_Role.setText(Role);
                        textView_City.setText(City);

                        //functionCalls.LogStatus("ID: " + User_Application_ID);
                        ll_uploading_status.setVisibility(View.INVISIBLE);
                        ll_Applied_status.setVisibility(View.VISIBLE);
                        // functionCalls.showToast(LoginActivity.this, "ID: " + User_ID);
                        Toast.makeText(PicFromGalleryActivity.this, "Video Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        /*String User_Name = details.getUserName();
                        functionCalls.LogStatus("SecurityID: " + User_Name);
                        editor.putString("UserID", User_ID);
                        editor.putString("UserName", User_Name);
                        editor.commit();*/
                        /*Intent intent = new Intent(PicFromGalleryActivity.this, MainActivity.class);
                        intent.putExtra("Email_id",User_ID);
                        startActivity(intent);
                        finish();*/
                    }
                    if (detailsValue.isVideoUploadFailure()) {
                        detailsValue.setVideoUploadFailure(false);
                        mythread.interrupt();
                        //dialog.dismiss();
                        Toast.makeText(PicFromGalleryActivity.this, "Oops, Soory Couldn't Upload Video, Try it from empressafilms.com", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //Uploading to Server

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 99));
                    }
                });

                File sourceFile = new File(selectedPath);
                // Adding file data to http body
                entity.addPart("video_name", new FileBody(sourceFile));
                /*// Extra parameters if you want to pass to server
                entity.addPart("website",
						new StringBody("www.askdial.co.in"));*/
                entity.addPart("user_id", new StringBody(User_ID));

                entity.addPart("audition_list_id", new StringBody(AuditionID));
                if(totalSize == 100){
                    Toast.makeText(PicFromGalleryActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                }

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
            recievingTask.RecieveUploadResult(result, detailsValue);
        }
    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                       // Intent intent = new Intent(PicFromGalleryActivity.this, MainActivity.class);
                       // startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
