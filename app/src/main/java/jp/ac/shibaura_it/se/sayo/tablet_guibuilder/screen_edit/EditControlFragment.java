package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLWriting;

/**
 * Created by matsu on 2014/12/01.
 */
public class EditControlFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_control_fragment, container, false);
    }

    public void addScreenFragment(String useCaseName) {
        CreationScreenController.setSize(this, R.id.edit_screen);
        ScreenFragment screenFragment = CreationScreenController.createScreen(Mode.EDITION, useCaseName);
        // ActivityにFragmentを登録する。
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // Layout位置先の指定
        fragmentTransaction.replace(R.id.edit_screen, screenFragment);
        fragmentTransaction.commit();
    }


    /**
     * 画面遷移やEntityのCRUD等に関する設定をするFragmentの追加
     * @param uniqueID
     */
    public  void addSettingWidgetFragment(int uniqueID){
        SettingActionWidgetFragment settingWidgetFragment = SettingActionWidgetFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt(XMLWriting.ATTRIBUTE_ID, uniqueID);
        settingWidgetFragment.setArguments(bundle);
        // ActivityにFragmentを登録する。
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.setting_widget, settingWidgetFragment);
        fragmentTransaction.commit();
    }

}
