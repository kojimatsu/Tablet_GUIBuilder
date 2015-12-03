package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.RelativeLayout;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;

/**
 * Created by 浩司 on 2015/12/03.
 */
public class CreationScreenController {

    public static ScreenFragment createScreen(Mode mode, String useCaseName){
        ScreenFragment screenFragment = ScreenFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(ShareInformationManager.ATTRIBUTE_NAME,useCaseName);
        bundle.putSerializable(screenFragment.MODE, mode);
        screenFragment.setArguments(bundle);
        return screenFragment;
    }

    /**
     * スマホ画面fragmentのサイズ設定
     */
    public static void setSize(Fragment fragment, int id) {
        setSize(fragment.getActivity(),id);
    }

    /**
     * スマホ画面fragmentのサイズ設定
     */
    public static void setSize(Activity activity, int id) {
        RelativeLayout screen = (RelativeLayout) activity.findViewById(id);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(360, 600);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        screen.setLayoutParams(params);
    }
}
