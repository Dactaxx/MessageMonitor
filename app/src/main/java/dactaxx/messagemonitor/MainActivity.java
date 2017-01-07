package dactaxx.messagemonitor;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import android.telephony.SmsMessage;
import android.database.Cursor;
public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    TextView text;
    SmsManager smsManager;
    Context c = this;
    Uri messages;
    Cursor cursor;
    ContactsContract contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }


        smsManager = SmsManager.getDefault();
        messages = Uri.parse("content://sms/inbox");
        cursor = getContentResolver().query(messages, null, null, null, null);
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getColumnCount(); i++) System.out.println(cursor.getColumnName(i) + ": " + i);
        System.out.println(cursor.getColumnIndex("number"));
        do {
            //System.out.println(cursor.getColumnName(2) + ": " + cursor.getString(2));
        } while(cursor.moveToNext());

        cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        System.out.println(" ");
        for(int i = 0; i < cursor.getColumnCount(); i++) {
            System.out.println(cursor.getColumnName(i) + ": " + i);
        }
        do {
            System.out.println(cursor.getString(6));
        } while(cursor.moveToNext());

        cursor.close();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(500, 100);
        text = new TextView(this.getApplicationContext());
        text.append("piemanjoe");
        text.setY(500);
        text.setBackgroundColor(Color.rgb(255, 0, 0));

        text.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                smsManager.sendTextMessage("7343381030", null, "whoa, it worked", null, null);
                System.out.println("itworks");
            }
        });
        this.addContentView(text, params);
        startActivity(new Intent(getApplicationContext(), SignInActivity.class));

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
