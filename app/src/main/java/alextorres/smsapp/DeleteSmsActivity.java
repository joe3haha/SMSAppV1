package alextorres.smsapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Context;
import android.widget.TextView;

public class DeleteSmsActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_sms);
        String message = "";
        Bundle messageID = getIntent().getExtras();
        if (messageID != null) {
            message = messageID.getString("MESSAGE_ID");
        }
        message = getMessage(getApplicationContext(),message);
        text = (TextView) findViewById(R.id.messagePlaceHolder);
        text.setText(message);
    }


    public String getMessage(Context context, String ID){
        ContentResolver cr = context.getContentResolver();
        String Query = "_ID="+ID;
        String message;
        Cursor MessageCursor = cr.query(Uri.parse("content://sms/"), null, Query, null, null);

        if(MessageCursor.moveToFirst()){
            message = MessageCursor.getString(MessageCursor.getColumnIndexOrThrow("body"));
            MessageCursor.close();
            return message;
        }

        return "No Message to show, error has occurred.";
    }

    public void deleteMessage(){

    }

}
