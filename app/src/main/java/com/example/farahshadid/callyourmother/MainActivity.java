package com.example.farahshadid.callyourmother;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ArrayList<String> testArr = new ArrayList<String>();

        /*
        the code that was used to test
         */
        testArr.add("s");
        testArr.add("sadasdas");

       User test = new User("farah",testArr, 0);
       test.writeNewUser("farah",testArr);

        User testFake = new User("Fake",testArr, 0);
        testFake.writeNewUser(testFake.username,testArr);

        test.removeUser(test);

        testFake.updateCommitmentScore(testFake, 10);



        TopContacts contact = new TopContacts("Farah","Q",0,0);
        TopContacts contact2 = new TopContacts("Farah","J",0,0);

        contact.writeNewContact(contact2.user, contact2.contactName,contact2.amountAccepted,contact2.amountNotified);
        contact.removeTopContact(contact.user, contact.contactName);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
