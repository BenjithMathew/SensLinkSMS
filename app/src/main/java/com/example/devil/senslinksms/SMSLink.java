package com.example.devil.senslinksms;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SMSLink extends Activity implements View.OnClickListener {

    Button btnSMS;

    SmsManager smsManager;

    StringBuffer smsBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smslink);

        btnSMS = (Button) findViewById(R.id.activity_smslink_btn_sms);

        smsManager = SmsManager.getDefault();
        smsBody = new StringBuffer();

        btnSMS.setOnClickListener(this);

        smsBody.append("https://surroundsync.slack.com/messages/dev/");

    }

    @Override
    public void onClick(View v) {

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("vnd.android-dir/mms-sms");
        sendIntent.putExtra("address", "9632785165");
        sendIntent.putExtra("sms_body",smsBody.toString());

        startActivity(sendIntent);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
    }
}
