package com.example.user.empressa.OTP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.empressa.R;
import com.example.user.empressa.RegisterActivity;
import com.example.user.empressa.dataposting.ConnectingTask;
import com.example.user.empressa.values.FunctionCalls;

import java.util.Random;

public class OTPConfirmActivity extends AppCompatActivity {

    EditText OTP;
    Button ConfirmOTP;
    TextView ClickHere, textView_Mobile;
    public String Mobile_Number, Random_OTP;
    FunctionCalls functionCalls;

    String str_OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpconfirm);

        functionCalls = new FunctionCalls();
        Bundle extras = getIntent().getExtras();
        Mobile_Number = extras.getString("Mobile_No_OTP");
        Random_OTP = extras.getString("OTP");

        OTP = findViewById(R.id.edt_text_otp);
        ConfirmOTP = findViewById(R.id.btn_submit_for_confirm_otp);
        ClickHere = findViewById(R.id.txt_Click_here);
        textView_Mobile = findViewById(R.id.textView_Mobile);

        textView_Mobile.setText(Mobile_Number);

        ConfirmOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (functionCalls.isInternetOn(OTPConfirmActivity.this)) {
                    ConfirmOTPDetails();
                } else {
                    Toast.makeText(OTPConfirmActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPConfirmActivity.this, OTPMobileActivity.class);
                //intent.putExtra("Mobile", Mobile_Number);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void ConfirmOTPDetails() {
        if (functionCalls.isInternetOn(OTPConfirmActivity.this)) {

            str_OTP = OTP.getText().toString();
            if (!OTP.getText().toString().equals("") && OTP.length() >= 6) {

                if (str_OTP.equals(Random_OTP)) {
                    Intent intent = new Intent(OTPConfirmActivity.this, RegisterActivity.class);
                    intent.putExtra("Mobile", Mobile_Number);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Entered OTP is Invalid", Toast.LENGTH_SHORT).show();
                }
            } else {
                OTP.setError("Enter OTP ");
            }
        } else {
            Toast.makeText(OTPConfirmActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
        }

    }
}
