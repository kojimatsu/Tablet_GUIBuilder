package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.CreationWidgetController;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.WidgetType;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;

/**
 * Created by 浩司 on 2015/11/10.
 */
public class SettingActionWidgetFragment extends Fragment {

    protected static SettingActionWidgetFragment newInstance() {
        SettingActionWidgetFragment fragment = new SettingActionWidgetFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_for_widget, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        ImageView  transition =  ((ImageView) getActivity().findViewById(R.id.to_transition));
        transition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingGestureDialog settingGestureDialog = SettingGestureDialog.newInstance();
                int uniqueID = getArguments().getInt(ShareInformationManager.ATTRIBUTE_ID);
                ShareInformationManager manager = ShareInformationManager.newInstance().newInstance();
                if (manager.getWidgetType(uniqueID) == WidgetType.INPUT){
                    Bundle bundle = new Bundle();
                    bundle.putInt(ShareInformationManager.ATTRIBUTE_ID, uniqueID);
                    settingGestureDialog.setArguments(bundle);
                    settingGestureDialog.show(getFragmentManager(), "gesture");
                }
            }
        });

        ImageView  operateEntity =((ImageView) getActivity().findViewById(R.id.operate_entity));
        operateEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageView delete = ((ImageView) getActivity().findViewById(R.id.delete_widget));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
