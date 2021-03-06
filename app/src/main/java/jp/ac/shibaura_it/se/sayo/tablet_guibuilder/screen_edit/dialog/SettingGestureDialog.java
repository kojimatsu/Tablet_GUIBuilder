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

/**
 * Created by 浩司 on 2015/11/10.
 */
public class SettingGestureDialog extends DialogFragment {

    private List<String> itemlist = new ArrayList<String>();


    public static SettingGestureDialog newInstance(){
        return new SettingGestureDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ設定
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("操作を選択してください");
        builder.setNegativeButton("CLOSE", onCancelListener);

        // アダプタ設定
        for (Gesture gesture : Gesture.values()) {
            itemlist.add(gesture.name());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemlist);
        ListView listView = new ListView(getActivity());
        listView.setAdapter(arrayAdapter);

        // リストの行がタップされたとき
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Bundle bundle = getArguments();
                String gestureName = Gesture.values()[position].name();
                bundle.putString(ShareInformationManager.ATTRIBUTE_NAME, gestureName);
                CRUD crud = (CRUD) bundle.getSerializable(CRUD.class.getName());
                if ( crud == null){
                    SettingTransitionScreenDialog stsDialog = SettingTransitionScreenDialog.newInstance();
                    stsDialog.setArguments(bundle);
                    stsDialog.show(getFragmentManager(), "transition");
                }else if(crud == CRUD.Create){
                    SettingCreationEntityDataDialog settingCreationEntityDataDialog = SettingCreationEntityDataDialog.newInstance();
                    settingCreationEntityDataDialog.setArguments(bundle);
                    settingCreationEntityDataDialog.show(getFragmentManager(),"setData");
                }else{
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
