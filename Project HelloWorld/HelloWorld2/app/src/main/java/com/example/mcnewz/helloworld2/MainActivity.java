package com.example.mcnewz.helloworld2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvHello;
    EditText editTextHello;
    Button btnCopy;
    EditText editText1;
    EditText editText2;
    private TextView tvResult;
    private Button btnCalculate;
    private RadioGroup rgOperator;
    private CustomViewGroup viewGroup2;
    private CustomViewGroup viewGroup1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set Screen Portrait for mobile
        if(getResources().getBoolean(R.bool.portrait_only)){
            // Fix screen orientation to Portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initInstances();
    }

    private void initInstances() {
        tvHello = (TextView) findViewById(R.id.tvHello);
        tvHello.setMovementMethod((LinkMovementMethod.getInstance()));
        tvHello.setText(Html.fromHtml("<b>He<u>llo</b> <i>World</i> <font color=\"#aaaaff\">La la la </font><a href=\"http://www.gooogle.com\">google.com</a>"));

        // press Don on keybord
        editTextHello = (EditText) findViewById(R.id.editTextHello);
        editTextHello.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE){
                    // Copy text in EditText to TextView
                    tvHello.setText(editTextHello.getText());
                    return true;
                }
                return false;
            }
        });

        // Press Button
        btnCopy = (Button) findViewById(R.id.btnCopy);
        btnCopy.setOnClickListener(this);
//        btnCopy.setOnClickListener(listener);

        ///////////////////////
        // Start Here
        //////////////////////

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);

        rgOperator = (RadioGroup) findViewById(R.id.rgOperator);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sum = 0;
                int val1 = 0;
                int val2 = 0;

                try {
                    val1 = Integer.parseInt(editText1.getText().toString());
                }catch (NumberFormatException e ){
                }
                try {
                    val2 = Integer.parseInt(editText2.getText().toString());
                }catch (NumberFormatException e){
                }
                //Check Operator
                switch (rgOperator.getCheckedRadioButtonId()){
                    case R.id.rbPlus:
                        sum = val1 + val2;
                        break;
                    case R.id.rbMinus:
                        sum = val1 - val2;
                        break;
                    case R.id.rbMultiply:
                        sum = val1 * val2;
                        break;
                    case R.id.rbDivide:
                        sum = val1 / val2;
                        break;
                    // Add case Here .. . . ..
                }

                // Show the result
                tvResult.setText(String.valueOf(sum));

                // Log cat
                Log.d("calculationxxx", "Result = " + sum);
                Toast.makeText(MainActivity.this, "Result ="+ sum, Toast.LENGTH_SHORT).show();

                // Open  Second Activity
                Intent intent = new Intent(MainActivity.this,
                        SecondActivity.class);

                intent.putExtra("result", sum);

                // Send Object attrah to Intent
                // 1.Playground
                Coordinate c1 = new Coordinate();
                c1.x = 5;
                c1.y = 10;
                c1.z = 20;

                Bundle bundle = new Bundle();
                bundle.putInt("x", c1.x);
                bundle.putInt("y", c1.y);
                bundle.putInt("z", c1.z);
                intent.putExtra("cBundle", bundle);

                // 2.Serializable Lab
                CoordinateSerializable c2 = new CoordinateSerializable();
                c2.x = 5;
                c2.y = 10;
                c2.z = 20;
                intent.putExtra("cSerializable", c2);

                // 3.Parcelable
                CoordinateParcelable c3 = new CoordinateParcelable();
                c3.x = 5;
                c3.y = 10;
                c3.z = 20;
                intent.putExtra("cParcelable", c3);

                // Start Activity
                startActivityForResult(intent, 12345);

                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        // Custom View Group.  Create xml >> inflate in CustomViewGroup >> use in a xml Main >> use here
        viewGroup1 = (CustomViewGroup) findViewById(R.id.viewGroup1);
        viewGroup2 = (CustomViewGroup) findViewById(R.id.viewGroup2);

        viewGroup1.setButtonText("Hello");
        viewGroup2.setButtonText("WORLD");


    }

    // Result from second Activity  call back
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if it is result from SecondActivity
        if (requestCode == 12345){
            if (resultCode == RESULT_OK){
                // Get data from data's extra
                String result = data.getStringExtra("result");
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //    Handle OnClickListenner
    @Override
    public void onClick(View v) {
        if (v == btnCopy){

        }
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


//    Menu Button Show Here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    Handle click Menu Here
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings){
            // Do What you want
            Toast.makeText(this, "Settings Click ", Toast.LENGTH_SHORT).show();
            //Handled
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    // On Save Instance State (Save Data Bundle)
    // 1 parameter
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Things here
    }
    // 1 parameter
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore things here
    }

//
//    // onsave 2 Parameter Save Persistent
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//    }
//    // onsave 2 Parameter Save Persistent
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onRestoreInstanceState(savedInstanceState, persistentState);
//    }
}
