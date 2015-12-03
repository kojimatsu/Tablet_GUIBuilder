package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.execution;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.CreationScreenController;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.Mode;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenFragment;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;

/**
 * Created by 浩司 on 2015/12/04.
 */
public class ExecutionActivity extends Activity{

    private static ScreenFragment screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.execution_activity);
        CreationScreenController.setSize(this, R.id.executon_screen);
        ShareInformationManager manager = ShareInformationManager.newInstance();
        String rootUseCaseName = manager.getChildUseCaseNameList(null).get(0);
        setScreenFragment(rootUseCaseName);

        findViewById(R.id.screen_revert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();
                Bundle bundle = screen.getArguments();
                String currentUseCaseName = bundle.getString(ShareInformationManager.ATTRIBUTE_NAME);
                String parentUseCaseName = shareInformationManager.getParentUseCase(shareInformationManager.getParentUseCase(currentUseCaseName));
                Debug.log("------------");
                if (parentUseCaseName != null){
                    setScreenFragment(parentUseCaseName);
                }
            }
        });
    }

    private void setScreenFragment(String useCaseName){
        screen = CreationScreenController.createScreen(Mode.EXECUTION, useCaseName);
        // ActivityにFragmentを登録する。
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // Layout位置先の指定
        fragmentTransaction.replace(R.id.executon_screen, screen);
        fragmentTransaction.commit();
    }

    public static void setScreen(ScreenFragment screen){
        ExecutionActivity.screen = screen;
    }
}
