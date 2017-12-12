package com.thkoeln.paulo.hearme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginStart extends AppCompatActivity {

    String email;
    String password;
    EditText emailAdresseField;
    EditText passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_start);

        final Button login = (Button) findViewById(R.id.anmelden);
        final Intent start = new Intent(this, MapsActivity.class);

        final Button registration = (Button) findViewById(R.id.registrieren);
        final Intent registrationIntent = new Intent(this, Registration.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailAdresseField = (EditText) findViewById(R.id.email);
                passwordField = (EditText) findViewById(R.id.password);

                email = emailAdresseField.getText().toString();
                password = passwordField.getText().toString();

                if (validate()) {
                    startActivity(start);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Anmeldung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(registrationIntent);
            }
        });


    }

    public boolean validate(){
        boolean validate = true;

        System.out.println("passwort " + password.length());

        if ((email.isEmpty()) || (!Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            emailAdresseField.setError("Kein gültiges Format!");
            validate = false;
        }
        if (password.toString().length() < 6 || password.length() > 10){
            passwordField.setError("Das Passwort muss aus 6 bis 10 Zeichen bestehen!");
            validate = false;
        }
        return validate;
    }



}
