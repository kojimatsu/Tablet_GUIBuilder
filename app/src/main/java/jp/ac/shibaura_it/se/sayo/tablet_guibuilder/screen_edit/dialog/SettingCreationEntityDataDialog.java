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

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.CRUD;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.GUIInformationManager;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLWriting;

/**
 * Created by 浩司 on 2015/12/21.
 */
public class SettingCreationEntityDataDialog extends DialogFragment{

    private EditText entityDataName;

    private int uniqueID;
    private String gestureName;
    private CRUD crud;

    public static SettingCreationEntityDataDialog newInstance(){
        return new SettingCreationEntityDataDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("EntityDataを設定してください");
        builder.setNegativeButton("OK", onCancelListener);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Bundle bundle = getArguments();
        uniqueID = bundle.getInt(XMLWriting.ATTRIBUTE_ID);
        gestureName = bundle.getString(ShareInformationManager.ATTRIBUTE_NAME);
        crud = (CRUD) bundle.getSerializable(CRUD.class.getName());
        ShareInformationManager manager = ShareInformationManager.newInstance();
        String dataName = manager.getEntityDataName(uniqueID, gestureName, crud);
        entityDataName = getEditText("EntityData名");
        linearLayout.addView(entityDataName);
        if (dataName != null){
            entityDataName.setText(dataName);
        }
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
            String dataName = entityDataName.getText().toString();
            ShareInformationManager manager = ShareInformationManager.newInstance();
            manager.writeEntityData(uniqueID,gestureName,crud,dataName);
            dialog.dismiss();
        }
    };
}
