package com.example.banco;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class transacciones extends AppCompatActivity {
    clsBank sohBank = new clsBank(this, "dbBank", null, 4);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);

        TextView tacount = findViewById(R.id.etacount);
        TextView balance = findViewById(R.id.tvbalance);
        EditText date = findViewById(R.id.etdate);
        EditText hour = findViewById(R.id.ethour);
        EditText value = findViewById(R.id.etvalue);
        RadioButton rbretiro = findViewById(R.id.rbretiro);
        RadioButton rbconsig = findViewById(R.id.rbconsig);
        Button btntrans = findViewById(R.id.btntransaction);
        ImageButton btnexit = findViewById(R.id.btnexit);


        //recibir y mostrar los datos enviados desde MainActivity (midclient)
        tacount.setText(getIntent().getStringExtra("tnumacount"));
        balance.setText(getIntent().getStringExtra("tbalance"));

        //******************** eventos ***************************
        btntrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!date.getText().toString().isEmpty()
                        && !hour.getText().toString().isEmpty()
                        && !value.getText().toString().isEmpty()){
                    SQLiteDatabase dbt = sohBank.getWritableDatabase();

                    int tvalue = parseInt(value.getText().toString());
                    int tbalance = parseInt(balance.getText().toString());
                    int min = tbalance - 10000;
                    ContentValues cvTrans = new ContentValues();
                    cvTrans.put("numacount", tacount.getText().toString());
                    cvTrans.put("dateacount", date.getText().toString());
                    cvTrans.put("hour", hour.getText().toString());
                    cvTrans.put("value", value.getText().toString());
                    if (rbretiro.isChecked()){
                        if (tvalue > 0 && tvalue < min){
                            //cvTrans.put("tipetrans", rbretiro.isChecked());
                            //guardar la trnasaccion en la tabla transaction1
                            dbt.insert("transaction1", null, cvTrans);
                            int ttvalue = parseInt(value.getText().toString());
                            dbt.execSQL("UPDATE acount SET balance = balance +'" + (-ttvalue) + "' WHERE numacount = '" + tacount.getText().toString() + "'");
                            dbt.close();
                            Toast.makeText(getApplicationContext(), "transaccion realizada correctamente ",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "el saldo supera al permitido a retirar ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else if (rbconsig.isChecked() && tvalue >= 1000000 && tvalue <= 50000000){
                            //cvTrans.put("tipetrans", rbconsig.isChecked());
                            //guardar la transaccion en la tabla transaction1
                            dbt.insert("transaction1", null, cvTrans);
                            int ttvalue = parseInt(value.getText().toString());
                            dbt.execSQL("UPDATE acount SET balance = balance +'" + ttvalue + "' WHERE numacount = '" + tacount.getText().toString() + "'");
                            dbt.close();
                        Toast.makeText(getApplicationContext(), "transaccion realizada correctamente ",
                                Toast.LENGTH_SHORT).show();

                    }
                        else{
                        Toast.makeText(getApplicationContext(),
                                      "el saldo para consignar\n " +
                                            "debe de estar entre \n" +
                                           "1 millon  y los 50 millones", Toast.LENGTH_SHORT).show();
                        }
                }
                else{
                    Toast.makeText(getApplicationContext(), "debe de ingresar todos los datos ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instruccion para cerrar la activity y vuelva a la principal
                finish();
            }
        });

    }
}