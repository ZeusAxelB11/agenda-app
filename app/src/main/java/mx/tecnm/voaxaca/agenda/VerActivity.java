package mx.tecnm.voaxaca.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mx.tecnm.voaxaca.agenda.db.DbContactos;
import mx.tecnm.voaxaca.agenda.entidades.Contactos;

public class VerActivity extends AppCompatActivity {

    EditText txtNumeroControl, txtNombre, txtCarrera, txtSemestre, txtGrupo;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;

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
        fabEliminar = findViewById(R.id.fabEliminar);

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

        DbContactos dbContactos = new DbContactos(VerActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null){
            txtNumeroControl.setText(contacto.getNumero_control());
            txtNombre.setText(contacto.getNombre());
            txtCarrera.setText(contacto.getCarrera());
            txtSemestre.setText(contacto.getSemestre());
            txtGrupo.setText(contacto.getGrupo());
            btnGuarda.setVisibility(View.INVISIBLE);
            txtNumeroControl.setInputType(InputType.TYPE_NULL);
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtCarrera.setInputType(InputType.TYPE_NULL);
            txtSemestre.setInputType(InputType.TYPE_NULL);
            txtGrupo.setInputType(InputType.TYPE_NULL);
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (dbContactos.eliminarContacto(id)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    private void lista(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}