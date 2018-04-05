package com.example.user.empressa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.empressa.dataposting.ConnectingTask;
import com.example.user.empressa.values.DetailsValue;
import com.example.user.empressa.values.FunctionCalls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

public class RegisterActivity extends Activity {

    String Name, FName, FatherOccupation, EmailId, Phone, Address, City, State, Country, Zipcode, DOB, Password, Confirm_Password;
    EditText editText_Name, editText_FName, editText_FatherOccupation, editText_EmailId, editText_Phone, editText_Address, editText_City, editText_State, editText_Country, editText_Zipcode,
            editText_DOB, editText_Password, editText_Confirm_Password;
    Button button_Submit, button_DOB;
    TextView textView_user_phone;
    TextView txtV_date_display;
    private int mYear, mDay, mMonth;
    FunctionCalls functionCalls;
    DetailsValue details;
    ConnectingTask task;
    Thread mythread;
    static ProgressDialog dialog = null;

    //3rdphase
    //adding extra twofields
    String[] levels;
    ArrayAdapter<String> arrayAdapter;
    Spinner spinnerLevels;
    String str_select_exp;
    String c_exp_ID="";
    EditText editText_description;
    String Description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle extras = getIntent().getExtras();
        //Phone = extras.getString("Mobile");

        //Phone = extras.getString("Mobile");
        Phone="9964609394";

        functionCalls = new FunctionCalls();
        task = new ConnectingTask();
        details = new DetailsValue();
        editText_Name = findViewById(R.id.edt_user_name);
        editText_FName = findViewById(R.id.edt_user_father_name);
        editText_FatherOccupation = findViewById(R.id.edt_user_father_occupation);
        editText_EmailId = findViewById(R.id.edt_user_emaill);
        //editText_Phone = findViewById(R.id.edt_user_phone);
        editText_Address = findViewById(R.id.edt_user_address);
        editText_City = findViewById(R.id.edt_user_city);
        editText_State = findViewById(R.id.edt_user_state);
        editText_Country = findViewById(R.id.edt_user_country);
        editText_Zipcode = findViewById(R.id.edt_user_zipcode);
        editText_Password = findViewById(R.id.edt_user_password);
        editText_Confirm_Password = findViewById(R.id.edt_user_confirm_password);
        txtV_date_display = findViewById(R.id.txt_date);
        button_Submit = findViewById(R.id.txt_submit);
        button_DOB = findViewById(R.id.button_date);
        textView_user_phone = findViewById(R.id.textView_user_phone);
        editText_description = findViewById(R.id.edt_desc);

        spinnerLevels = findViewById(R.id.spinner_level);
        levels = getResources().getStringArray(R.array.levels);

        textView_user_phone.setText(Phone);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, levels);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLevels.setAdapter(arrayAdapter);

        spinnerLevels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c_exp_ID = "";
                str_select_exp = spinnerLevels.getSelectedItem().toString();
                if (str_select_exp.equals("Select Packages")) {
                    c_exp_ID = "0";
                    // Toast.makeText(Add_Coustmer.this, "your Plan: "+c_plan_ID, Toast.LENGTH_SHORT).show();
                } else if (str_select_exp.equals("Fresher")) {
                    c_exp_ID = "1";
                    Toast.makeText(RegisterActivity.this, "You are Fresher", Toast.LENGTH_SHORT).show();
                } else if (str_select_exp.equals("Experienced")) {
                    c_exp_ID = "2";
                    Toast.makeText(RegisterActivity.this, "You are Experienced", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // Toast.makeText(RegisterActivity.this, "Please select the experience level", Toast.LENGTH_SHORT).show();
            }
        });


        button_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Dialog dialog = null;
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, -3);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dp = new DatePickerDialog(RegisterActivity.this, dpd, year, month, date);
                dp.getDatePicker().setMaxDate(cal.getTimeInMillis());
                dialog = dp;
                dialog.show();

                /*Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        int i_month=1+ month;
                        String strMonth = (String.valueOf(i_month));
                        String strDay = String.valueOf(day);
                        String strYear = String.valueOf(year);

                        if (month < 10) {
                            strMonth = "0" + strMonth ;
                        }

                        if (day < 10) {
                            strDay = "0" + strDay;
                        }

                        // Handle the data here
                        txtV_date_display.setText(strDay + " - " + strMonth + " - " + strYear);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();*/
            }
        });


        button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (functionCalls.isInternetOn(RegisterActivity.this)) {
                    coustomerDetails();
                } else {
                    Toast.makeText(RegisterActivity.this, "Please Turn On Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void coustomerDetails() {
        Name = editText_Name.getText().toString().trim();
        if (!editText_Name.getText().toString().equals("")) {
            FName = editText_FName.getText().toString().trim();
            if (!editText_FName.getText().toString().equals("")) {
                FatherOccupation = editText_FatherOccupation.getText().toString().trim();
                if (!editText_FatherOccupation.getText().toString().equals("")) {
                    EmailId = editText_EmailId.getText().toString();
                    //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    if (!editText_EmailId.getText().toString().equals("") && EmailId.matches(EMAIL_PATTERN)) {
                        /*Phone = editText_Phone.getText().toString();
                        if (!editText_Phone.getText().toString().equals("") && Phone.length() >= 10) {*/
                        Address = editText_Address.getText().toString().trim();
                        if (!editText_Address.getText().toString().equals("") && Address.length() > 5) {
                            City = editText_City.getText().toString().trim();
                            if (!editText_City.getText().toString().equals("")) {
                                State = editText_State.getText().toString().trim();
                                if (!editText_State.getText().toString().equals("")) {
                                    Country = editText_Country.getText().toString().trim();
                                    if (!editText_Country.getText().toString().equals("")) {
                                        Zipcode = editText_Zipcode.getText().toString();
                                        if (!editText_Zipcode.getText().toString().equals("")) {
                                            DOB = txtV_date_display.getText().toString();
                                            if (!txtV_date_display.getText().toString().equals("")) {
                                               // System.out.print(c_exp_ID);
                                                //Toast.makeText(RegisterActivity.this, "cid"+c_exp_ID, Toast.LENGTH_SHORT).show();
                                                if (c_exp_ID.equals("1") || c_exp_ID.equals("2")) {
                                               // if ((c_exp_ID != "1") || (c_exp_ID != "2")){
                                                    Description = editText_description.getText().toString();
                                                    if (!editText_description.getText().toString().equals("")) {
                                                        Password = editText_Password.getText().toString();
                                                        if (!Password.equals("") && Password.length() >= 6) {
                                                            Confirm_Password = editText_Confirm_Password.getText().toString();
                                                            if (Password.equals(Confirm_Password)) {
                                                                // if (c_plan_ID.equals("0")) {
                                                                // if (((c_plan_ID != "1") || (c_plan_ID != "2") || (c_plan_ID != "3"))
                                                                ConnectingTask.VisitorDetails login = task.new VisitorDetails(Name, FName, FatherOccupation, EmailId, Phone, Address, City, State, Country, Zipcode, DOB, Password, c_exp_ID, Description, details);
                                                                login.execute();
                                                                dialog = ProgressDialog.show(RegisterActivity.this, "", "Registering please wait..", true);
                                                                dialog.setCancelable(true);
                                                                mythread = null;
                                                                Runnable runnable = new RegisterTimer();
                                                                mythread = new Thread(runnable);
                                                                mythread.start();
                                                            } else {
                                                                Toast.makeText(RegisterActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(RegisterActivity.this, "Please enter Password atleast 6 characters", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(RegisterActivity.this, "How good you are ? Describe in sentence", Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Please select the experience level", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Please select the DOB", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Enter the Correct Zip Code", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Enter the Country", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Enter the State", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Enter the City", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Enter the Correct Address", Toast.LENGTH_SHORT).show();
                        }
                        /*} else {
                            Toast.makeText(RegisterActivity.this, "Enter the Currect Mobile No", Toast.LENGTH_SHORT).show();
                        }*/
                    } else {
                        Toast.makeText(RegisterActivity.this, "Enter the Correct Email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Enter Father Occupation", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Enter the Father Name", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Enter the Name", Toast.LENGTH_SHORT).show();
        }
    }

    class RegisterTimer implements Runnable {

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
                    if (details.isSignUpSuccess()) {
                        details.setSignUpSuccess(false);
                        dialog.dismiss();
                        String Email_ID = details.getU_EmailID();
                        functionCalls.LogStatus("ID: " + Email_ID);
                        //Date_Added=detailsvalue.getDate_added();
                       /* Intent intent=new Intent();
                        intent.putExtra("Email_id",Email_ID);
                        setResult(2,intent);
                        finish();*/
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        // intent.putExtra("date_added", Date_Added);
                        //intent.putExtra("Email_id",Email_ID);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterActivity.this, "Succefully Registered", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                    if (details.isSignUpFailure()) {
                        details.setSignUpFailure(false);
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Registeration failure", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                    if (details.isEmailExist()) {
                        details.setEmailExist(false);
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                        mythread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date Starttime = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Starttime = new SimpleDateFormat("dd/MM/yyyy").parse(("" + dayOfMonth + "/" + "" + (monthOfYear + 1) + "/" + "" + year));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateselected = sdf.format(Starttime);
            Log.d("debug", dateselected);
            txtV_date_display.setText(dateselected);
        }
    };

}
