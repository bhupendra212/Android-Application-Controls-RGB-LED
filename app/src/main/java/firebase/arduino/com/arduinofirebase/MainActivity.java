package firebase.arduino.com.arduinofirebase;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skydoves.colorpickerview.ActionMode;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnOn, btnOff;
    private TextView tvLED_STATUS, tvSelectedColorCode;
    private ColorPickerView colorPickerView;
    private LinearLayout llSelectedColor;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private int RED, GREEN, BLUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the Firebase database instance
        database = FirebaseDatabase.getInstance();
        // Create the child in root database location
        myRef = database.getReference("LED_STATUS");
        tvLED_STATUS = (TextView) findViewById(R.id.tvLED_STATUS);
        btnOn = (Button) findViewById(R.id.btnOn);
        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write value to database
                // By sending 1 from app, it will turn ON the LED
                myRef.setValue(1);
                myRef = database.getReference("RED");
                myRef.setValue(RED);
                myRef = database.getReference("GREEN");
                myRef.setValue(GREEN);
                myRef = database.getReference("BLUE");
                myRef.setValue(BLUE);
                Log.e("LED_STATUS", "1");
                Log.e(TAG + " RGB ", "(" + RED + GREEN + BLUE + ")");
                tvLED_STATUS.setText("LED IS ON");
                tvLED_STATUS.setTextColor(Color.GREEN);
            }
        });
        btnOff = (Button) findViewById(R.id.btnOff);
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write value to database
                // By sending 0 from app, it will turn OFF the LED
                myRef.setValue(0);
                Log.e("LED_STATUS", "0");
                tvLED_STATUS.setText("LED IS OFF");
                tvLED_STATUS.setTextColor(Color.RED);
            }
        });
        llSelectedColor = (LinearLayout) findViewById(R.id.llSelectedColor);
        colorPickerView = (ColorPickerView) findViewById(R.id.colorPickerView);
        colorPickerView.setActionMode(ActionMode.ALWAYS); // the listener will be invoked always
        tvSelectedColorCode = (TextView) findViewById(R.id.tvSelectedColorCode);
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                llSelectedColor.setBackgroundColor(envelope.getColor());
                // Get Red, Green & Blue from ColorPickerView
                RED = Color.red(envelope.getColor());
                GREEN = Color.green(envelope.getColor());
                BLUE = Color.blue(envelope.getColor());
                tvSelectedColorCode.setText(" HexCode: " + envelope.getHexCode() + " \n RGB: " + "(" + RED + "," + GREEN + "," + BLUE + ")");
                // Turn ON the LED, and pass RGB value to the Firebase Realtime Database
                // Any change in value trigger the listeners, and LED will response accordingly
                myRef.setValue(1);
                myRef = database.getReference("RED");
                myRef.setValue(RED);
                myRef = database.getReference("GREEN");
                myRef.setValue(GREEN);
                myRef = database.getReference("BLUE");
                myRef.setValue(BLUE);
                Log.e("LED_STATUS", "1");
                Log.e(TAG + " RGB ", "(" + RED + GREEN + BLUE + ")");
                tvLED_STATUS.setText("LED IS ON");
                tvLED_STATUS.setTextColor(Color.GREEN);
            }
        });
    }
}
