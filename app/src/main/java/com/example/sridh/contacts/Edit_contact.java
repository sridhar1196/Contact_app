package com.example.sridh.contacts;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Edit_contact extends AppCompatActivity {


    ArrayList<Contact_list> contacts=new ArrayList<Contact_list>();
    public static int EDIT_REQ_CODE = 200;
    public Uri selectedUri;
    private static final int CAMERA_REQUEST = 1888;
    int id;

    private static final int DATE_DIALOG_ID = 0;
    public String imageName = null;

    private EditText birthdate;

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
        setContentView(R.layout.activity_edit_contact);

        Intent intent=this.getIntent();
        id=intent.getExtras().getInt("index");
        Contact_list contact = getIntent().getExtras().getParcelable(Display_contact.DISPLAY_KEY);
        birthdate = (EditText)findViewById(R.id.birthday);
        birthdate.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(v == birthdate)
                    showDialog(DATE_DIALOG_ID);
                return false;
            }
        });


        if (getIntent().getExtras() != null) {

            ImageButton imgId = (ImageButton) findViewById(R.id.image);
            EditText first = (EditText) findViewById(R.id.first_name);
            EditText last = (EditText) findViewById(R.id.last_name);
            EditText company = (EditText) findViewById(R.id.company);
            EditText phone = (EditText) findViewById(R.id.phone);
            EditText email = (EditText) findViewById(R.id.email);
            EditText url = (EditText) findViewById(R.id.url);
            EditText address = (EditText) findViewById(R.id.address);
            EditText birthday = (EditText) findViewById(R.id.birthday);
            EditText nickname = (EditText) findViewById(R.id.nickname);
            EditText fb = (EditText) findViewById(R.id.fb_profile);
            EditText twitter = (EditText) findViewById(R.id.twitter_profile);
            EditText skype = (EditText) findViewById(R.id.skype);
            EditText youtube = (EditText) findViewById(R.id.youtube_channel);

            setImageName(contact.image_id);

            File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String imageName = getImageName();
            File filePath = new File(picDirectory,imageName);
            Log.d("demo","value_" + filePath);

            if(filePath.exists()){
                Log.d("demo", "inside exists");
                Bitmap myBitmap = BitmapFactory.decodeFile(filePath.getPath());
                imgId.setImageBitmap(myBitmap);
                imgId.setVisibility(View.VISIBLE);
            } else {
                imgId.setImageResource(R.drawable.add_photo);
            }

            first.setText(contact.first_name);
            last.setText(contact.last_name);
            company.setText(contact.company);
            phone.setText(contact.phone_no);
            email.setText(contact.email_id);
            url.setText(contact.url);
            address.setText(contact.address);
            birthday.setText(contact.birthday);
            nickname.setText(contact.nickname);
            fb.setText(contact.fackbook_profile);
            twitter.setText(contact.twitter);
            skype.setText(contact.skype);
            youtube.setText(contact.youtube);
        }

        Bundle b = this.getIntent().getExtras();
        contacts = b.getParcelableArrayList("contacts");
        int size = contacts.size();

        contacts.remove(id);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,  mDateSetListener,  cyear, cmonth, cday);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        // onDateSet method
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date_selected = String.valueOf(dayOfMonth)+" /"+String.valueOf(monthOfYear+1)+" /"+String.valueOf(year);
            if(year >= 1850){
                birthdate.setText(date_selected);
            } else {
                Toast.makeText(getApplicationContext(), "Select date greater then 01/01/1850", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void Capture(View view){
        verifyStoragePermissions(Edit_contact.this);
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imageName = getpicturename();
        File filePath = new File(picDirectory,imageName);
        Log.d("demo","value_" + picDirectory + imageName);
        Uri pictureSave = Uri.fromFile(filePath);
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT,pictureSave);
        startActivityForResult(takePicture, CAMERA_REQ);
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
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public static boolean isEmailValid(String email) {
        String expression = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPhoneNoValid(String phoneNo) {
        String expression = "^((\\+|00)(\\d{1,3})[\\s-]?)?(\\d{10})$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }

    public void onSave(View view) {

        EditText first = (EditText) findViewById(R.id.first_name);
        EditText last = (EditText) findViewById(R.id.last_name);
        EditText company = (EditText) findViewById(R.id.company);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText email = (EditText) findViewById(R.id.email);
        EditText url = (EditText) findViewById(R.id.url);
        EditText address = (EditText) findViewById(R.id.address);
        EditText birthday = (EditText) findViewById(R.id.birthday);
        EditText nickname = (EditText) findViewById(R.id.nickname);
        EditText fb = (EditText) findViewById(R.id.fb_profile);
        EditText twitter = (EditText) findViewById(R.id.twitter_profile);
        EditText skype = (EditText) findViewById(R.id.skype);
        EditText youtube = (EditText) findViewById(R.id.youtube_channel);

        if (first.getText().toString().trim().isEmpty()){
            Toast.makeText(getBaseContext(),"First Name not entered",Toast.LENGTH_LONG).show();
        } else if (last.getText().toString().trim().isEmpty()){
            Toast.makeText(getBaseContext(),"Last Name not entered",Toast.LENGTH_LONG).show();
        } else if(!isPhoneNoValid(phone.getText().toString().trim())){
            Toast.makeText(getBaseContext(),"Phone number not valid",Toast.LENGTH_LONG).show();
        } else if((!email.getText().toString().trim().isEmpty())&&(!isEmailValid(email.getText().toString().trim()))){
            Toast.makeText(getBaseContext(),"Email id not valid",Toast.LENGTH_LONG).show();
        } else {
            Contact_list contact = new Contact_list(
                    getImageName(),
                    first.getText().toString().trim(),
                    last.getText().toString().trim(),
                    company.getText().toString().trim(),
                    phone.getText().toString().trim(),
                    email.getText().toString().trim()
                    , url.getText().toString().trim(),
                    address.getText().toString().trim(),
                    birthday.getText().toString().trim(),
                    nickname.getText().toString().trim(),
                    fb.getText().toString().trim(),
                    twitter.getText().toString().trim(),
                    skype.getText().toString().trim()
                    ,youtube.getText().toString().trim());

            contacts.add(contact);
            Collections.sort(contacts);
            Intent displayIntent = new Intent();
            displayIntent.putParcelableArrayListExtra(Display_contact.EDIT_RESULT, contacts);
            setResult(RESULT_OK, displayIntent);
            finish();
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
                    Bitmap myBitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
                    imageid.setImageBitmap(myBitmap);
                }
            }
        }
}
