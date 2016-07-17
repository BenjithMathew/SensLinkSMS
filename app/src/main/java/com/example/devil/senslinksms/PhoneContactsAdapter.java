package com.example.devil.senslinksms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PhoneContactsAdapter extends ArrayAdapter<PhoneContacts> {

    public static ArrayList<String> msgInviteList = new ArrayList<>();
    ListView list;
    ContactsHolder holder;

    static class ContactsHolder {
        TextView tv_contact_name;
        TextView tv_contact_number;
        CheckBox cbinvite;
        RelativeLayout rl;
    }


    public PhoneContactsAdapter(Context context, ArrayList<PhoneContacts> contacts) {
        super(context, R.layout.custom_contacts_row, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            holder=null;
        final PhoneContacts eachContact = getItem(position);

        if (convertView == null){
            holder = new ContactsHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_contacts_row, parent, false);

            holder.tv_contact_name = (TextView) convertView.findViewById(R.id.tv_contact_name);
            holder.tv_contact_number = (TextView) convertView.findViewById(R.id.tv_contact_no);
            holder.rl=(RelativeLayout)convertView.findViewById(R.id.itemLayout);
            holder.cbinvite = (CheckBox) convertView.findViewById(R.id.cbInvite);
        }else {
            holder = (ContactsHolder) convertView.getTag();
        }

        holder.tv_contact_name.setText(eachContact.getContactName());
        holder.tv_contact_number.setText(eachContact.getContactNumber());

        convertView.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eachContact.isCheck()) {
                    if (msgInviteList.contains(eachContact.getContactNumber())) {
                        msgInviteList.remove(eachContact.getContactNumber());
                        eachContact.setChecked(false);
                    }
                } else {
                    if (!msgInviteList.contains(eachContact.getContactNumber())) {
                        msgInviteList.add(eachContact.getContactNumber());
                        eachContact.setChecked(true);
                    }
                }
                notifyDataSetChanged();
               /* Log.d("list: ", " " + msgInviteList);
                Toast.makeText(getContext()," "+eachContact.getContactName(),Toast.LENGTH_SHORT).show();*/
            }
        });


        holder.cbinvite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    if (!msgInviteList.contains(eachContact.getContactNumber())) {
                        msgInviteList.add(eachContact.getContactNumber());
                        eachContact.setChecked(true);
                    }
               //     Toast.makeText(getContext(), " Name: " + eachContact.contactName + " \n " + "Contact No: " + eachContact.getContactNumber(), Toast.LENGTH_LONG).show();

                } else {
                    if (msgInviteList.contains(eachContact.getContactNumber())) {
                        msgInviteList.remove(eachContact.getContactNumber());
                        eachContact.setChecked(false);
                    }

                }

            //    Log.d("list: ", " " + msgInviteList);

            }
        });

        if (eachContact.isChecked) {
            holder.cbinvite.setChecked(true);
            eachContact.setChecked(true);
        } else {
            holder.cbinvite.setChecked(false);
            eachContact.setChecked(false);
        }
        convertView.setTag(holder);


        return convertView;
    }
}
