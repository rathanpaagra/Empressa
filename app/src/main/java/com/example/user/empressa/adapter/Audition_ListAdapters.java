package com.example.user.empressa.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.empressa.MainActivity;
import com.example.user.empressa.R;
import com.example.user.empressa.uploadVideo.PicFromGalleryActivity;
import com.example.user.empressa.values.DetailsValue;
import com.example.user.empressa.values.FunctionCalls;

import java.util.ArrayList;

/**
 * Created by User on 01-03-2018.
 */

public class Audition_ListAdapters extends RecyclerView.Adapter<Audition_ListAdapters.CustomerViewHolder> {

    private static final int REQUEST_FOR_ACTIVITY_CODE = 1;
    ArrayList<DetailsValue> arrayList = new ArrayList<>();
    MainActivity mainActivity;
    String ContextView,Email_ID,AuditionID,Role,Description,City;
    DetailsValue details;
    Context context;
    Activity activity;
    FunctionCalls functionCalls = new FunctionCalls();


    public Audition_ListAdapters(MainActivity mainActivity, ArrayList<DetailsValue> arrayList, Context context,String Email_ID/*,String AuditionID,String Role,String Description,String City*/) {
        this.mainActivity = mainActivity;
        this.arrayList = arrayList;
        this.context=context;
        this.Email_ID = Email_ID;
        activity = (Activity)context;
        /*this.AuditionID = AuditionID;
        this.Role = Role;
        this.Description = Description;
        this.City = City;*/
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audition_cardview, parent, false);
        CustomerViewHolder viewHolder = new CustomerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        details = arrayList.get(position);
        String role=details.getRole().trim();
        String desc=details.getDescription().trim();
        String city=details.getLocation().trim();
        holder.tv_role.setText(role);
        holder.tv_desc.setText(desc);
        holder.tvcity.setText(city);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_role,tv_desc,tvcity;
        LinearLayout ll_apply;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_role = (TextView) itemView.findViewById(R.id.txt_Role);
            tv_desc = (TextView) itemView.findViewById(R.id.txt_Description);
            tvcity = (TextView) itemView.findViewById(R.id.txt_city);
            ll_apply=itemView.findViewById(R.id.ll_Apply);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            DetailsValue details = arrayList.get(pos);
            Intent intent = null;
            intent = new Intent(context, PicFromGalleryActivity.class);
            intent.putExtra("Role", details.getRole());
            intent.putExtra("User_ID", details.getU_ID());
            intent.putExtra("Email", details.getU_EmailID());
            intent.putExtra("City", details.getLocation());
            intent.putExtra("AuditionId", details.getAudition_ID());
            intent.putExtra("Description", details.getDescription());
           // arrayList.clear();
            ((Activity) context).startActivity(intent);
            /*int pos = getAdapterPosition();
            DetailsValue details = arrayList.get(pos);
            Intent intent = null;
            intent = new Intent(context, Appointment_Details.class);
            intent.putExtra("NAME", details.getVisitors_Name());
            intent.putExtra("MOBILE", details.getVisitors_Mobile());
            intent.putExtra("EMAIL", details.getVisitors_Email());
            intent.putExtra("TOMEET", details.getVisitors_tomeet());
            intent.putExtra("DATE", functionCalls.ConvertApointmentDate(details.getAppointmentDate()));
            intent.putExtra("TIME", details.getAppointmentTime());
            intent.putExtra("PURPOSE", details.getPurpose());
            intent.putExtra("LOCATION",details.getAppointmentLocation());
            intent.putExtra("FROM",details.getAppointmentFrom());
            ((Activity) context).startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);*/
        }
    }
}
