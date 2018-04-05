package com.example.user.empressa;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.empressa.OTP.OTPMobileActivity;
import com.example.user.empressa.dataposting.ConnectingTask;

import com.example.user.empressa.values.DetailsValue;
import com.example.user.empressa.values.FunctionCalls;

public class LoginActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    //Permission
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    EditText user_email, user_password;
    TextView textView_signin, textView_signup;

    FunctionCalls functionCalls;
    DetailsValue details;
    ConnectingTask task;
    Thread mythread;
    static ProgressDialog dialog = null;

    String Email = null, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        editor.apply();

        functionCalls = new FunctionCalls();
        task = new ConnectingTask();
        details = new DetailsValue();

        /*Bundle extras = getIntent().getExtras();
        Email = extras.getString("Email_id");*/
        user_email = (EditText) findViewById(R.id.edt_user_email);
        user_password = (EditText) findViewById(R.id.edt_user_password);
        textView_signin = (TextView) findViewById(R.id.txt_sign_in);
        textView_signup = (TextView) findViewById(R.id.txt_sign_up);

        textView_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (functionCalls.isInternetOn(LoginActivity.this)) {
                    if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("Need Storage Permission");
                            builder.setMessage("This app needs storage permission.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                                    Logindetails();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                            //Previously Permission Request was cancelled with 'Dont Ask Again',
                            // Redirect to Settings after showing Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("Need Storage Permission");
                            builder.setMessage("This app needs storage permission.");
                            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    sentToSettings = true;
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    //Logindetails();

                                }
                            });
                            builder.show();
                        } else {
                            //just request the permission
                            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                        }


                        SharedPreferences.Editor editor = permissionStatus.edit();
                        editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
                        editor.commit();


                    } else {
                        Logindetails();
                        //You already have the permission, just go ahead.
                        proceedAfterPermission();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (functionCalls.isInternetOn(LoginActivity.this)) {
                    Intent intent = new Intent(LoginActivity.this, OTPMobileActivity.class);
                    startActivity(intent);
                    ;
                } else {
                    Toast.makeText(LoginActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void proceedAfterPermission() {

        Logindetails();
        //We've got the permission, now we can proceed further
        // Toast.makeText(getBaseContext(), "We got the Storage Permission", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.icon_logo);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                       /* Intent intent=new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);*/
                        //System.exit(0);
                        // finish();
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();


                            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    public void Logindetails() {
        if (functionCalls.isInternetOn(LoginActivity.this)) {
            Email = user_email.getText().toString();
            //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            if (!Email.equals("") && Email.matches(EMAIL_PATTERN)) {
                Password = user_password.getText().toString();
                if (!Password.equals("") /*&& Password.length() >= 6*/) {
                    ConnectingTask.LoginData login = task.new LoginData(Email, Password, details);
                    login.execute();
                    //dialog = ProgressDialog.show(LoginActivity.this, "", "Logging In please wait..", true);
                    //dialog.setCancelable(true);
                    mythread = null;
                    Runnable runnable = new LoginTimer();
                    mythread = new Thread(runnable);
                    mythread.start();
                } else {
                    user_password.setError("Please Enter Correct Password");
                }
            } else {
                user_email.setError("Please Enter Email/Mobile");
            }
        } else {
            Toast.makeText(LoginActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
        }

    }

    class LoginTimer implements Runnable {

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
                    if (details.isLoginSuccess()) {
                        details.setLoginSuccess(false);
                        //dialog.dismiss();
                        mythread.interrupt();
                        Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                        String User_EmailID = details.getU_EmailID();
                        functionCalls.LogStatus("ID: " + User_EmailID);
                        editor.putString("Email_id", User_EmailID);
                        editor.commit();
                        String User_ID = details.getU_ID();
                        functionCalls.LogStatus("ID: " + User_ID);
                        editor.putString("User_ID", User_ID);
                        editor.commit();
                        String ProfileExistOrNot = details.getProfileYesOrNo();
                        functionCalls.LogStatus("ID: " + ProfileExistOrNot);
                        if (ProfileExistOrNot.equals("Yes")) {
                            //doNothing
                        } else if (ProfileExistOrNot.equals("No")) {
                            Toast.makeText(LoginActivity.this, "Please upload your profile pic in profile section", Toast.LENGTH_SHORT).show();
                        }
                        // functionCalls.showToast(LoginActivity.this, "ID: " + User_ID);
                        /*String User_Name = details.getUserName();
                        functionCalls.LogStatus("SecurityID: " + User_Name);
                        editor.putString("UserID", User_ID);
                        editor.putString("UserName", User_Name);
                        editor.commit();*/
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("User_ID", User_ID);
                        intent.putExtra("Email_id", User_EmailID);
                        intent.putExtra("Role", details.getRole());
                        intent.putExtra("City", details.getU_City());
                        intent.putExtra("Audition_ID", details.getAudition_ID());
                        intent.putExtra("Description", details.getDescription());
                        startActivity(intent);
                        finish();
                    }
                    if (details.isLognFailure()) {
                        details.setLognFailure(false);
                        //dialog.dismiss();
                        mythread.interrupt();
                        Toast.makeText(LoginActivity.this, "Login / Password incorrect", Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }
                    if (details.isNoAuditionListFound()) {
                        details.setNoAuditionListFound(false);
                        //dialog.dismiss();
                        mythread.interrupt();
                        String User_ID = details.getU_ID();
                        functionCalls.LogStatus("ID: " + User_ID);
                        String User_EmailID = details.getU_EmailID();
                        functionCalls.LogStatus("ID: " + User_EmailID);
                        Toast.makeText(LoginActivity.this, "No Auditions Found", Toast.LENGTH_SHORT).show();
                        editor.putString("Email_id", User_EmailID);
                        editor.putString("User_ID", User_ID);
                        editor.commit();
                        String ProfileExistOrNot = details.getProfileYesOrNo();
                        functionCalls.LogStatus("ID: " + ProfileExistOrNot);
                        if (ProfileExistOrNot.equals("Yes")) {
                            //doNothing
                        } else if (ProfileExistOrNot.equals("No")) {
                            Toast.makeText(LoginActivity.this, "Please upload your profile pic in profile section", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("User_ID", User_ID);
                        intent.putExtra("Email_id", User_EmailID);
                        intent.putExtra("No_Audition", "No_Audition_Found");
                        startActivity(intent);
                        finish();

                        //dialog.dismiss();
                    }

                    if (details.isAudition_Limit_Reached()) {
                        details.setAudition_Limit_Reached(false);
                        mythread.interrupt();
                        String User_ID = details.getU_ID();
                        functionCalls.LogStatus("ID: " + User_ID);
                        String User_EmailID = details.getU_EmailID();
                        functionCalls.LogStatus("ID: " + User_EmailID);
                        editor.putString("Email_id", User_EmailID);
                        editor.putString("User_ID", User_ID);
                        editor.commit();
                        Toast.makeText(LoginActivity.this, " Audition Limit Reached", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("User_ID", User_ID);
                       // intent.putExtra("Email_id", User_EmailID);
                        //intent.putExtra("No_Audition", "No_Audition_Found");
                        startActivity(intent);
                        finish();
                        //dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
