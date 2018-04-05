package com.example.user.empressa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.empressa.adapter.Audition_StatusListAdapter;
import com.example.user.empressa.dataposting.ConnectingTask;
import com.example.user.empressa.values.DetailsValue;
import com.example.user.empressa.values.FunctionCalls;

import java.util.ArrayList;

public class UserStatusActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String User_ID = "";
    LinearLayout ll_status_recycler_view,ll_status_error;
    RecyclerView recyclerView_status_list;
    ImageView imageview_status_Profile;
    static ProgressDialog dialog = null;
    Thread mythread,mythread2;
    ConnectingTask task;
    DetailsValue detailsvalue;
    FunctionCalls functionCalls;
    ArrayList<DetailsValue> arrayStatusList;
    Audition_StatusListAdapter audition_status_list_Adapters;
    RecyclerView.LayoutManager layoutManager;
    Button btn_status_home_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        /*Bundle extras = getIntent().getExtras();
        User_ID = extras.getString("User_ID");*/

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        User_ID=settings.getString("User_ID","");
        detailsvalue = new DetailsValue();
        task = new ConnectingTask();
        functionCalls = new FunctionCalls();
        arrayStatusList = new ArrayList<DetailsValue>();


        //initialisation
        ll_status_recycler_view = findViewById(R.id.ll_status_recycler_view);
        ll_status_error = findViewById(R.id.ll_status_error);
        imageview_status_Profile = findViewById(R.id.imageview_status_Profile);
        recyclerView_status_list = (RecyclerView)findViewById(R.id.recyclerView_status_list);
        btn_status_home_profile=findViewById(R.id.btn_status_home_profile);


        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        audition_status_list_Adapters = new Audition_StatusListAdapter(UserStatusActivity.this, arrayStatusList, UserStatusActivity.this, User_ID/*,AuditionID,Role,Description,City*/);
        recyclerView_status_list.setHasFixedSize(true);
        recyclerView_status_list.setLayoutManager(layoutManager);
        recyclerView_status_list.setLayoutManager(new LinearLayoutManager(UserStatusActivity.this));
        recyclerView_status_list.setAdapter(audition_status_list_Adapters);

        //endregion
        ConnectingTask.MyStatus checkAppointments = task.new MyStatus(arrayStatusList, audition_status_list_Adapters, detailsvalue, UserStatusActivity.this, User_ID);
        checkAppointments.execute();
        dialog = ProgressDialog.show(UserStatusActivity.this, "", "Searching for Status...", true);
        dialog.setCancelable(true);
        mythread = null;
        Runnable runnable = new MyStatusTimer();
        mythread = new Thread(runnable);
        mythread.start();

        imageview_status_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });
        btn_status_home_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserStatusActivity.this, MainActivity.class);
                intent.putExtra("User_ID", User_ID);
                //Toast.makeText(UserProfileActivity.this, "Email: "+stEmail , Toast.LENGTH_SHORT).show();
                //intent.putExtra("Application_ID", User_Application_ID);
                startActivity(intent);
                finish();
            }
        });

    }

    public void click() {

        if (functionCalls.isInternetOn(UserStatusActivity.this)) {
            ConnectingTask.Viewrofile login = task.new Viewrofile(User_ID, detailsvalue);
            login.execute();
                  /*  dialog = ProgressDialog.show(LoginActivity.this, "", "Logging In please wait..", true);
                    dialog.setCancelable(true);*/
            mythread2 = null;
            Runnable runnable = new UserStatusActivity.ViewProfile();
            mythread2 = new Thread(runnable);
            mythread2.start();
        } else {
            Toast.makeText(UserStatusActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
        }
    }

    class MyStatusTimer implements Runnable {

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
                    if (detailsvalue.isStausReceivedSuccess()) {
                        detailsvalue.setStausReceivedSuccess(false);
                        dialog.dismiss();
                        //Toast.makeText(MainActivity.this, "Please select ur Role", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                    if (detailsvalue.isStausReceivedFailure()) {
                        detailsvalue.setStausReceivedFailure(false);
                        ll_status_recycler_view.setVisibility(View.INVISIBLE);
                        ll_status_error.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        //Toast.makeText(MainActivity.this, "Roles not available", Toast.LENGTH_SHORT).show();
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

                        Intent intent = new Intent(UserStatusActivity.this, UserProfileActivity.class);
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
                        intent.putExtra("Profile_Pic",detailsvalue.getU_Profile_Pic());
                        intent.putExtra("Zipcode", detailsvalue.getU_Zipcode());
                        intent.putExtra("Dob", detailsvalue.getU_DOB());
                        intent.putExtra("Level", detailsvalue.getU_Level());
                        intent.putExtra("U_Description", detailsvalue.getU_Description());
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "Please select ur Role", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }else if (detailsvalue.isProfilePicNotExist()) {
                        detailsvalue.setProfilePicNotExist(false);
                        dialog.dismiss();
                        Intent intent = new Intent(UserStatusActivity.this, UserProfileActivity.class);
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
                        intent.putExtra("Profile_Pic",detailsvalue.getU_Profile_Pic());
                        intent.putExtra("Zipcode", detailsvalue.getU_Zipcode());
                        intent.putExtra("Dob", detailsvalue.getU_DOB());
                        intent.putExtra("Level", detailsvalue.getU_Level());
                        intent.putExtra("U_Description", detailsvalue.getU_Description());
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this, "Roles not available", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    } else if(detailsvalue.isProfileRecievedFailure()) {
                        detailsvalue.setProfileRecievedFailure(false);
                        dialog.dismiss();
                        Toast.makeText(UserStatusActivity.this, "Profile not available", Toast.LENGTH_SHORT).show();

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
}
