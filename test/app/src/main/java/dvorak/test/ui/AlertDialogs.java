package dvorak.test.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import dvorak.test.chatlist;

public class AlertDialogs {

    public static void make(chatlist CL){
        AlertDialog alertDialog = new AlertDialog.Builder(CL.getContext()).create();
        alertDialog.setTitle("New Message");

        final EditText input = new EditText(CL.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(input);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
