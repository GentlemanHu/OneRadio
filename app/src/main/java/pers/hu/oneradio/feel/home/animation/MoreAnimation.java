package pers.hu.oneradio.feel.home.animation;

import android.view.animation.DecelerateInterpolator;

import com.nightonke.boommenu.BoomMenuButton;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

public class MoreAnimation {
    private RichPathView more;
    private RichPathAnimator animator;
    private boolean once_pressed = false;
    private BoomMenuButton boom;

    public MoreAnimation(RichPathView more) {
        this.more = more;
    }

    public MoreAnimation(BoomMenuButton boom){
        this.boom = boom;
    }
    public void centerRotation(){
        if(!once_pressed){
            more.animate().rotationY(65).start();
            once_pressed =true;
        }else if(once_pressed){
            more.animate().rotationY(0).start();
            once_pressed =false;
        }
    }

    public void boomRotation(){
        if(!once_pressed){
            boom.animate().rotationY(65).start();
            once_pressed =true;
        }else if(once_pressed){
            boom.animate().rotationY(0).start();
            once_pressed =false;
        }
    }

    //由于找不到如何控制旋转的圆心，暂时不用
    @Deprecated
    public void onPressed() {
        final RichPath[] paths = more.findAllRichPaths();
        if (!once_pressed) {
            animator = RichPathAnimator.animate(paths)
                    .interpolator(new DecelerateInterpolator())
                    .translationY(0,10,20,10,0)
                    .rotation(90f)
                    .duration(1000)
                    .start();
            once_pressed = true;
            System.out.println("11111");
        } else if (once_pressed) {
            animator=RichPathAnimator.animate(paths)
                    .interpolator(new DecelerateInterpolator())
                    .rotation(0, 10, -10, 5, -5, 2, -2, 0)
                    .duration(1000)
                    .start();
            once_pressed = false;
            System.out.println("22222");
        }
    }
}
