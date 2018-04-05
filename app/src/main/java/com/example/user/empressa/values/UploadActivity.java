package com.example.user.empressa.values;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.empressa.MainActivity;
import com.example.user.empressa.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class UploadActivity extends Activity {

    //define global views variable
    public ImageView imageView;
    public Button selectImage,uploadImage;
    //public String SERVER = "http://192.168.1.2/co/storemanger/saveImage.php", timestamp;
    public static final String SERVER = "http://askdial.co.in/empressa/Videoupload_/imageupload";
    //public String SERVER = "http://askdial.co.in/empressa/Videoupload_/imageupload", timestamp;

    HashMap<String, String> postdata;

    private static final String TAG = MainActivity.class.getSimpleName();
    static boolean completed = false;
    static String Name="11054_image";

    String imagepath;

    private static final int RESULT_SELECT_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        postdata = new HashMap<>();

        //instantiate view
        imageView = (ImageView) findViewById(R.id.imageView);
        selectImage=findViewById(R.id.selectImage);
        uploadImage=findViewById(R.id.uploadImage);


        //when selectImage button is pressed
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the function to select image from album
                selectImage();
            }
        });

        //when uploadImage button is pressed
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get image in bitmap format
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                //execute the async task and upload the image to server

                String root = Environment.getExternalStorageDirectory()
                        .toString();
                File newDir = new File(root + "/Empessa_profilepic");
                newDir.mkdirs();
                Random gen = new Random();
                int n = 10000;
                n = gen.nextInt(n);
                String fotoname = "cm_" + n + ".jpg";
                File file = new File(newDir, fotoname);
                imagepath = file.getAbsolutePath();
                Log.i("Path of saved image.", imagepath);
                System.err.print("Path of saved image." + imagepath);

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, out);

                    out.flush();
                    // Toast.makeText(getApplicationContext(), "Photo Saved " + fotoname,Toast.LENGTH_SHORT).show();
                    out.close();
                } catch (Exception e) {

                    Log.e("Exception", e.toString());
                }
                scanPhoto(getApplicationContext(),imagepath);
                String actualimagepath = imagepath;

                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                    options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(actualimagepath, options);

                // new Upload_Image(rotateImage(bitmap, ImagePath), Mobile, Visitors_id, postdata).execute();
                //test
                // new Upload(image,"IMG_"+timestamp).execute();
                new Upload_Image(bitmap, Name, postdata).execute();

            }
        });
    }

    private  void scanPhoto(Context ctx, String imgFileName) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }
    //function to select a image
    private void selectImage() {
        //open album to select image
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, RESULT_SELECT_IMAGE);
    }


    //This function is called when we pick some image from the album

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null){
            //set the selected image to image variable
            Uri image = data.getData();
            imageView.setImageURI(image);

            //get the current timeStamp and strore that in the time Variable
            Long tsLong = System.currentTimeMillis() / 1000;


           // Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
        }
    }

    private String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private static class Upload_Image extends AsyncTask<String, String, String> {
        String responsestr = "", Name, Visitors_id;
        Bitmap image;
        HashMap<String, String> datamap;
        URL url;

        public Upload_Image(Bitmap image, String mobile, HashMap<String, String> hashMap) {
            this.image = image;
            this.Name = mobile;
            this.datamap = hashMap;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                responsestr = uploadimage(url, datamap, image, 100);
                return responsestr;
            } catch (OutOfMemoryError ex) {
                Log.d("debug", "OutofMemory Exception caught");
                responsestr = uploadimage(url, datamap, image, 75);
                return responsestr;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("debug", "Image upload result: " + result);
            completed = true;
        }
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private static String uploadimage(URL url, HashMap<String, String> datamap, Bitmap image, int quality) {
        String response = "";
        datamap = new HashMap<>();
        Log.d("debug", "Image upload 1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Log.d("debug", "Image upload 2");
        Log.d("debug", "Image upload Compressing to "+quality+"..");
        image.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        Log.d("debug", "Image upload 3");
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        long lengthbmp = imageInByte.length;
        Log.d("debug", "Image size to Upload: "+lengthbmp);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        Log.d("debug", "Image upload 4");
        datamap.put("image", encodedImage);
        Log.d("debug", "Image upload 5");
        datamap.put("name", Name);
        Log.d("debug", "Image upload 6");
        try {
            url = new URL(SERVER);
            Log.d("debug", "Image upload 8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d("debug", "Image upload 9");
            conn.setReadTimeout(15000);
            Log.d("debug", "Image upload 10");
            conn.setConnectTimeout(15000);
            Log.d("debug", "Image upload 11");
            conn.setRequestMethod("POST");
            Log.d("debug", "Image upload 12");
            conn.setDoInput(true);
            Log.d("debug", "Image upload 13");
            conn.setDoOutput(true);
            Log.d("debug", "Image upload 14");

            OutputStream os = conn.getOutputStream();
            Log.d("debug", "Image upload 15");
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            Log.d("debug", "Image upload 16");
            try {
                writer.write(getPostDataString(datamap));
                Log.d("debug", "Image upload 17");
            } catch (RuntimeException re) {
                Log.d("debug", "RunTime Exception Image upload 17");
                writer.write(getPostDataString(datamap));
                Log.d("debug", "Image upload 17");
            } catch (UnsupportedEncodingException ee) {
                Log.d("debug", "UnsupportedEncodingException Image upload 17");
                writer.write(getPostDataString(datamap));
                Log.d("debug", "Image upload 17");
            } catch (OutOfMemoryError ome) {
                Log.d("debug", "OutOfMemoryError Image upload 17");
                writer.write(getPostDataString(datamap));
                Log.d("debug", "Image upload 17");
            }
            writer.flush();
            Log.d("debug", "Image upload 18");
            writer.close();
            Log.d("debug", "Image upload 19");
            os.close();
            Log.d("debug", "Image upload 20");
            int responseCode=conn.getResponseCode();
            Log.d("debug", "Image upload 21");
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.d("debug", "Image upload 22");
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Log.d("debug", "Image upload 23");
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
                Log.d("debug", "Image upload 24");
            }
            else {
                response="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("debug", "Response for upload image "+response);
        return response;
    }
}
