package mx.tecnm.voaxaca.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mx.tecnm.voaxaca.agenda.db.DbContactos;
import mx.tecnm.voaxaca.agenda.entidades.Contactos;

public class EditarActivity extends AppCompatActivity {

    EditText txtNumeroControl, txtNombre, txtCarrera, txtSemestre, txtGrupo;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;

    Contactos contacto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNumeroControl = findViewById(R.id.txtNumeroControl);
        txtNombre = findViewById(R.id.txtNombre);
        txtCarrera = findViewById(R.id.txtCarrera);
        txtSemestre = findViewById(R.id.txtSemestre);
        txtGrupo = findViewById(R.id.txtGrupo);
        btnGuarda = findViewById(R.id.btnGuardar);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null){
            txtNumeroControl.setText(contacto.getNumero_control());
            txtNombre.setText(contacto.getNombre());
            txtCarrera.setText(contacto.getCarrera());
            txtSemestre.setText(contacto.getSemestre());
            txtGrupo.setText(contacto.getGrupo());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNumeroControl.getText().toString().equals("") && !txtNombre.getText().toString().equals("") && !txtCarrera.getText().toString().equals("") && !txtSemestre.getText().toString().equals("") && !txtGrupo.getText().toString().equals("")){
                    correcto = dbContactos.editarContacto(id, txtNumeroControl.getText().toString(),txtNombre.getText().toString(), txtCarrera.getText().toString(), txtSemestre.getText().toString(), txtGrupo.getText().toString());

                    if (correcto){
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR EL REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarActivity.this, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}