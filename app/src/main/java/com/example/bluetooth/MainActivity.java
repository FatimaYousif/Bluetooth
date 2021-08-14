package com.example.bluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

   private static final int REQUEST_ENABLE_BT=0;
   private static final int REQUEST_DISCOVER_BT=1;


    TextView paired;
    Button turnOn, turnOff, discoverable, pairedDevices;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        paired=(TextView)findViewById(R.id.paired);
        turnOn=(Button) findViewById(R.id.turn_on);
        turnOff=(Button)findViewById(R.id.turn_off);
        discoverable=(Button)findViewById(R.id.discoverable);
        pairedDevices=(Button)findViewById(R.id.paired_devices);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        //turn on BTN
        turnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! bluetoothAdapter.isEnabled())
                {
                   showToast("Bluetooth is turning on...");
                   Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                }
             showToast("Bluetooth is ALREADY on");
            }
        });

        //turn off BTN
        turnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bluetoothAdapter.isEnabled())
                {
                    bluetoothAdapter.disable();
                    showToast("turning Bluetooth OFF");
                }
                else
                {
                    showToast("Bluetooth is ALREADY off");
                }


            }
        });

        //discover BTN
        discoverable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! bluetoothAdapter.isDiscovering())
                {
                    showToast("making your device discoverable");
                    Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
                else{
                    showToast("Bluetooth is ALREADY on");
                }
            }
        });

        //paired devices BTN
        pairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetoothAdapter.isEnabled())
                {
                   Set<BluetoothDevice> devices=bluetoothAdapter.getBondedDevices();
                   for(BluetoothDevice device: devices)
                   {
                        paired.append("\ndevice: "+ device.getName() +","+device);
                   }
                }
                else
                {
                    showToast("Turn on Bluetooth to get paired devices");
                }
            }
        });
    }

    //on activity for result method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        switch(requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode==RESULT_OK)
                {
                    showToast("bluetooth is on");
                }
                else
                {
                    showToast("couldnt turn on bluetooth");
                }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    private void showToast (String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}