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

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenEditActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;

/**
 * Created by 浩司 on 2015/12/03.
 */
public class SettingTransitionScreenDialog extends DialogFragment {

    private List<String> itemlist = new ArrayList<String>();


    public static SettingTransitionScreenDialog newInstance(){
        return new SettingTransitionScreenDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("遷移先の画面を設定してください");
        builder.setNegativeButton("CLOSE", onCancelListener);

        // アダプタ設定
        final ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();
        ScreenEditActivity seActivity = (ScreenEditActivity) getActivity();
        final List<String> useCaseNameList = shareInformationManager.getChildUseCaseNameList(seActivity.useCaseName);
        for (String useCaseName : useCaseNameList) {
            itemlist.add(useCaseName);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemlist);
        ListView listView = new ListView(getActivity());
        listView.setAdapter(arrayAdapter);

        // リストの行がタップされたとき
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                int uniqueID = getArguments().getInt(ShareInformationManager.ATTRIBUTE_ID);
                String gestureName = getArguments().getString(ShareInformationManager.ATTRIBUTE_NAME);
                String toTransitionUseCaseName = useCaseNameList.get(position);
                shareInformationManager.writeTransitionScreen(uniqueID,gestureName,toTransitionUseCaseName);
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
