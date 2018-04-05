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
import com.example.user.empressa.UserStatusActivity;
import com.example.user.empressa.uploadVideo.PicFromGalleryActivity;
import com.example.user.empressa.values.DetailsValue;
import com.example.user.empressa.values.FunctionCalls;

import java.util.ArrayList;

/**
 * Created by User on 01-03-2018.
 */

public class Audition_StatusListAdapter extends RecyclerView.Adapter<Audition_StatusListAdapter.CustomerViewHolder> {

    private static final int REQUEST_FOR_ACTIVITY_CODE = 1;
    ArrayList<DetailsValue> arrayList = new ArrayList<>();
    UserStatusActivity mainActivity;
    String ContextView,Email_ID,AuditionID,Role,Description,City;
    DetailsValue details;
    Context context;
    Activity activity;
    FunctionCalls functionCalls = new FunctionCalls();


    public Audition_StatusListAdapter(UserStatusActivity mainActivity, ArrayList<DetailsValue> arrayList, Context context, String Email_ID/*,String AuditionID,String Role,String Description,String City*/) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_cardview, parent, false);
        CustomerViewHolder viewHolder = new CustomerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        details = arrayList.get(position);

        String list_id=details.getStatus_audition_list_id().trim();
        String status=details.getStatus_status().trim();
        String auditionlocation_time=details.getStatus_audition_location_time().trim();
        String audition_address=details.getStatus_udition_location_address().trim();

        String role=details.getStatus_audition_list_role();
        String desc=details.getStatus_audition_list_desc();
        String city=details.getStatus_audition_list_city();

        holder.textView_audition_list_id.setText(list_id);
        holder.textView_status.setText(status);
        holder.textView_audition_location_time.setText(auditionlocation_time);
        holder.textView_audition_address.setText(audition_address);
        holder.tv_role.setText(role);
        holder.tv_desc.setText(desc);
        holder.tvcity.setText(city);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView_audition_list_id,textView_status,textView_audition_location_time,textView_audition_address,tv_role,tv_desc,tvcity;
        LinearLayout ll_apply;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView_audition_list_id = (TextView) itemView.findViewById(R.id.textView_audition_list_id);
            textView_status = (TextView) itemView.findViewById(R.id.textView_status);
            textView_audition_location_time = (TextView) itemView.findViewById(R.id.textView_audition_location_time);
            textView_audition_address = (TextView) itemView.findViewById(R.id.textView_audition_address);

            tv_role = (TextView) itemView.findViewById(R.id.textView_audition_role);
            tv_desc = (TextView) itemView.findViewById(R.id.textView_audition_description);
            tvcity = (TextView) itemView.findViewById(R.id.textView_audition_city);
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
