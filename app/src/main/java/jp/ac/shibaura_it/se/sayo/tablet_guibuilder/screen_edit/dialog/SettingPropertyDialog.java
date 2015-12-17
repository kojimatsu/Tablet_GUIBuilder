package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.GUIInformationManager;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLWriting;

/**
 * Created by matsu on 2015/12/10.
 */
public class SettingPropertyDialog extends DialogFragment {

    private EditText label;
    private EditText roll;

    public static SettingPropertyDialog newInstance(){
        return new SettingPropertyDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("プロパティを設定してください");
        builder.setNegativeButton("OK", onCancelListener);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        int uniqueID = getArguments().getInt(XMLWriting.ATTRIBUTE_ID);
        label = getEditText("ラベル");
        roll = getEditText("ID");

        ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();
        GUIInformationManager guiInformationManager = GUIInformationManager.newInstance();
        String labelStr = guiInformationManager.getLabel(uniqueID);
        String rollStr = shareInformationManager.getRoll(uniqueID);

        if (labelStr != null){
            label.setText(labelStr);
        }
        if (rollStr != null){
            roll.setText(rollStr);
        }
        linearLayout.addView(label);
        linearLayout.addView(roll);
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
            ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();
            GUIInformationManager guiInformationManager = GUIInformationManager.newInstance();
            int uniqueID = getArguments().getInt(XMLWriting.ATTRIBUTE_ID);
            OutputWidget outputWidget = shareInformationManager.getOutputWidget(uniqueID);
            String labelStr = label.getText().toString();
            String rollStr = roll.getText().toString();
            View view = outputWidget.getVisualView();
            if (view instanceof TextView){
                TextView textView = (TextView) view;
                textView.setText(labelStr);
                if ( !labelStr.equals("") ){
                    guiInformationManager.writeLabel(uniqueID, labelStr);
                }
                if ( !rollStr.equals("") ){
                    shareInformationManager.writeRoll(uniqueID, rollStr);
                }
            }
            dialog.dismiss();
        }
    };

}
