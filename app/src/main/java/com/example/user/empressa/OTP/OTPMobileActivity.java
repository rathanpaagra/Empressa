package com.example.user.empressa.OTP;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.empressa.LoginActivity;
import com.example.user.empressa.MainActivity;
import com.example.user.empressa.R;
import com.example.user.empressa.dataposting.ConnectingTask;
import com.example.user.empressa.values.DetailsValue;
import com.example.user.empressa.values.FunctionCalls;

import java.util.Random;

public class OTPMobileActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    EditText mobile;
    Button SubmitforOTP;
    TextView signIn;

    FunctionCalls functionCalls;
    DetailsValue details;
    ConnectingTask task;
    Thread mythread;

    String str_mobile, randomOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpmobile);

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        editor.apply();

        functionCalls = new FunctionCalls();
        task = new ConnectingTask();
        details = new DetailsValue();

        mobile = findViewById(R.id.edt_text_user_phone);
        SubmitforOTP = findViewById(R.id.btn_submit_for_otp);
        signIn = findViewById(R.id.txt_sign_in);

        SubmitforOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mobiledetails();

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPMobileActivity.this, LoginActivity.class);
                //intent.putExtra("Mobile", Mobile_Number);
                startActivity(intent);
            }
        });


    }

    public void Mobiledetails() {
        if (functionCalls.isInternetOn(OTPMobileActivity.this)) {

            str_mobile = mobile.getText().toString();
            if (!mobile.getText().toString().equals("") && mobile.length() >= 10) {
                Random rand = new Random();
                int num = rand.nextInt(900000) + 100000;
                randomOTP = String.valueOf(num);
                //editor.putString("OTP", ""+num+" ");
                //editor.putString("Mobile_No_OTP", str_mobile);
                editor.commit();
                //ConnectingTask.SMSOTP login = task.new SMSOTP(settings.getString("Mobile_No_OTP", ""),settings.getString("OTP", ""), details);
                ConnectingTask.SMSOTP login = task.new SMSOTP(str_mobile, randomOTP, details);
                login.execute();
                mythread = null;
                Runnable runnable = new OTPTimer();
                mythread = new Thread(runnable);
                mythread.start();

            } else {
                mobile.setError("Please Enter Mobile");
            }
        } else {
            Toast.makeText(OTPMobileActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
    class OTPTimer implements Runnable {

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
                    if (details.isSMSOTPSuccces()) {
                        details.setSMSOTPSuccces(false);
                        mythread.interrupt();
                        Intent intent = new Intent(OTPMobileActivity.this, OTPConfirmActivity.class);
                        intent.putExtra("Mobile_No_OTP", str_mobile);
                        intent.putExtra("OTP", randomOTP);
                        startActivity(intent);
                        finish();
                    }
                    if (details.isSMSOTPFailure()) {
                        details.setSMSOTPFailure(false);
                        mythread.interrupt();
                        Toast.makeText(OTPMobileActivity.this, "SMS OTP Couldn't send", Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }

                    if (details.isSMSOTP_Invalid_mobile_number()) {
                        details.setSMSOTP_Invalid_mobile_number(false);
                        mythread.interrupt();
                        Toast.makeText(OTPMobileActivity.this, "inavalid Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
