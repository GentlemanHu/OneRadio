package pers.hu.oneradio.feel.home.perfectview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.richpath.RichPathView;

import java.io.Serializable;

import jp.wasabeef.blurry.Blurry;
import pers.hu.oneradio.R;
import pers.hu.oneradio.feel.home.animation.ItemIconAnimation;
import pers.hu.oneradio.feel.home.animation.Transition;

/**
 * Created by xmuSistone on 2016/9/18.
 */
public class CommonFragment extends Fragment implements Serializable {
    private ImageView imageView;
    private View address1, address2, address4, address5;
    private TextView address3;
    private RatingBar ratingBar;
    private View head1, head2, head3, head4;
    private String imageUrl;
    private RichPathView richPathView;
    private ItemIconAnimation animation;
    private ImageLoader imageLoader;
    private View rootView;
    private ImageView homebg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_common, null);
        DragLayout dragLayout = rootView.findViewById(R.id.drag_layout);
        homebg = rootView.findViewById(R.id.homebg);
        imageView = (ImageView) dragLayout.findViewById(R.id.image);
        richPathView = dragLayout.findViewById(R.id.loading);
        animation = new ItemIconAnimation(richPathView);
        animation.animateCommand();

        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        address1 = dragLayout.findViewById(R.id.address1);
        address2 = dragLayout.findViewById(R.id.address2);
        address3 = dragLayout.findViewById(R.id.address3);
        address4 = dragLayout.findViewById(R.id.address4);
        address5 = dragLayout.findViewById(R.id.address5);
        ratingBar = (RatingBar) dragLayout.findViewById(R.id.rating);

        head1 = dragLayout.findViewById(R.id.head1);
        head2 = dragLayout.findViewById(R.id.head2);
        head3 = dragLayout.findViewById(R.id.head3);
        head4 = dragLayout.findViewById(R.id.head4);


        return rootView;
    }

    public void bindData(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    public ImageView getImageView() {
        return imageView;
    }

}