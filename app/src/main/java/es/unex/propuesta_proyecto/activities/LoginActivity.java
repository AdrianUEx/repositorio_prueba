package es.unex.propuesta_proyecto.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;//Los Toast deben ser lanzados desde el hilo de la Activity, no en un hilo secundario
import es.unex.propuesta_proyecto.R;
import es.unex.propuesta_proyecto.api.AppExecutors;
import es.unex.propuesta_proyecto.dao.AppDatabaseUsuarios;
import es.unex.propuesta_proyecto.model.Usuarios;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        username = findViewById(R.id.etUsuarioLogin);
        password = findViewById(R.id.etContraseñaLogin);
        btnLogin = findViewById(R.id.bIniciarSesion);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this,"Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                else{
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Usuarios usuario;
                            usuario = AppDatabaseUsuarios.getInstance(getApplicationContext()).daoUsuarios().comprobarUsuario(user);
                            if(usuario != null){
                                if(usuario.getName().equals(user) && usuario.getPassword().equals(pass)){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "Ha iniciado sesión!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Intent intent = new Intent(getApplicationContext(),ClasesActivity.class);
                                    intent.putExtra("estado",true);
                                    intent.putExtra("usuario",user);
                                    startActivity(intent);
                                } else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this,"Credenciales incorrectas!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this,"No existe usuario!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }//Fin run() del onClick()
                    });
                }
            }//Fin onClick()
        });
    }//Fin onCreate()

    public void crearCuenta(View view){
        Intent actCrear = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(actCrear);
    }

    public void entrarSinCuenta(View view){
        Intent actClassSinCuenta = new Intent(getApplicationContext(), ClasesActivity.class);
        actClassSinCuenta.putExtra("estado",false);
        startActivity(actClassSinCuenta);
    }
}