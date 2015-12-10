package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
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
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.setting_property_dialog);

        // Buttonがnullになる
//        Button ok = ((Button) getActivity().findViewById(R.id.setting_property_ok));
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int uniqueID = getArguments().getInt(ShareInformationManager.ATTRIBUTE_ID);
//                EditText idView = (EditText) getActivity().findViewById(R.id.setting_id);
//                String id = idView.getText().toString();
//                EditText labelView = (EditText) getActivity().findViewById(R.id.setting_label);
//                String label = labelView.getText().toString();
//                ShareInformationManager manager = ShareInformationManager.newInstance();
//                manager.writeWidgetProperty(uniqueID, id, label);
//            }
//        });
        return dialog;
    }

}
