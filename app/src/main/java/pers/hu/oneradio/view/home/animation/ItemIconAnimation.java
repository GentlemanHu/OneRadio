package pers.hu.oneradio.view.home.animation;

import android.view.animation.LinearInterpolator;

import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

public class ItemIconAnimation {
    private boolean isAnim = false;
    private RichPathAnimator animator;

    public void animateCommand(RichPathView commandRichPathView) {
        isAnim = !isAnim;

        if (isAnim) {
            final RichPath part1 = commandRichPathView.findRichPathByName("part1");
            final RichPath part2 = commandRichPathView.findRichPathByName("part2");
            final RichPath part3 = commandRichPathView.findRichPathByName("part3");
            final RichPath part4 = commandRichPathView.findRichPathByName("part4");
            final RichPath part5 = commandRichPathView.findRichPathByName("part5");
            final RichPath part6 = commandRichPathView.findRichPathByName("part6");
            final RichPath part7 = commandRichPathView.findRichPathByName("part7");
            final RichPath part8 = commandRichPathView.findRichPathByName("part8");

            animator = RichPathAnimator
                    .animate(part1)
                    .trimPathOffset(0, 1.0f)

                    .andAnimate(part2)
                    .trimPathOffset(0.125f, 1.125f)

                    .andAnimate(part3)
                    .trimPathOffset(0.250f, 1.250f)

                    .andAnimate(part4)
                    .trimPathOffset(0.375f, 1.375f)

                    .andAnimate(part5)
                    .trimPathOffset(0.500f, 1.500f)

                    .andAnimate(part6)
                    .trimPathOffset(0.625f, 1.625f)

                    .andAnimate(part7)
                    .trimPathOffset(0.750f, 1.750f)

                    .andAnimate(part8)
                    .trimPathOffset(0.875f, 1.875f)

                    .durationSet(2000)
                    .repeatModeSet(RichPathAnimator.RESTART)
                    .repeatCountSet(RichPathAnimator.INFINITE)
                    .interpolatorSet(new LinearInterpolator())
                    .start();
        } else {
            animator.cancel();
        }
    }
}
