package yakovskij.lab5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.MyName);
    }
    @Override
    protected void onActivityResult(int requestCode, int result, @Nullable Intent data) {

        if (requestCode == 1 && data != null) {
            String s = data.getStringExtra("newn");
            boolean s1 = data.getBooleanExtra("news1", false);
            boolean s2 = data.getBooleanExtra("news2", false);
            CheckBox a = findViewById(R.id.checkBox);
            CheckBox b = findViewById(R.id.checkBox2);
            txt.setText(s);
            a.setChecked(s1);
            b.setChecked(s2);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, result, data);
    }
    public void dialog_click(View v){
        Intent i = new Intent(this, SecActivity.class);
        i.putExtra("myname", txt.getText().toString());
        CheckBox a = findViewById(R.id.checkBox);
        CheckBox b = findViewById(R.id.checkBox2);
        i.putExtra("boolch1", a.isChecked());
        i.putExtra("boolch2", b.isChecked());
        startActivityForResult(i, 1);
        txt.setText(i.getStringExtra("newn"));
    }

    public void ok_cancel_click(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}