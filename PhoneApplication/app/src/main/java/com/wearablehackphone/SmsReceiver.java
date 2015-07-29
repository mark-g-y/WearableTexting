package com.wearablehackphone;

/**
 * Created by Mark on 13/12/2014.
 */
import  android.content.BroadcastReceiver;
import  android.content.Context;
import  android.content.Intent;
import  android.os.Bundle;
import  android.telephony.SmsMessage;
import  android.util.Log;

import com.wearablehackphone.bluetooth.BluetoothChat;

// This will run when an SMS message comes in.
// We can see if we want to do something based upon the message
// Perhaps launch an activity
public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context ctx, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0; i < messages.length; i++)
        {
            messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            Log.v("SMSFun","Body: " + messages[i].getDisplayMessageBody());
            Log.v("SMSFun","Address: " + messages[i].getDisplayOriginatingAddress());
            // we could launch an activity and pass the data
            Intent newintent = new Intent(ctx, BluetoothChat.class);
            newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // Pass in data
            Bundle b = new Bundle();
            b.putString("address", messages[i].getDisplayOriginatingAddress());
            b.putString("message", messages[i].getDisplayMessageBody());
            newintent.putExtras(b);
            ctx.startActivity(newintent);
        }
    }
}
