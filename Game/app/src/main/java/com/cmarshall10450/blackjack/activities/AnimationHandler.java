package com.cmarshall10450.blackjack.activities;


import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class AnimationHandler<T> {

  ArrayList<T> items;
  int animationId;

  public AnimationHandler(Context context, ArrayList<T> items, int animationId) {
    this.items = items;
    this.animationId = animationId;

    Animation animation = AnimationUtils.loadAnimation(context, animationId);

    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {

      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
  }
}
