package com.example.sridh.contacts;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Edit_contact extends AppCompatActivity {


    ArrayList<Contact_list> contacts=new ArrayList<Contact_list>();
    public static final String EDIT_KEY = "EDIT";
    public static int EDIT_REQ_CODE = 200;
    public Uri selectedUri;
    private static final int CAMERA_REQUEST = 1888;
    int id;

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
        setContentView(R.layout.activity_edit_contact);

        Intent intent=this.getIntent();
        id=intent.getExtras().getInt("index");
        Contact_list contact = getIntent().getExtras().getParcelable(Display_contact.DISPLAY_KEY);


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


            Toast.makeText(this, "name: " + contact.first_name, Toast.LENGTH_LONG).show();
        }

        Bundle b = this.getIntent().getExtras();
        contacts = b.getParcelableArrayList("contacts");
        int size = contacts.size();

        contacts.remove(id);

//        Toast.makeText(getBaseContext()," "+ contacts.size(),Toast.LENGTH_LONG).show();
//        Intent i = new Intent(getBaseContext(), ContactsList.class);
//        Bundle c = new Bundle();
//        c.putParcelableArrayList("contacts", contacts);
//        i.putExtras(c);
//        i.putExtra("id", EDIT_KEY);
//        startActivityForResult(i, EDIT_REQ_CODE);

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

//    public void onClickCamera(View v) {
////            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////            startActivityForResult(cameraIntent, CAMERA_REQUEST);
//
//        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedUri);
//        startActivityForResult(photoIntent, CAMERA_REQUEST);
//
////            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
////        startActivity(intent);
//    }

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

//        contacts.get(id).first_name=first.get
        Contact_list contact = new Contact_list(
                getImageName(),
                first.getText().toString(),
                last.getText().toString(),
                company.getText().toString(),
                phone.getText().toString(),
                email.getText().toString()
                , url.getText().toString(),
                address.getText().toString(),
                birthday.getText().toString(),
                nickname.getText().toString(),
                fb.getText().toString(),
                twitter.getText().toString(),
                skype.getText().toString()
                ,youtube.getText().toString());

        contacts.add(contact);
        Collections.sort(contacts);
//        Intent i = new Intent(getBaseContext(), ContactsList.class);
//        Bundle b = new Bundle();
//        b.putParcelableArrayList("contacts", contacts);
//        i.putExtras(b);
//        i.putExtra("id", EDIT_KEY);
//        startActivityForResult(i, EDIT_REQ_CODE);



        Intent displayIntent = new Intent();
        displayIntent.putParcelableArrayListExtra(Display_contact.EDIT_RESULT, contacts);
        setResult(RESULT_OK, displayIntent);
        finish();

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
