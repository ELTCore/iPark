package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CountDownCheckOut extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_check_out);

        //android.app.ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        /*public boolean onOptionsItemSelected(MenuItem menu) {
            Intent myIntent = new Intent(getApplicationContext(), UserHomepage.class);
            startActivityForResult(myIntent, 0);
            return true;

        }*/

        Button checkoutButt = (Button) findViewById(R.id.button6);
        Button reportButt = (Button) findViewById(R.id.button2);
        Button emergencyButt = (Button) findViewById(R.id.emer);
        Button mapButt = (Button) findViewById(R.id.button8);
        Button help = (Button) findViewById(R.id.button16);


        /* information page */
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(CountDownCheckOut.this);
                hlp.setTitle("Help Information");
                hlp.setMessage("Timer will start when at the time you put for Clock In.\n" +
                        "Click 'CHECKOUT' at any time to sign out and end your reservation.\n"+
                        "Click 'REPORT' to report illegal parking if there is a car in your spot. " +
                        "You will recieve a new parking space\n"
                        + " 'MAP' will give you a map of where your space is.\n" +
                        " 'EMERGENCY' is to call the police in case of an emergency.");
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });

                hlp.setNegativeButton("No", null);
                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });


        /* check out and go to review */
        checkoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, activity_review.class);
                startActivity(intent);

            }
        });


        /* report your own spot */
        reportButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, ReportIllegal.class);
                startActivity(intent);

            }
        });


        /* go to map */
        mapButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, MapDirectional.class);
                startActivity(intent);

            }
        });


        /* emergency call */
        emergencyButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, BossEmergency.class);
                startActivity(intent);

            }
        });

    }
}
