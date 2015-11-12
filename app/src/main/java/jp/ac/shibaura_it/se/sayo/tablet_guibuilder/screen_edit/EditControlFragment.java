package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by matsu on 2014/12/01.
 */
public class EditControlFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_control_fragment, container, false);
    }

    /**
     *
     * @param useCasePath ユースケース名
     */
    public void addScreenFragment(String useCasePath) {
        setSize();
        ScreenFragment screenFragment = ScreenFragment.newInstance(useCasePath);
        // ActivityにFragmentを登録する。
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // Layout位置先の指定
        fragmentTransaction.replace(R.id.edit_screen, screenFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    /**
     * スマホ画面fragmentのサイズ設定
     */
    private void setSize() {
        RelativeLayout screen = (RelativeLayout) getActivity().findViewById(
                R.id.edit_screen);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(360, 600);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        screen.setLayoutParams(params);
    }

    public  void addSettingWidgetFragment(){
        SettingActionWidgetFragment settingWidgetFragment = SettingActionWidgetFragment.newInstance();
        // ActivityにFragmentを登録する。
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // Layout位置先の指定
        fragmentTransaction.replace(R.id.setting_widget, settingWidgetFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

}
