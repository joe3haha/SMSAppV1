package alextorres.smsapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapterNames, arrayAdapterId, arrayAdapterDrafts;
    ListView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ContentResolver cr = getContentResolver();
        ArrayList<String> ident = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        arrayAdapterNames = new ArrayAdapter<String>(this, 			android.R.layout.simple_list_item_1, names);
        //arrayAdapterId = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, id);
        contactList = (ListView) findViewById(R.id.ContactList);
        contactList.setAdapter(arrayAdapterNames);
        //contactList.setOnItemClickListener(this);

         contactList.setClickable(true);
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {



            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                new SMS(ContactsContract.PhoneLookup.NUMBER);//pass phone number to sms activity

                Intent intent = new Intent(getApplicationContext(), SMS.class);
                startActivity(intent);
            }


        });

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
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
