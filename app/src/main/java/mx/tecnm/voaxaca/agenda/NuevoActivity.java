package mx.tecnm.voaxaca.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.tecnm.voaxaca.agenda.db.DbContactos;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNumeroControl, txtNombre, txtCarrera, txtSemestre, txtGrupo;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNumeroControl = findViewById(R.id.txtNumeroControl);
        txtNombre = findViewById(R.id.txtNombre);
        txtCarrera = findViewById(R.id.txtCarrera);
        txtSemestre = findViewById(R.id.txtSemestre);
        txtGrupo = findViewById(R.id.txtGrupo);
        btnGuardar = findViewById(R.id.btnGuardar);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                long id = dbContactos.insertarContacto(txtNumeroControl.getText().toString(),txtNombre.getText().toString(), txtCarrera.getText().toString(), txtSemestre.getText().toString(), txtGrupo.getText().toString());

                if(id > 0){
                    Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                    verPrincipal();
                } else {
                    Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR EL REGISTRO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar(){
        txtNumeroControl.setText("");
        txtNombre.setText("");
        txtCarrera.setText("");
        txtSemestre.setText("");
        txtGrupo.setText("");
    }

    private void verPrincipal(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}