package firebase.arduino.com.arduinofirebase;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
        tvLED_STATUS  = (TextView)findViewById(R.id.tvLED_STATUS);
        btnOn = (Button)findViewById(R.id.btnOn);
        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("LED_STATUS");
                myRef.setValue(1);
                myRef = database.getReference("RED");
                myRef.setValue(RED);
                myRef = database.getReference("GREEN");
                myRef.setValue(GREEN);
                myRef = database.getReference("BLUE");
                myRef.setValue(BLUE);
                Log.e("LED_STATUS","1");
                Log.e(TAG+" RGB ","("+ RED + GREEN + BLUE +")");
                tvLED_STATUS.setText("LED IS ON");
                tvLED_STATUS.setTextColor(Color.GREEN);
            }
        });
        btnOff = (Button)findViewById(R.id.btnOff);
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS");
                myRef.setValue(0);
                Log.e("LED_STATUS","0");
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
                RED = Color.red(envelope.getColor());
                GREEN = Color.green(envelope.getColor());
                BLUE = Color.blue(envelope.getColor());
                tvSelectedColorCode.setText(" HexCode: "+envelope.getHexCode()+" \n RGB: "+"("+ RED +","+ GREEN +","+ BLUE +")");
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("LED_STATUS");
                myRef.setValue(1);
                myRef = database.getReference("RED");
                myRef.setValue(RED);
                myRef = database.getReference("GREEN");
                myRef.setValue(GREEN);
                myRef = database.getReference("BLUE");
                myRef.setValue(BLUE);
                Log.e("LED_STATUS","1");
                Log.e(TAG+" RGB ","("+ RED + GREEN + BLUE +")");
                tvLED_STATUS.setText("LED IS ON");
                tvLED_STATUS.setTextColor(Color.GREEN);
            }
        });
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }*/
}
