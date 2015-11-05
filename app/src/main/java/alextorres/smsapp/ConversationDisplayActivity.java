package alextorres.smsapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ConversationDisplayActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapterMessages;
    ListView messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String threadID = "99";
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
