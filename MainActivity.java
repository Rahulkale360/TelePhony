package com.example.rahul.telephony;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean isSmsEnabled;
    boolean isCallEnabled;
    EditText et1,et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        int status= ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);
        int status1=ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE);
        if(status == PackageManager.PERMISSION_GRANTED )
        {
            isSmsEnabled=true;
            }
        if( status1==PackageManager.PERMISSION_GRANTED)
        {
            isCallEnabled=true;

        }

        if(status == PackageManager.PERMISSION_DENIED ||status1==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions
                    (this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE},1);
            }

    }
    public void makeCall(View v)
    {
            if(isCallEnabled)
            {
                Intent i=new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("tel:"+et1.getText().toString()));
                startActivity(i);
            }
            else
                Toast.makeText(this,"pls grant permission to call",Toast.LENGTH_LONG).show();
    }
    public void sendSms(View v) {
        if (isSmsEnabled) {
            Intent si = new Intent(this, SendActivity.class);
            Intent di = new Intent(this, DeliverActivity.class);
            PendingIntent sintent = PendingIntent.getActivity(this, 0, si, 0);
            PendingIntent dintent = PendingIntent.getActivity(this, 0, di, 0);
            String[] val = et1.getText().toString().split(",");
            for (int i = 0; i < val.length; i++) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(val[i], null, et2.getText().toString(), sintent, dintent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            isSmsEnabled=true;
        } if(grantResults[1]==PackageManager.PERMISSION_GRANTED)
        {
            isCallEnabled=true;
        }

    }
}
