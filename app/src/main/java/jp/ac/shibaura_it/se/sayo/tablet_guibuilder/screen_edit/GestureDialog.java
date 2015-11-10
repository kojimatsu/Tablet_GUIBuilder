package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by 浩司 on 2015/11/10.
 */
public class GestureDialog extends DialogFragment {


    private ArrayList<String> itemlist = new ArrayList<String>();

    public static GestureDialog newInstance(){
        return new GestureDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_listview, null, false);
        // ダイアログ設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("タイトル");
        builder.setNegativeButton("CLOSE", onCancelListener);

        // アダプタ設定
        DialogListAdapter dialogListAdapter = new DialogListAdapter(getActivity());
        itemlist.add("a");
        dialogListAdapter.setList(itemlist);
        ListView listView = (ListView) getActivity().findViewById(R.id.dialog_listView);
        listView.setAdapter(dialogListAdapter);

        // リストの行がタップされたとき
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {


            }
        });

        builder.setView(view);
        return builder.create();
    }

    // CLOSEボタンが押されたとき
    private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    };
}
