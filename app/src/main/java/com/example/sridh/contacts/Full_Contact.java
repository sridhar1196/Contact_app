package com.example.sridh.contacts;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class Full_Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__contact);

        if(getIntent().getExtras()!=null) {
            Log.d("demo","sdad");
            Contact_list contact = getIntent().getExtras().getParcelable(Display_contact.DISPLAY_KEY);
            ImageView imageid = (ImageView) findViewById(R.id.imageView2);
            File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File filePath = new File(picDirectory,contact.image_id);
            Log.d("demo","value_" + filePath);
            if(filePath.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(filePath.getPath());
                imageid.setImageBitmap(myBitmap);
                imageid.setVisibility(View.VISIBLE);
                Log.d("demo", "inside exists");
            } else {
                imageid.setImageResource(R.drawable.add_photo);
                imageid.setVisibility(View.VISIBLE);
            }
            TextView name = (TextView) findViewById(R.id.name);
            TextView phone = (TextView) findViewById(R.id.phone);
            TextView email = (TextView) findViewById(R.id.Email);
            TextView company = (TextView) findViewById(R.id.company);
            TextView birthday = (TextView) findViewById(R.id.birthday);
            TextView url = (TextView) findViewById(R.id.url);
            TextView fb = (TextView) findViewById(R.id.fb);
            TextView twitter = (TextView) findViewById(R.id.twitter);
            TextView skype = (TextView) findViewById(R.id.skype);
            TextView youtube = (TextView) findViewById(R.id.youtube);

            name.setText(contact.first_name + " " + contact.last_name);
            phone.setText(contact.phone_no);
            email.setText(contact.email_id);
            company.setText(contact.company);
            birthday.setText(contact.birthday);
            url.setText(contact.url);
            fb.setText(contact.fackbook_profile);
            twitter.setText(contact.twitter);
            skype.setText(contact.skype);
            youtube.setText(contact.youtube);
        }
    }

    public void onClick(View view)
    {
        TextView url = (TextView) findViewById(R.id.url);
        TextView fb = (TextView) findViewById(R.id.fb);
        TextView twitter = (TextView) findViewById(R.id.twitter);
        TextView skype = (TextView) findViewById(R.id.skype);
        TextView youtube = (TextView) findViewById(R.id.youtube);

        Intent intent=new Intent();
        if(view.getId()==R.id.url)
        {
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url.getText().toString()));
            startActivity(intent);
        }
        else if(view.getId()==R.id.fb)
        {
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse(fb.getText().toString()));
            startActivity(intent);
        }
        else if(view.getId()==R.id.twitter)
        {
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse(twitter.getText().toString()));
            startActivity(intent);
        }
        else if(view.getId()==R.id.skype)
        {
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse(skype.getText().toString()));
            startActivity(intent);
        }
        else if(view.getId()==R.id.youtube)
        {
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse(youtube.getText().toString()));
            startActivity(intent);
        }

    }
}
