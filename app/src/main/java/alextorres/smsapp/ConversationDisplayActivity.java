package alextorres.smsapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ConversationDisplayActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_display);
    }

    private ArrayList<String> getMessages(String thread_id){
        ContentResolver cr = getContentResolver();
        ArrayList<String> messages = new ArrayList<String>();
        String query = "thread_id=" + thread_id;
        Cursor conversationMessageCursor = cr.query(Uri.parse("content://sms/inbox"), null, query, null, null);

        if (conversationMessageCursor.getCount() > 0) {
            while (conversationMessageCursor.moveToNext()) {
                String id = conversationMessageCursor.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                ident.add(id);
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                names.add(name);
                //if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                //System.out.println("Blue\n");
                //}
            }
        }

        return messages;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversation_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
