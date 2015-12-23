package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.CRUD;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.Gesture;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLWriting;

/**
 * Created by 浩司 on 2015/12/23.
 */
public class SettingRUDEntityDataDialog extends DialogFragment {

    private ListView listView;
    private int uniqueID;

    private List<String> itemlist = new ArrayList<String>();


    public static SettingRUDEntityDataDialog newInstance(){
        return new SettingRUDEntityDataDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ設定
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("EntityDataを選択してください");
        builder.setNegativeButton("CLOSE", onCancelListener);

        ShareInformationManager manager = ShareInformationManager.newInstance();
        uniqueID = getArguments().getInt(XMLWriting.ATTRIBUTE_ID);
        itemlist = manager.getSettableEntityDataNameList(uniqueID);
        if (itemlist.isEmpty()){
            itemlist.add("NO");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemlist);
        listView = new ListView(getActivity());
        listView.setAdapter(arrayAdapter);

        // リストの行がタップされたとき
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ShareInformationManager manager = ShareInformationManager.newInstance();
                String gestureName = getArguments().getString(ShareInformationManager.ATTRIBUTE_NAME);
                CRUD crud = (CRUD) getArguments().getSerializable(CRUD.class.getName());
                String dataName = (String) listView.getItemAtPosition(position);
                manager.writeEntityData(uniqueID, gestureName, crud, dataName);
                if (crud == CRUD.Read){
                    manager.setOutputWidgetText(uniqueID,dataName);
                }
                dismiss();
            }
        });

        builder.setView(listView);
        return builder.create();
    }

    // CLOSEボタンが押されたとき
    private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    };
}
