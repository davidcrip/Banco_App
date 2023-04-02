package com.example.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    clsBank sohBank = new clsBank(this, "dbBank", null, 4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText idclient = findViewById(R.id.etidclient);
        EditText fullname = findViewById(R.id.etfullname);
        EditText email = findViewById(R.id.etemail);
        EditText password =findViewById(R.id.etpassword);
        //botones
        ImageButton btncreate = findViewById(R.id.btncreate);
        ImageButton btnlogin = findViewById(R.id.btnlogin);


        //eventos

        //boton de login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase dbB = sohBank.getReadableDatabase();

                //generar una variable que contenga la instrucción para la busqueda por idclient
                String query = "SELECT idclient FROM client WHERE idclient = '" + idclient.getText().toString() + "'";
                String query1 = "SELECT password FROM client WHERE password = '" + password.getText().toString() + "'";
                //Generar tabla cursor
                Cursor cursorSerch = dbB.rawQuery(query, null);
                Cursor cursorSerch1 = dbB.rawQuery(query1, null);
                if (!idclient.getText().toString().isEmpty()
                        && !password.getText().toString().isEmpty()){
                    //Chequear si la tabla tiene al menos un registro
                    if (cursorSerch.moveToFirst() && cursorSerch1.moveToFirst()) {//(si no lo encuentra)
                        //generar un objeto bajasado en la clase intent para cambiar de activity
                        //startActivity(new Intent(getApplicationContext(),sales.class));
                        //pasar la identificación y el nombre para la actividad de ventas (sales)
                        Intent icuentas = new Intent(getApplicationContext(), cuentas.class);
                        icuentas.putExtra("midclient", idclient.getText().toString());
                        startActivity(icuentas);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "identificación o contraseña errada \n intente de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "ingrese identificación y contraseña", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // boton de guardar
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!idclient.getText().toString().isEmpty()
                        && !fullname.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty()
                        && !password.getText().toString().isEmpty()) {

                    SQLiteDatabase dbBank1 = sohBank.getReadableDatabase();

                    //generar una variable que contenga la instrucción para la busqueda por idseller
                    String query = "SELECT idclient FROM client WHERE idclient = '" + idclient.getText().toString() + "'";
                    //Generar tabla cursor
                    Cursor cursorSerch = dbBank1.rawQuery(query, null);
                    //Chequear si la tabla tiene al menos un registro
                    if (!cursorSerch.moveToFirst()) {//(si no lo encuentra)
                        // generar un objeto de SQLiteDataBase en modo escritura
                        SQLiteDatabase dbBankw = sohBank.getWritableDatabase();
                        //crear un contentValues
                        ContentValues cvClient = new ContentValues();
                        //Asignar a cada campo del contenedor su referencia respectiva
                        cvClient.put("idclient", idclient.getText().toString());
                        cvClient.put("fullname", fullname.getText().toString());
                        cvClient.put("email", email.getText().toString());
                        cvClient.put("password", password.getText().toString());
                        //Guardar los datos del contetValues en la tabla física
                        dbBankw.insert("client", null, cvClient);
                        dbBankw.close();
                        //dbSalesr.close();
                        Toast.makeText(getApplicationContext(),
                                "Clieonte creado correctamemte...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "identificación ya registrada a otro usuario \n verifique por favor", Toast.LENGTH_SHORT).show();

                    }
                    dbBank1.close();



                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Debe ingresar todos los datos...", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}