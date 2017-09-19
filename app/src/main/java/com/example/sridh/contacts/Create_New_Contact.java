package com.example.sridh.contacts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.data;

public class Create_New_Contact extends AppCompatActivity {

    public String imageName = null;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    static final int CAMERA_REQ = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__new__contact);
        setImageName("");
    }
    public void Capture(View view){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imageName = getpicturename();
        File filePath = new File(picDirectory,imageName);
        Log.d("demo","value_" + picDirectory + imageName);
        Uri pictureSave = Uri.fromFile(filePath);
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT,pictureSave);
        startActivityForResult(takePicture, CAMERA_REQ);
    }
    public void Save(View view){
        EditText firstName = (EditText) findViewById(R.id.first_name);
        EditText lastName = (EditText) findViewById(R.id.last_name);
        EditText company = (EditText) findViewById(R.id.company);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText email = (EditText) findViewById(R.id.email);
        EditText url = (EditText) findViewById(R.id.url);
        EditText address = (EditText) findViewById(R.id.address);
        EditText birthday = (EditText) findViewById(R.id.birthday);
        EditText nickName = (EditText) findViewById(R.id.nickname);
        EditText fbProfile = (EditText) findViewById(R.id.fb_profile);
        EditText twitterProf = (EditText) findViewById(R.id.twitter_profile);
        EditText skype = (EditText) findViewById(R.id.skype);
        EditText youtubeChan = (EditText) findViewById(R.id.youtube_channel);
        Boolean error = false;
        if (firstName.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(),"First Name not entered",Toast.LENGTH_LONG).show();
            error = true;
        } else if (lastName.getText().toString().isEmpty()){
            Toast.makeText(getBaseContext(),"Last Name not entered",Toast.LENGTH_LONG).show();
            error = true;
        } else if(isPhoneNoValid(phone.getText().toString())){
            Toast.makeText(getBaseContext(),"Phone number not valid",Toast.LENGTH_LONG).show();
            error = true;
        } else if((!(email.getText().toString().isEmpty()) && isEmailValid(email.getText().toString()))){
            Toast.makeText(getBaseContext(),"Email id not valid",Toast.LENGTH_LONG).show();
            error = true;
        }

        if(!(error)) {
            Contact_list listOne = new Contact_list(getImageName(), firstName.getText().toString(), lastName.getText().toString(), company.getText().toString(), phone.getText().toString(), email.getText().toString(), url.getText().toString(), address.getText().toString(), birthday.getText().toString(), nickName.getText().toString(), fbProfile.getText().toString(), twitterProf.getText().toString(), skype.getText().toString(), youtubeChan.getText().toString());
            Log.d("demo","Contact" + listOne.toString());
            Intent intent = new Intent();
            intent.putExtra(Contact.NEW_CONTACT_DATA,listOne);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPhoneNoValid(String phoneNo) {
        String expression = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }

    private String getpicturename() {
        String imgName = getImageName();
        if (imgName.isEmpty()){
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp =  date.format(new Date());
            setImageName("image" + timestamp + ".jpg");
            return getImageName();
        } else {
            return getImageName();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            ImageButton imageid = (ImageButton) findViewById(R.id.image);
            File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String imageName = getImageName();
            File filePath = new File(picDirectory,imageName);
            Log.d("demo","value_" + filePath);

            if(filePath.exists()){
                Log.d("demo", "inside exists");
                Bitmap myBitmap = BitmapFactory.decodeFile(filePath.getPath());
                //Log.d("demp","size" + myBitmap.getByteCount());
                //Log.d("demo","width" + myBitmap.getWidth());
                //Toast.makeText(getBaseContext(),"width:"+ myBitmap.getWidth() +"height:" + myBitmap.getHeight(),Toast.LENGTH_LONG).show();
                //Bitmap scaled = Bitmap.createScaledBitmap(myBitmap,150,100,true);
                imageid.setImageBitmap(myBitmap);
                imageid.setVisibility(View.VISIBLE);
            }
        }
    }
}
