package com.example.mcnewz.helloworld2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    int sum = 0;
    int x1 = 0;
    private TextView tvResult;
    private Button btnOk;
    private TextView editTextHola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //get Intent here
        Intent intent = getIntent();

        // 0. get Extra Data
        sum = intent.getIntExtra("result", 0);

        // 1. get Bundle Extra is use save data
        Bundle bundle = intent.getBundleExtra("cBundle");
        // use Bundle Here && other same use
        int x = bundle.getInt("x");
        int z = bundle.getInt("y");
        int y = bundle.getInt("z");

        // 2. get Serializable (It's slowly for mobile not use )
        CoordinateSerializable c2 = (CoordinateSerializable)intent.getSerializableExtra("cSerializable");
        x1 = c2.x;

        // 3. get Parcelable Container for a message
        // Container Good for data structure in any complex level (data for scale is good)
        CoordinateParcelable c3 = intent.getParcelableExtra("sParcelable");

        initInstances();
    }

    private void initInstances() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText("Result = "+ sum+"sss"+ x1);

        editTextHola = (EditText) findViewById(R.id.editTextHola);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", editTextHola.getText().toString());
                setResult(RESULT_OK, returnIntent);

                finish();

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}
