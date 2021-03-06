package com.example.sridh.contacts;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Display_contact extends AppCompatActivity {
    public static final String DISPLAY_KEY = "Display-key";
    public static final String EDIT_RESULT = "Edit-key";
    int listSelecte = 0;
    ArrayList<Contact_list> contacts;
    String[] IMAGES;
    String[] firstNamelastName;
    String[] phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        if(getIntent().getExtras() != null){
            Bundle bundleContacts = this.getIntent().getExtras();
            if(getIntent().getExtras().containsKey(Contact.EDIT)){
                contacts = bundleContacts.getParcelableArrayList("contacts");
                int size = contacts.size();
                Log.d("demo","value" + size);
                createLayout(size);
            } else if(getIntent().getExtras().containsKey(Contact.DELETE)){
                contacts = bundleContacts.getParcelableArrayList("contacts");
                int size = contacts.size();
                createLayout(size);
            } else if(getIntent().getExtras().containsKey(Contact.DISPLAY)){
                contacts = bundleContacts.getParcelableArrayList("contacts");
                int size = contacts.size();
                createLayout(size);
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Contact.EDIT_CONTACT) {
            if(resultCode == RESULT_OK){
                contacts = data.getParcelableArrayListExtra(EDIT_RESULT);
                Collections.sort(contacts);
                Intent displayIntent = new Intent();
                displayIntent.putParcelableArrayListExtra(Contact.EDIT, contacts);
                setResult(RESULT_OK, displayIntent);
                this.finish();

            } else if(resultCode == RESULT_CANCELED) {
                Collections.sort(contacts);

            }
        }

}
    private void createLayout(int size) {
        IMAGES = new String[contacts.size()];
        firstNamelastName = new String[contacts.size()];
        phonenumber = new String[contacts.size()];
        for(int i = 0;i<size;i++){
            IMAGES[i] = contacts.get(i).image_id;
            firstNamelastName[i] = contacts.get(i).first_name + " " + contacts.get(i).last_name;
            phonenumber[i] = contacts.get(i).phone_no;
        }
        final ListView listView = (ListView) findViewById(R.id.listview1);
        Log.d("demo","after list");
        CustomAdapter adapter = new CustomAdapter();
        verifyStoragePermissions(Display_contact.this);
        Log.d("demo","after custom adaptor");
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(getIntent().getExtras().containsKey(Contact.DISPLAY))
                {
                    Intent intent=new Intent(Display_contact.this,Full_Contact.class);
                    intent.putExtra(DISPLAY_KEY,contacts.get(i));
                    startActivity(intent);
                }
                else if (getIntent().getExtras().containsKey(Contact.EDIT))
                {
                    Intent intent =new Intent(Display_contact.this,Edit_contact.class);
                    intent.putExtra(DISPLAY_KEY,contacts.get(i));
                    intent.putExtra("index",Integer.parseInt(String.valueOf(i)));
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("contacts", contacts);
                    intent.putExtras(b);
                    startActivityForResult(intent,Contact.EDIT_CONTACT);
                }
                else if(getIntent().getExtras().containsKey(Contact.DELETE))
                {
                    Log.d("demo","entered Delete");
                    listSelecte = i;
                    AlertDialog.Builder builder=new AlertDialog.Builder(Display_contact.this);
                    builder.setTitle("Delete Contact")
                            .setMessage("Do you really want to delete this?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    contacts.remove(listSelecte);

                                    Collections.sort(contacts);

                                    Intent displayIntent = new Intent();
                                    displayIntent.putParcelableArrayListExtra(Contact.DELETE, contacts);
                                    setResult(RESULT_OK, displayIntent);
                                    finish();
                                    //startActivity(getIntent());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    final AlertDialog alert=builder.create();
                    alert.show();
                }
            }

        });


    }
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.defaultImage);
            File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File filePath = new File(picDirectory,IMAGES[i]);
            Log.d("demo","value_" + filePath);

            if(filePath.exists()){
                Log.d("demo", "inside exists");
                Bitmap myBitmap = BitmapFactory.decodeFile(filePath.getPath());
                //imageView.setImageResource(R.drawable.default_image);
                imageView.setImageBitmap(myBitmap);
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setImageResource(R.drawable.default_image);
            }
            TextView textview_name = (TextView) view.findViewById(R.id.firstLastName);
            TextView phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
            textview_name.setText(firstNamelastName[i]);
            phoneNumber.setText(phonenumber[i]);
            return view;
        }
    }
}


