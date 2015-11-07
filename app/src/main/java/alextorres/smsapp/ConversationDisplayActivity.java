package alextorres.smsapp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ConversationDisplayActivity extends AppCompatActivity {

<<<<<<< HEAD
    ArrayAdapter<String> arrayAdapterMessages;
    ListView messageList;
    ListView forwardLV = (ListView) findViewById(R.id.forwardMessage);
    public String threadID;

=======
    ArrayAdapter<String> arrayAdapterMessages, arrayAdapterNames;
    ListView messageList, nameList;
>>>>>>> joe3haha/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerForContextMenu(forwardLV);
        threadID = "99";
        setContentView(R.layout.activity_conversation_display);
        Bundle threadDetail = getIntent().getExtras();
        if (threadDetail != null) {
            threadID = threadDetail.getString("THREAD_ID");
        }
        ArrayList<String> messages = getMessages(threadID);
        Collections.reverse(messages);
        arrayAdapterMessages = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);
        messageList = (ListView) findViewById(R.id.conversationMessageList);
        messageList.setAdapter(arrayAdapterMessages);
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence text = "Clicked on the item!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getBaseContext(), text, duration);
                toast.show();
            }
        });

        ArrayList<String> names = getNames(threadID);
        Collections.reverse(names);
        arrayAdapterNames = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        nameList = (ListView) findViewById(R.id.namesOrNumbers);
        nameList.setAdapter(arrayAdapterNames);
        nameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence text = "Clicked on the name!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getBaseContext(), text, duration);
                toast.show();
            }
        });

    }

    private ArrayList<String> getMessages(String thread_id){
        ContentResolver cr = getContentResolver();
        ArrayList<String> messages = new ArrayList<String>();
        String query = "thread_id=" + thread_id;
        Cursor conversationMessageCursor = cr.query(Uri.parse("content://sms/"), null, query, null, null);

        if (conversationMessageCursor.getCount() > 0) {
            while (conversationMessageCursor.moveToNext()) {
                try{
                    messages.add(conversationMessageCursor.getString(conversationMessageCursor.getColumnIndexOrThrow("body")).toString());

                }catch (Exception E){
                    System.out.println("Something went wrong: " + E);
                }
            }
        }

        return messages;
    }

    private ArrayList<String> getNames(String thread_id){
        ContentResolver cr = getContentResolver();
        ArrayList<String> messages = new ArrayList<String>();
        String query = "thread_id=" + thread_id;
        String name = null;
        String number = null;
        Cursor conversationMessageCursor = cr.query(Uri.parse("content://sms/"), null, query, null, null);

        if (conversationMessageCursor.getCount() > 0) {
            while (conversationMessageCursor.moveToNext()) {
                try{
                    number = conversationMessageCursor.getString(conversationMessageCursor.getColumnIndexOrThrow("address")).toString();
                    name = getContactName(getApplicationContext(), conversationMessageCursor.getString(conversationMessageCursor.getColumnIndexOrThrow("address")));

                    if(name == null) {
                        messages.add(number);
                    }else{
                        messages.add(name);
                    }

                }catch (Exception E){
                    System.out.println("Something went wrong: " + E);
                }
            }
        }

        return messages;
    }

    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.forwardMessage) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_conversation_display, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String body = messageList.getSelectedItem().toString();
        if (item.getItemId() == R.id.forward) {
            new SMS(body);
        }
        return true;
    }

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
