package com.example.banco;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class cuentas extends AppCompatActivity {
    clsBank sohBank = new clsBank(this, "dbBank", null, 4);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);

        TextView idclientc = findViewById(R.id.etidclientc);
        EditText acount = findViewById(R.id.etacount);
        EditText date = findViewById(R.id.etdate);
        TextView balance = findViewById(R.id.etbalance);
        //botones
        ImageButton btncreate = findViewById(R.id.btncreatec);
        ImageButton btntransaction = findViewById(R.id.btntransaction);
        ImageButton btnlogout = findViewById(R.id.btnlogout);

        //recibir y mostrar los datos enviados desde MainActivity (midclient)
        idclientc.setText(getIntent().getStringExtra("midclient"));

        //******************** eventos ***************************

        //boton de transacciones
        btntransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase dbB = sohBank.getReadableDatabase();

                //generar una variable que contenga la instrucción para la busqueda por idclient
                String query = "SELECT numacount, balance FROM acount WHERE numacount = '" + acount.getText().toString() + "'";
                //Generar tabla cursor
                Cursor cursorSerch = dbB.rawQuery(query, null);
                //Chequear si la tabla tiene al menos un registro
                if (!acount.getText().toString().isEmpty()) {
                    if (cursorSerch.moveToFirst()) {//(si no lo encuentra)
                        balance.setText(cursorSerch.getString(1));
                        //generar un objeto bajasado en la clase intent para cambiar de antivity
                        //pasar el numero de cuenta para la actividad de transacciones (transacciones)
                        Intent itransf = new Intent(getApplicationContext(), transacciones.class);
                        itransf.putExtra("tnumacount", acount.getText().toString());
                        itransf.putExtra("tbalance", balance.getText().toString());
                        startActivity(itransf);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Nro de cuenta no existe \n intente de nuevo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Ingrese el numero de cuenta ", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //boton crear cuenta
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!acount.getText().toString().isEmpty()
                        && !date.getText().toString().isEmpty()
                        //&& !balance.getText().toString().isEmpty()
                ) {

                    SQLiteDatabase dbBank1 = sohBank.getReadableDatabase();

                    //generar una variable que contenga la instrucción para la busqueda por idseller
                    String query = "SELECT numacount FROM acount WHERE numacount = '" + acount.getText().toString() + "'";
                    //Generar tabla cursor
                    Cursor cursorSerch = dbBank1.rawQuery(query, null);
                    //Chequear si la tabla tiene al menos un registro
                    //double nbalance = parseDouble(balance.getText().toString());
                    //if (nbalance >= 1000000 && nbalance <= 50000000){
                        if (!cursorSerch.moveToFirst()) {//(si no lo encuentra)
                            // generar un objeto de SQLiteDataBase en modo escritura
                            SQLiteDatabase dbBankw = sohBank.getWritableDatabase();
                            //crear un contentValues
                            ContentValues cvAcount = new ContentValues();
                            //Asignar a cada campo del contenedor su referencia respectiva
                            cvAcount.put("numacount", acount.getText().toString());
                            cvAcount.put("idclient", idclientc.getText().toString());
                            cvAcount.put("dateacount", date.getText().toString());
                            cvAcount.put("balance", 0);
                            //Guardar los datos del contetValues en la tabla física
                            dbBankw.insert("acount", null, cvAcount);
                            dbBankw.close();
                            //dbSalesr.close();
                            Toast.makeText(getApplicationContext(),
                                    "cuenta creado correctamemte...", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "cuenta ya registrada a otro usuario \n " +
                                            "verifique por favor", Toast.LENGTH_SHORT).show();
                        }
                        dbBank1.close();
                    }
                    else {
                        //Toast.makeText(getApplicationContext(),
                          //      "el saldo para crear cuenta\n " +
                            //            "debe de estar entre \n" +
                             //           "1 millon  y los 50 millones", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),
                            "Debe ingresar todos los datos...", Toast.LENGTH_SHORT).show();
                    }
                //}
                //else{
                    //Toast.makeText(getApplicationContext(),
                    //        "Debe ingresar todos los datos...", Toast.LENGTH_SHORT).show();
                //}
            }
        });

        //boton logout
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instruccion para cerrar la activity y vuelva a la principal
                finish();
            }
        });


    }
}