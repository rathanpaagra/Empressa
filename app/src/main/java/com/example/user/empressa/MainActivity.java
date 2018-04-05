package com.example.user.empressa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.empressa.adapter.Audition_ListAdapters;
import com.example.user.empressa.dataposting.ConnectingTask;
import com.example.user.empressa.values.DetailsValue;
import com.example.user.empressa.values.FunctionCalls;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final int REQUEST_PERMISSION = 1;
    boolean doubleBackToExitPressedOnce = false;
    String User_ID = "", AuditionID = "", Email_ID = "", Role = "", Description = "", City = "";
    String No_Audition_Found = "";
    static ProgressDialog dialog = null;
    Thread mythread, mythread2;
    ConnectingTask task;
    DetailsValue detailsvalue;
    FunctionCalls functionCalls;
    String user_ID = "", user_Name = "";
    RecyclerView recyler_audition_Details;
    ArrayList<DetailsValue> arrayRolesList;
    Audition_ListAdapters audition_list_Adapters;
    RecyclerView.LayoutManager layoutManager;
    LinearLayout ll_recyclerview, ll_err_message, ll_error_exceed;
    ImageView imageview_Profile;

    //Profile Check
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        No_Audition_Found = extras.getString("No_Audition");
        User_ID = extras.getString("User_ID");
        Email_ID = extras.getString("Email_id");
        AuditionID = extras.getString("Audition_ID");
        Role = extras.getString("Role");
        Description = extras.getString("Description");
        City = extras.getString("City");


        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        User_ID = settings.getString("User_ID", "");

        detailsvalue = new DetailsValue();
        task = new ConnectingTask();
        functionCalls = new FunctionCalls();
        arrayRolesList = new ArrayList<DetailsValue>();

        //initialisation
        ll_recyclerview = findViewById(R.id.ll_recycler_view);
        ll_err_message = findViewById(R.id.ll_error);
        ll_error_exceed = findViewById(R.id.ll_error_exceed);
        imageview_Profile = findViewById(R.id.imageview_Profile);


        //region RecyclerView with Adapter
        recyler_audition_Details = (RecyclerView) findViewById(R.id.recyclerView_audition_list);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        audition_list_Adapters = new Audition_ListAdapters(MainActivity.this, arrayRolesList, MainActivity.this, User_ID/*,AuditionID,Role,Description,City*/);
        recyler_audition_Details.setHasFixedSize(true);
        recyler_audition_Details.setLayoutManager(layoutManager);
        recyler_audition_Details.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyler_audition_Details.setAdapter(audition_list_Adapters);

        //endregion
        ConnectingTask.MyRoles checkAppointments = task.new MyRoles(arrayRolesList, audition_list_Adapters, detailsvalue, MainActivity.this, User_ID);
        checkAppointments.execute();
        dialog = ProgressDialog.show(MainActivity.this, "", "Searching for Roles...", true);
        dialog.setCancelable(true);
        mythread = null;
        Runnable runnable = new MyRolesTimer();
        mythread = new Thread(runnable);
        mythread.start();

        imageview_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.icon_logo);
        builder.setMessage("Do you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        System.exit(0);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @SuppressLint("MissingSuperCall")
    public void onDestroy() {

    }


    public void click() {

        if (functionCalls.isInternetOn(MainActivity.this)) {
            ConnectingTask.Viewrofile login = task.new Viewrofile(User_ID, detailsvalue);
            login.execute();
           /* dialog = ProgressDialog.show(LoginActivity.this, "", "Logging In please wait..", true);
            dialog.setCancelable(true);*/
            mythread2 = null;
            Runnable runnable = new MainActivity.ViewProfile();
            mythread2 = new Thread(runnable);
            mythread2.start();
        } else {
            Toast.makeText(MainActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
        }
    }

    class MyRolesTimer implements Runnable {

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
                    if (detailsvalue.isRoleSelectedSuccess()) {
                        detailsvalue.setRoleSelectedSuccess(false);
                        dialog.dismiss();
                        mythread.interrupt();
                    }
                    if (detailsvalue.isRoleSelectedFailure()) {
                        detailsvalue.setRoleSelectedFailure(false);
                        dialog.dismiss();
                        mythread.interrupt();
                    }

                    if (detailsvalue.isNoAuditionListFound()) {
                        detailsvalue.setNoAuditionListFound(false);
                        dialog.dismiss();
                        ll_recyclerview.setVisibility(View.GONE);
                        ll_err_message.setVisibility(View.VISIBLE);
                        mythread.interrupt();
                    }
                    if (detailsvalue.isAudition_Limit_Reached()) {
                        detailsvalue.setAudition_Limit_Reached(false);
                        dialog.dismiss();
                        ll_recyclerview.setVisibility(View.GONE);
                        ll_error_exceed.setVisibility(View.VISIBLE);
                        mythread.interrupt();
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
                    if (detailsvalue.isProfilePicExist()) {
                        detailsvalue.setProfilePicExist(false);
                        dialog.dismiss();

                        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                        intent.putExtra("User_ID", detailsvalue.getU_ID());
                        intent.putExtra("Email_id", detailsvalue.getU_EmailID());
                        intent.putExtra("Name", detailsvalue.getUsername());
                        intent.putExtra("Fname", detailsvalue.getU_Fathername());
                        intent.putExtra("Foccupation", detailsvalue.getU_FatherAccupation());
                        intent.putExtra("Mobile", detailsvalue.getU_Phone());
                        intent.putExtra("Address", detailsvalue.getU_Address());
                        intent.putExtra("City", detailsvalue.getU_City());
                        intent.putExtra("State", detailsvalue.getU_State());
                        intent.putExtra("Country", detailsvalue.getU_Country());
                        intent.putExtra("Profile_Pic", detailsvalue.getU_Profile_Pic());
                        intent.putExtra("Zipcode", detailsvalue.getU_Zipcode());
                        intent.putExtra("Dob", detailsvalue.getU_DOB());
                        intent.putExtra("Level", detailsvalue.getU_Level());
                        intent.putExtra("v", detailsvalue.getU_Description());
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "Please select ur Role", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    } else if (detailsvalue.isProfilePicNotExist()) {
                        detailsvalue.setProfilePicNotExist(false);
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                        intent.putExtra("User_ID", detailsvalue.getU_ID());
                        intent.putExtra("Email_id", detailsvalue.getU_EmailID());
                        intent.putExtra("Name", detailsvalue.getUsername());
                        intent.putExtra("Fname", detailsvalue.getU_Fathername());
                        intent.putExtra("Foccupation", detailsvalue.getU_FatherAccupation());
                        intent.putExtra("Mobile", detailsvalue.getU_Phone());
                        intent.putExtra("Address", detailsvalue.getU_Address());
                        intent.putExtra("City", detailsvalue.getU_City());
                        intent.putExtra("State", detailsvalue.getU_State());
                        intent.putExtra("Country", detailsvalue.getU_Country());
                        intent.putExtra("Profile_Pic", detailsvalue.getU_Profile_Pic());
                        intent.putExtra("Zipcode", detailsvalue.getU_Zipcode());
                        intent.putExtra("Dob", detailsvalue.getU_DOB());
                        intent.putExtra("Level", detailsvalue.getU_Level());
                        intent.putExtra("U_Description", detailsvalue.getU_Description());
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "Roles not available", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    } else if (detailsvalue.isProfileRecievedFailure()) {
                        detailsvalue.setProfileRecievedFailure(false);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Profile not available", Toast.LENGTH_SHORT).show();

                        mythread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
