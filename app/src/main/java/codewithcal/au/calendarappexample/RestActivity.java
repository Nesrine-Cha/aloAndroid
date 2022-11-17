package codewithcal.au.calendarappexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RestActivity extends AppCompatActivity {
TextView username;
EditText pass,repass;
Button confirm;
DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
            username=(TextView)findViewById(R.id.username_reset_text);
        pass=(EditText) findViewById(R.id.password_reset);
        repass=(EditText) findViewById(R.id.repassword_reset);
        confirm = (Button)findViewById(R.id.btnconform);
        DB =new DBHelper(this);
        Intent intent= getIntent();
        username.setText(intent.getStringExtra("username"));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user =username.getText().toString();
                String password=pass.getText().toString();
                String repassword=repass.getText().toString();
                if (password.equals(repassword)){
                Boolean checkpasswordupdate=DB.updatepassword(user,password);
                if(checkpasswordupdate==true){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                    Toast.makeText(RestActivity.this,"Password Updates successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RestActivity.this,"Password Not Updates ",Toast.LENGTH_SHORT).show();

                }}else {
                    Toast.makeText(RestActivity.this,"Password Not Matching ",Toast.LENGTH_SHORT).show();


                }

            }
        });
    }
}