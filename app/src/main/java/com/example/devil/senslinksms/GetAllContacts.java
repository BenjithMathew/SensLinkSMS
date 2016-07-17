package com.example.devil.senslinksms;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GetAllContacts extends Activity implements View.OnClickListener {

    ArrayList<PhoneContacts> contacts;
    public static ArrayList<String> inviteList;

    String[] contactNames;
    String[] contactNUmbers;
    Button btn, btn_invite;
    CheckBox checkBox;
    ListView listView;
    String authKey;
    String senderID;
    String message;
    String route;
    URLConnection myURLConnection;
    URL myURL;
    String mainUrl = null;
    String contactName = null;
    String phNumber = null;
    BufferedReader reader=null;
    StringBuilder sbPostData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_contacts);

        btn = (Button) findViewById(R.id.button1);
        btn_invite = (Button) findViewById(R.id.btn_invite);
        listView = (ListView) findViewById(R.id.listView1);
        checkBox= (CheckBox) findViewById(R.id.cbInvite);

        contacts = new ArrayList<>();
        contactNames = null;
        contactNUmbers = null;
        String x = "";

        authKey = "102086AOLwF637x5690adcc";
        senderID = "SURROU";
        message = "hey guys whats going n";
        route = "4";

        myURL = null;
        myURLConnection = null;
        mainUrl="http://api.msg91.com/api/sendhttp.php?";
        sbPostData= new StringBuilder(mainUrl);

        sbPostData.append("authkey="+authKey);


        Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (c.moveToNext()) {

            contactName = c
                    .getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phNumber = c
                    .getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            PhoneContacts singleContacts = new PhoneContacts(contactName, phNumber, false);

            contacts.add(singleContacts);
            Collections.sort(contacts);

        }
        c.close();



        btn.setOnClickListener(this);
        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteList = new ArrayList<>();
                if (!PhoneContactsAdapter.msgInviteList.isEmpty()) {
                    inviteList = PhoneContactsAdapter.msgInviteList;
                    String mobiles = null;
                    String x="";
                    for (int i =0; i<inviteList.size(); i++) {
                        if(inviteList.size() -1 == i)
                            mobiles = inviteList.get(i);
                        else
                            mobiles = inviteList.get(i)+",";

                        x=x+mobiles;
                    }
                    Log.d("GoingIn"," "+x);

                    sbPostData.append("&mobiles="+x);
                    Log.d("GoingIn"," "+sbPostData);
                    sbPostData.append("&message="+message);
                    sbPostData.append("&route="+route);
                    sbPostData.append("&sender="+senderID);

                    mainUrl = sbPostData.toString();

                    new Thread() {
                        @Override
                        public void run() {
                            try
                            {
                                Log.d("GoingIn"," "+sbPostData);
                                //prepare connection
                                myURL = new URL(mainUrl);
                                myURLConnection = myURL.openConnection();
                                myURLConnection.connect();
                                reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

                                //reading response
                                String response;
                                while ((response = reader.readLine()) != null)
                                    //print response
                                    Log.d("RESPONSE", ""+response);

                                //finally close connection
                                reader.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                    Toast.makeText(getBaseContext(),"Invitation send Successfully",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(),"Select contacts",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        final PhoneContactsAdapter adapter = new PhoneContactsAdapter(this, contacts);
        adapter.sort(new Comparator<PhoneContacts>() {
            @Override
            public int compare(PhoneContacts lhs, PhoneContacts rhs) {
                return 0;
            }
        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s= parent.getItemAtPosition(position).toString();
                Toast.makeText(GetAllContacts.this, ""+s, Toast.LENGTH_SHORT).show();
            }
        });

    }


}


