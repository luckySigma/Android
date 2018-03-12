package com.anuradha.andriod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtBatteryStatus,txtBatteryPlug,txtBatteryHealth,txtBatteryVoltage,txtBatteryTemp,txtBatteryTech,txtBatteryLevel;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtBatteryStatus = (TextView)findViewById(R.id.textStatus);
        txtBatteryPlug = (TextView)findViewById(R.id.textPlug);
        txtBatteryHealth = (TextView)findViewById(R.id.textHealth);
        txtBatteryVoltage = (TextView)findViewById(R.id.textVoltage);
        txtBatteryTemp = (TextView)findViewById(R.id.textTemp);
        txtBatteryTech = (TextView)findViewById(R.id.textTech);
        txtBatteryLevel =(TextView)findViewById(R.id.textLevel);

        //Intent Filter
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Status
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                if (isCharging)
                    txtBatteryStatus.setText("Battery status: charging");
                else
                    txtBatteryStatus.setText("Battery status: Charged Full");

                //Power Plug
                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean isUSBCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB || chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

                if (isUSBCharge)
                    txtBatteryPlug.setText("Poer Source :USB");
                else
                    txtBatteryPlug.setText("Poer Source :AC");

                //Level
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryPct = (level/(float)scale)*100;
                txtBatteryLevel.setText("Battery level :" +batteryPct+"%");

                //Voltage
                int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,-1);
                txtBatteryVoltage.setText("Battery Voltage:"+voltage+"nV");

                //Temparature
                int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
                txtBatteryTemp.setText("Battery Temparature: "+temp+"C");

                //Technology
                String tech = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                txtBatteryTech.setText("Battery Technology :" +tech);

                //Health
                int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,-1);
                switch (health)
                {

                    case BatteryManager.BATTERY_HEALTH_COLD:
                        txtBatteryHealth.setText("Battery Helath :Cold");
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        txtBatteryHealth.setText("Battery Helath :DEAD");
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        txtBatteryHealth.setText("Battery Helath :GOOD");
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        txtBatteryHealth.setText("Battery Helath :Over Heat");
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        txtBatteryHealth.setText("Battery Helath :Over Voltage");
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        txtBatteryHealth.setText("Battery Helath :UnKnown");
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                        txtBatteryHealth.setText("Battery Helath :FAILURE");
                        break;
                    default:
                        break;
                }

            }
        };
        //call function
        MainActivity.this.registerReceiver(broadcastReceiver,intentFilter);

    }
}
