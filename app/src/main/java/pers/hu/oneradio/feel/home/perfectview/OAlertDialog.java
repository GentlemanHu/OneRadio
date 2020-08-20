package pers.hu.oneradio.feel.home.perfectview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pers.hu.oneradio.R;

public class OAlertDialog {
    private String m_Text;
    private Looper looper;
    public OAlertDialog(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请输入API地址：(结尾不要带/)");

// Set up the input
        final EditText input = new EditText(context);
        input.setText("https://musicapi.leanapp.cn");
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                System.out.println(m_Text+"<------input");
                Looper.myLooper().quit();
                dialog.dismiss();
            }
        });

        builder.show();
        try {
            Looper.loop();
        }catch (Exception e){
            System.out.println("error--->");
        }
    }

    public String getText(){
        return m_Text;
    }
}
