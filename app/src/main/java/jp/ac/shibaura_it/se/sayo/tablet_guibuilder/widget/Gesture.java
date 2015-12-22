package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

/**
 * Created by 浩司 on 2015/12/03.
 */
public enum  Gesture {
    Tap,
    DoubleTap;

    public static Gesture getGesture(String gestureName) {
        if (gestureName.equals(Tap.name())){
            return Tap;
        }else if (gestureName.equals(DoubleTap.name())){
            return DoubleTap;
        }
        return null;
    }
}
