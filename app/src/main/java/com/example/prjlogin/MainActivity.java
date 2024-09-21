package com.example.prjlogin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editSenhaJ, editUserJ, editNewUserJ, editNewPassJ;
    TextView txtSaida;
    View novoUsuario;
    SQLiteDatabase BancoSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        novoUsuario = findViewById(R.id.fundo);

        editUserJ = findViewById(R.id.editUser);
        editSenhaJ = findViewById(R.id.txtPassword);
        editNewUserJ = findViewById(R.id.editNewUser);
        editNewPassJ = findViewById(R.id.editNewPassword);
        txtSaida = findViewById(R.id.txtOut);

        setTitle("Login");

        BancoSQLite = openOrCreateDatabase("AulaDB", Context.MODE_PRIVATE, null);

        BancoSQLite.execSQL("CREATE TABLE IF NOT EXISTS tblUsuario (Id INTEGER PRIMARY KEY AUTOINCREMENT, login VARCHAR, senha VARCHAR);");

    }

    public void acesso(View v)
    {

        String entUsuario, entSenha;
        entUsuario = editUserJ.getText().toString();
        entSenha = editSenhaJ.getText().toString();
        Cursor c2 = BancoSQLite.rawQuery("SELECT * FROM tblUsuario where login = '"+entUsuario+"' and senha='"+entSenha+"'",null);

        if(c2.moveToNext())
        {
            Intent intensao = new Intent(MainActivity.this, Screen2.class);
            startActivity(intensao);
            finish();
            Toast.makeText(this, "Acertouu", Toast.LENGTH_SHORT).show();
        }
        else if(entUsuario.equalsIgnoreCase("admin") && entSenha.equalsIgnoreCase("a1234"))
        {
            novoUsuario.setVisibility(View.VISIBLE);
        }
        else
        {
            txtSaida.setText("Usuário ou senha errado");
        }

    }

    public void insert(View v)
    {

        if(editNewUserJ.getText().toString().isEmpty() || editNewPassJ.getText().toString().isEmpty())
        {
            txtSaida.setText("Preencha os campos");
        }
        else
        {
            BancoSQLite.execSQL("INSERT INTO tblUsuario(login, senha) VALUES ('"+editNewUserJ.getText()+"', '"+editNewPassJ.getText()+"');");

            Toast.makeText(this, "Dados Salvos", Toast.LENGTH_SHORT).show();

            editNewUserJ.setText("");
            editNewPassJ.setText("");
            editUserJ.setText("");
            editSenhaJ.setText("");
        }

    }
}