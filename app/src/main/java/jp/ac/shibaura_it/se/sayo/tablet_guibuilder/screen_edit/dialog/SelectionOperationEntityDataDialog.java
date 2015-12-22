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

        itemlist.add(CRUD.Create.name());
        itemlist.add(CRUD.Read.name());
        itemlist.add(CRUD.Update.name());
        itemlist.add(CRUD.Delete.name());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemlist);
        ListView listView = new ListView(getActivity());
        listView.setAdapter(arrayAdapter);

        // リストの行がタップされたとき
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
            Bundle bundle = getArguments();
            CRUD crud = CRUD.values()[position];
            bundle.putSerializable(CRUD.class.getName(),crud);
            SettingGestureDialog settingGestureDialog = SettingGestureDialog.newInstance();
            settingGestureDialog.setArguments(bundle);
            settingGestureDialog.show(getFragmentManager(), "gesture");
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
