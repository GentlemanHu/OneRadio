package pers.hu.oneradio.feel.home.animation;

import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.provider.SongInfo;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

import pers.hu.oneradio.tool.music.MusicHelper;

public class ItemIconAnimation {
    private RichPathAnimator animator, animator2;
    private RichPathView commandRichPathView;
    private boolean isBigAnim = false;
    private boolean isOneAnim = false;

    public boolean isIsBigAnim() {
        return isBigAnim;
    }

    public boolean isIsOneAnim() {
        return isOneAnim;
    }


    public ItemIconAnimation(RichPathView commandRichPathView) {
        this.commandRichPathView = commandRichPathView;
    }

    public void animateCommand() {
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

                .durationSet(3000)
                .repeatModeSet(RichPathAnimator.RESTART)
                .repeatCountSet(RichPathAnimator.INFINITE)
                .interpolatorSet(new LinearInterpolator())
                .start();
        isOneAnim = true;
    }

    public void openBigAnimation() {
        final RichPath part1 = commandRichPathView.findRichPathByName("part1");
        final RichPath part2 = commandRichPathView.findRichPathByName("part2");
        final RichPath part3 = commandRichPathView.findRichPathByName("part3");
        final RichPath part4 = commandRichPathView.findRichPathByName("part4");
        final RichPath part5 = commandRichPathView.findRichPathByName("part5");
        final RichPath part6 = commandRichPathView.findRichPathByName("part6");
        final RichPath part7 = commandRichPathView.findRichPathByName("part7");
        final RichPath part8 = commandRichPathView.findRichPathByName("part8");

        animator2 = RichPathAnimator
                .animate(part1)
                .scaleX(1, 0.9f, 1.07f, 1)
                .scaleY(1, 0.9f, 1.07f, 1)

                .andAnimate(part2)
                .scaleX(1, 0.9f, 1.07f, 1)
                .scaleY(1, 0.9f, 1.07f, 1)

                .andAnimate(part3)
                .scaleX(1, 0.9f, 1.07f, 1)
                .scaleY(1, 0.9f, 1.07f, 1)

                .andAnimate(part4)
                .scaleX(1, 0.9f, 1.07f, 1)
                .scaleY(1, 0.9f, 1.07f, 1)

                .andAnimate(part5)
                .scaleX(1, 0.9f, 1.07f, 1)
                .scaleY(1, 0.9f, 1.07f, 1)

                .andAnimate(part6)
                .scaleX(1, 0.9f, 1.07f, 1)
                .scaleY(1, 0.9f, 1.07f, 1)

                .andAnimate(part7)
                .scaleX(1, 0.9f, 1.07f, 1)
                .scaleY(1, 0.9f, 1.07f, 1)

                .andAnimate(part8)
                .scaleX(1, 0.9f, 1.07f, 1)
                .scaleY(1, 0.9f, 1.07f, 1)

                .durationSet(700)

                .repeatCountSet(3)
                .repeatMode(RichPathAnimator.RESTART)
                .start();
        isBigAnim = true;
    }

    public void cancelAnimOne() {
        isOneAnim = false;
        animator.cancel();
    }

    public void cancelBigAnim() {
        isBigAnim = false;
        animator2.cancel();
    }
}
