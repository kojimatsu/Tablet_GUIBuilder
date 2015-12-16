package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenEditActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.Gesture;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;

/**
 * Created by matsu on 2015/12/10.
 */
public class SettingPropertyDialog extends DialogFragment {

    public static SettingPropertyDialog newInstance(){
        return new SettingPropertyDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("プロパティを設定してください");
        builder.setNegativeButton("OK", onCancelListener);

        // アダプタ設定
        ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(getEditText("ラベル"));
        linearLayout.addView(getEditText("ID"));
        builder.setView(linearLayout);
        return builder.create();
    }
    private EditText getEditText(String hint){
        EditText editText = new EditText(getActivity());
        editText.setHint(hint);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(params);
        return editText;
    }


    // OKボタンが押されたとき
    private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    };

}
