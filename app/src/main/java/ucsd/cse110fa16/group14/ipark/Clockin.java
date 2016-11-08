package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

public class Clockin extends AppCompatActivity {

    private static HashSet<String> parking = new HashSet<>();
    private static HashSet<String> spots = new HashSet<>();
    protected static HashMap<String, String> parkSpotReserve = new HashMap<>();
    /*private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;
*/

    private int min, hour, curMin, curHour, ampm;

    public void dialogEvent(View view) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clockin);
        final Bundle bundle = getIntent().getExtras();


        Button homeButt = (Button) findViewById(R.id.button8);
        Button help = (Button) findViewById(R.id.button4);
        Button nextButt = (Button) findViewById(R.id.next);

        NumberPicker hourNumPick = (NumberPicker) findViewById(R.id.hour);
        NumberPicker minNumPick = (NumberPicker) findViewById(R.id.min);
        NumberPicker amPmPick = (NumberPicker) findViewById(R.id.amPM);
        final String[] s = {"AM", "PM"};

        //Populate values from minimum and maximum value range
        hourNumPick.setMinValue(1);
        minNumPick.setMinValue(0);
        amPmPick.setMinValue(1);


        //Specify the maximum value/number
        hourNumPick.setMaxValue(12);
        minNumPick.setMaxValue(59);
        amPmPick.setMaxValue(2);

        // Display text
        amPmPick.setDisplayedValues(s);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        hourNumPick.setWrapSelectorWheel(true);
        minNumPick.setWrapSelectorWheel(true);

        // Initialize start time
        hour = 1;
        min = 0;
        ampm = 1;


        //Set a value change listener for hourNumPick
        hourNumPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = newVal;
            }
        });

        //Set a value change listener for minNumPick
        minNumPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = newVal;
            }
        });

        //Set a value change listener for minNumPick
        amPmPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                ampm = newVal;                       // 1 = AM, 2 = PM
            }
        });

        // Check to see if time entered is valid before continuing (after current time)
        nextButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hourEntered, minEntered;
                hourEntered = hour;
                minEntered = min;

                if (hour == 12 && ampm == 1) {
                    hourEntered = 0;
                }

                if ((ampm == 2) && (hour != 12))
                    hourEntered += 12;

                Date date = new Date();                               // given date
                Calendar calendar = GregorianCalendar.getInstance();  // creates a new calendar instance
                calendar.setTime(date);                               // assigns calendar to given date
                curHour = calendar.get(Calendar.HOUR_OF_DAY);         // gets hour in 24h format
                curMin = calendar.get(Calendar.MINUTE);              // get cur minute

                System.out.println("Current Time: " + curHour + ":" + curMin);
                System.out.println("Entered Time: " + hourEntered + ":" + minEntered);

                if (hourEntered < curHour) {
                    AlertDialog.Builder invalidTimeAlert = new AlertDialog.Builder(Clockin.this);
                    invalidTimeAlert.setTitle("Invalid Time");
                    invalidTimeAlert.setMessage(
                            "Arrival time may not be before the current time.");
                    invalidTimeAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = invalidTimeAlert.create();
                    alertDialog.show();
                } else if ((hourEntered == curHour) && (minEntered < curMin)) {
                    AlertDialog.Builder invalidTimeAlert = new AlertDialog.Builder(Clockin.this);
                    invalidTimeAlert.setTitle("Invalid Time");
                    invalidTimeAlert.setMessage(
                            "Arrival time may not be before the current time.");
                    invalidTimeAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = invalidTimeAlert.create();
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(Clockin.this, ChooseDepartureTimeActivity.class);
                    intent.putExtra("arriveHour", hourEntered);
                    intent.putExtra("arriveMin", minEntered);
                    intent.putExtra("Username", bundle.getString("Username"));
                    startActivity(intent);
                }

            }
            //@Override
            //public void onClick(View v) {
            //Intent intent = new Intent(Clockin.this, Payment.class);
            //startActivity(intent);


            //}
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(Clockin.this);
                hlp.setTitle("Help Information");
                hlp.setMessage(
                        "Please choose the time you expect to arrive. " +
                                "Arrival time may not be before the current time." +
                                "Press next once you are finished.");
                hlp.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });


        homeButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clockin.this, UserHomepage.class);
                intent.putExtra("Username", bundle.getString("Username"));
                startActivity(intent);

            }
        });


    }

    /*public void showDialog(View v) {

                AlertDialog.Builder altial = new AlertDialog.Builder(Clockin.this);
                altial.setMessage("$2.50/hr").setCancelable(false)
                        .setPositiveButton("Okay, charge me!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Clockin.this, Payment.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altial.create();
                alert.setTitle("Current Rate:");
                alert.show();




        }*/
}
