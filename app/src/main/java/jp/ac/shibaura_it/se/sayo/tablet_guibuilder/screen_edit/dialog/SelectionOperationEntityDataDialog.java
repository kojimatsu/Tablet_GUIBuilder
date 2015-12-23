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
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.WidgetType;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLWriting;

/**
 * Created by 浩司 on 2015/12/21.
 */
public class SelectionOperationEntityDataDialog extends DialogFragment{

    private List<String> itemlist = new ArrayList<String>();


    public static SelectionOperationEntityDataDialog newInstance(){
        return new SelectionOperationEntityDataDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("EntityDataに対するCRUDを設定してください");
        builder.setNegativeButton("CLOSE", onCancelListener);

        int uniqueID = getArguments().getInt(XMLWriting.ATTRIBUTE_ID);
        ShareInformationManager manager = ShareInformationManager.newInstance().newInstance();
        if (manager.getWidgetType(uniqueID) == WidgetType.INPUT){
            itemlist.add(CRUD.Create.name());
            itemlist.add(CRUD.Read.name());
            itemlist.add(CRUD.Update.name());
            itemlist.add(CRUD.Delete.name());
        }else{
            itemlist.add(CRUD.Read.name());
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemlist);
        final ListView listView = new ListView(getActivity());
        listView.setAdapter(arrayAdapter);

        // リストの行がタップされたとき
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Bundle bundle = getArguments();
                String crudStr = (String) listView.getItemAtPosition(position);
                CRUD crud = CRUD.valueOf(crudStr);
                bundle.putSerializable(CRUD.class.getName(), crud);
                if(crud != CRUD.Read){
                    SettingGestureDialog settingGestureDialog = SettingGestureDialog.newInstance();
                    settingGestureDialog.setArguments(bundle);
                    settingGestureDialog.show(getFragmentManager(), "gesture");
                }else {
                    SettingRUDEntityDataDialog settingRUDEntityDataDialog = SettingRUDEntityDataDialog.newInstance();
                    settingRUDEntityDataDialog.setArguments(bundle);
                    settingRUDEntityDataDialog.show(getFragmentManager(),"rud");
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
