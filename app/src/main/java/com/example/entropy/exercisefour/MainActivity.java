package com.example.entropy.exercisefour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
Button start;
    Button stop;
    TextView textView;
BroadcastReceiver broadcastReceiver;
    SharedPreferences sharedPreferences;

    public static final String USER_PREF = "USER_PREF";
    public static final String USER_DATA = "USER_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        start=(Button) findViewById(R.id.start_button);
        stop=(Button) findViewById(R.id.stop_button);
        textView=(TextView) findViewById(R.id.tv);
        textView.setText(sharedPreferences.getString(USER_DATA,""));


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this,MyService.class));
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this,MyService.class));
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(USER_DATA, "");
                editor.commit();
               textView.setText(sharedPreferences.getString(USER_DATA,""));
            }
        });

        IntentFilter filter= new IntentFilter("intent_sent");


        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(MainActivity.this,"broadcast received",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(USER_DATA, sharedPreferences.getString(USER_DATA,"")+"\n"+intent.getStringExtra("data_to_send"));
                editor.commit();
                textView.setText(sharedPreferences.getString(USER_DATA,""));


            }
        };
        registerReceiver(broadcastReceiver,filter);
    }
}
