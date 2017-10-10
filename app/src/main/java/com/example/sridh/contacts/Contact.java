package com.example.sridh.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;

public class Contact extends AppCompatActivity {
    public static final int CREATE_CONTACT = 15;
    public static final int EDIT_CONTACT = 20;
    public static final int DISPLAY_CONTACT = 25;
    public static final int DELETE_CONTACT = 30;
    public static final String NEW_CONTACT_DATA = "newData";
    public static final String EDIT = "Edit";
    public static final String DELETE = "Delete";
    public static final String DISPLAY = "Display";
    public static String CALL_TYPE = "call";
    ArrayList<Contact_list> contacts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }
    public void Contact_create(View view){
        Intent intent = new Intent(Contact.this,Create_New_Contact.class);
        startActivityForResult(intent,CREATE_CONTACT);
    }
    public void Contact_display(View view){
        Intent intent = new Intent(getBaseContext(),Display_contact.class);
        if(view.getId() == R.id.edit_contact){
            Bundle bundleContacts = new Bundle();
            bundleContacts.putParcelableArrayList("contacts", contacts);
            intent.putExtras(bundleContacts);
            intent.putExtra("Edit",EDIT);
            startActivityForResult(intent,EDIT_CONTACT);
        } else if(view.getId() == R.id.delete_contact){
            Bundle bundleContacts = new Bundle();
            bundleContacts.putParcelableArrayList("contacts", contacts);
            intent.putExtras(bundleContacts);
            intent.putExtra("Delete",DELETE);
            startActivityForResult(intent,DELETE_CONTACT);
        } else if(view.getId() == R.id.disp_contact){
            Bundle bundleContacts = new Bundle();
            bundleContacts.putParcelableArrayList("contacts", contacts);
            intent.putExtras(bundleContacts);
            intent.putExtra("Display",DISPLAY);
            startActivityForResult(intent,DISPLAY_CONTACT);
        }
    }
    public void Contact_exit(View view){
        finish();
        System.exit(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_CONTACT){
            if(resultCode == RESULT_OK){
                Log.d("demo","Contact");
                contacts.add((Contact_list) data.getParcelableExtra(NEW_CONTACT_DATA));
                Collections.sort(contacts);
            }
        } else if(requestCode == EDIT_CONTACT){
            if(resultCode == RESULT_OK){
                contacts = data.getParcelableArrayListExtra(EDIT);
                Collections.sort(contacts);
            }
        } else if (requestCode == DELETE_CONTACT){
            if(resultCode == RESULT_OK){
                Log.d("demo","in delete main");
                contacts = data.getParcelableArrayListExtra(DELETE);
                Collections.sort(contacts);
            }
        } else if (requestCode == DISPLAY_CONTACT){
            if(resultCode == RESULT_OK){
                contacts = data.getParcelableArrayListExtra(DISPLAY);
                Collections.sort(contacts);
            }
        }
    }
}
