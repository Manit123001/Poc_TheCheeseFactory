package com.mcnew.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    int sum = 0;
    TextView tvResult;
    Button btnOk;
    EditText editTextHola;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        sum = intent.getIntExtra("result", 0);

        Bundle bundle;
        bundle = intent.getBundleExtra("cBundle");

        int x = bundle.getInt("x");
        int z = bundle.getInt("y");
        int y = bundle.getInt("z");

        CoordinateSerializable c2 = (CoordinateSerializable)
                intent.getSerializableExtra("cSerializable");

        CoordinateParcelable c3 = intent.getParcelableExtra("sParcelable");

        initinstances();
    }


    private void initinstances() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText("Result = "+ sum);

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
