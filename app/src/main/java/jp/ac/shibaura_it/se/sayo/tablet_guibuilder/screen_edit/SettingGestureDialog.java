package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                Bundle bundle = new Bundle();
                int uniqueID = getArguments().getInt(ShareInformationManager.ATTRIBUTE_ID);
                bundle.putInt(ShareInformationManager.ATTRIBUTE_ID, uniqueID);
                String gestureName = Gesture.values()[position].name();
                bundle.putString(ShareInformationManager.ATTRIBUTE_NAME, gestureName);
                SettingTransitionScreenDialog stsDialog = SettingTransitionScreenDialog.newInstance();
                stsDialog.setArguments(bundle);
                stsDialog.show(getFragmentManager(), "transition");
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
