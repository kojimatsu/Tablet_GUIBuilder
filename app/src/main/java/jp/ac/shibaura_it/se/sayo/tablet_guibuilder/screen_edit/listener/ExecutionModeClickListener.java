package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.listener;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.execution.ExecutionActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.CreationScreenController;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.Mode;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenFragment;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.Gesture;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;

/**
 * Created by 浩司 on 2015/12/03.
 */
public class ExecutionModeClickListener implements View.OnClickListener {

    private  FragmentTransaction fragmentTransaction;

    public ExecutionModeClickListener(Context context){
        Activity activity = (Activity) context;
        fragmentTransaction = activity.getFragmentManager().beginTransaction();
    }

    @Override
    public void onClick(View v) {
        int uniqueID = v.getId();
        /**
         * 現状はTapしか認識しない
         */
        ShareInformationManager manager = ShareInformationManager.newInstance();
        String toTransitionUseCase = manager.getToTransitionUseCaseName(uniqueID, Gesture.Tap);
        if (toTransitionUseCase != null){
            moveScreen(toTransitionUseCase);
        }
    }

    private void moveScreen(String useCaseName){
        ScreenFragment screenFragment = CreationScreenController.createScreen(Mode.EXECUTION, useCaseName);
        // ActivityにFragmentを登録する。
        // Layout位置先の指定
        fragmentTransaction.replace(R.id.executon_screen, screenFragment);
        fragmentTransaction.commit();
        ExecutionActivity.setScreen(screenFragment);
    }
}
