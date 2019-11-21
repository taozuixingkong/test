package com.liuleilei.macbook.basedispose.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.liuleilei.macbook.basedispose.util.DensityUtil;

// 圆角
public class FourTwelveRoundLinearLayout extends RelativeLayout {

        private float _width,_height;
        private int _mRadiusPx;

        public FourTwelveRoundLinearLayout(Context context) {
            this(context, null);
        }

        public FourTwelveRoundLinearLayout(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public FourTwelveRoundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            _mRadiusPx = DensityUtil.dp2px(12);
            if (Build.VERSION.SDK_INT < 18) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            _width = getWidth();
            _height = getHeight();
        }

        @Override
        protected void onDraw(Canvas canvas) {

            //这里的目的是将画布设置成一个顶部边缘是圆角的矩形
            if (_width > _mRadiusPx && _height > _mRadiusPx) {
                Path path = new Path();

                path.moveTo(_mRadiusPx, 0);

                path.lineTo(_width - _mRadiusPx, 0);

                path.quadTo(_width, 0, _width, _mRadiusPx);

                path.lineTo(_width, _height - _mRadiusPx);

                path.quadTo(_width, _height, _width - _mRadiusPx, _height);

                path.lineTo(_mRadiusPx, _height);

                path.quadTo(0, _height, 0, _height - _mRadiusPx);

                path.lineTo(0, _mRadiusPx);

                path.quadTo(0, 0, _mRadiusPx, 0);

                canvas.clipPath(path);
            }

            super.onDraw(canvas);
        }
}
