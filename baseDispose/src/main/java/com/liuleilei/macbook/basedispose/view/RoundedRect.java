package com.liuleilei.macbook.basedispose.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.liuleilei.macbook.basedispose.R;

/**
 * create by liu
 * on2019/11/21
 */
public class RoundedRect extends RelativeLayout {
    private Paint _borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Canvas _borderCanvas;
    private Bitmap _borderBitmap;

    private Paint _backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Canvas _backgroundCanvas;
    private Bitmap _backgroundBitmap;

    private Paint _clipPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Canvas _clipCanvas;
    private Bitmap _clipBitmap;

    private PorterDuffXfermode _clipMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    private Path _path = new Path();

    private int _leftTop = 0;
    private int _rightTop = 0;
    private int _leftBottom = 0;
    private int _rightBottom = 0;
    private int _borderWidth = 0;
    private @ColorInt
    int _borderColor = Color.TRANSPARENT;
    private @ColorInt
    int _backgroundColor = Color.TRANSPARENT;
    private boolean _painted = false;

    public RoundedRect(Context context, int leftTopRadius, int rightTopRadius, int leftBottomRadius, int rightBottomRadius, @ColorInt int borderColor, int borderWidth, @ColorInt int backgroundColor) {
        super(context);

        _leftTop = leftTopRadius;
        _rightTop = rightTopRadius;
        _leftBottom = leftBottomRadius;
        _rightBottom = rightBottomRadius;
        _borderColor = borderColor;
        _borderWidth = borderWidth;
        _backgroundColor = backgroundColor;

        init();
    }

    public RoundedRect(Context context) {
        super(context);

        _leftTop = 0;
        _rightTop = 0;
        _leftBottom = 0;
        _rightBottom = 0;
        _borderColor = Color.parseColor("#00000000");
        _borderWidth = 0;
        _backgroundColor = Color.parseColor("#00000000");
        init();
    }

    public RoundedRect(Context context, @ColorInt int backgroundColor) {
        super(context);

        _leftTop = 0;
        _rightTop = 0;
        _leftBottom = 0;
        _rightBottom = 0;
        _borderColor = Color.parseColor("#00000000");
        _borderWidth = 0;
        _backgroundColor = backgroundColor;

        init();
    }

    public RoundedRect(Context context, int cornerRadius, @ColorInt int borderColor, int borderWidth, @ColorInt int backgroundColor) {
        super(context);

        _leftTop = cornerRadius;
        _rightTop = cornerRadius;
        _leftBottom = cornerRadius;
        _rightBottom = cornerRadius;
        _borderColor = borderColor;
        _borderWidth = borderWidth;
        _backgroundColor = backgroundColor;

        init();
    }

    public RoundedRect(Context context, int leftTopRadius, int rightTopRadius, int leftBottomRadius, int rightBottomRadius, @ColorInt int borderColor, int borderWidth) {
        super(context);

        _leftTop = leftTopRadius;
        _rightTop = rightTopRadius;
        _leftBottom = leftBottomRadius;
        _rightBottom = rightBottomRadius;
        _borderColor = borderColor;
        _borderWidth = borderWidth;

        init();
    }

    public RoundedRect(Context context, int cornerRadius, @ColorInt int borderColor, int borderWidth) {
        super(context);

        _leftTop = cornerRadius;
        _rightTop = cornerRadius;
        _leftBottom = cornerRadius;
        _rightBottom = cornerRadius;
        _borderColor = borderColor;
        _borderWidth = borderWidth;

        init();
    }

    public RoundedRect(Context context, @ColorInt int borderColor, int borderWidth) {
        super(context);

        _leftTop = 0;
        _rightTop = 0;
        _leftBottom = 0;
        _rightBottom = 0;
        _borderColor = borderColor;
        _borderWidth = borderWidth;

        init();
    }

    public RoundedRect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

        init();
    }

    public RoundedRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedRect);

        int cornerRadius = typedArray.getDimensionPixelSize(R.styleable.RoundedRect_cornerRadius, 0);
        int leftTop = typedArray.getDimensionPixelSize(R.styleable.RoundedRect_leftTopCornerRadius, 0);
        int rightTop = typedArray.getDimensionPixelSize(R.styleable.RoundedRect_rightTopCornerRadius, 0);
        int leftBottom = typedArray.getDimensionPixelSize(R.styleable.RoundedRect_leftBottomCornerRadius, 0);
        int rightBottom = typedArray.getDimensionPixelSize(R.styleable.RoundedRect_rightBottomCornerRadius, 0);

        _borderWidth = typedArray.getDimensionPixelOffset(R.styleable.RoundedRect_borderWidth, 0);
        _borderColor = typedArray.getColor(R.styleable.RoundedRect_borderColor, Color.TRANSPARENT);
        _backgroundColor = typedArray.getColor(R.styleable.RoundedRect_backgroundColor, Color.TRANSPARENT);

        _leftTop = (cornerRadius > 0 ? cornerRadius : leftTop) - _borderWidth / 2;
        _rightTop = (cornerRadius > 0 ? cornerRadius : rightTop) - _borderWidth / 2;
        _leftBottom = (cornerRadius > 0 ? cornerRadius : leftBottom) - _borderWidth / 2;
        _rightBottom = (cornerRadius > 0 ? cornerRadius : rightBottom) - _borderWidth / 2;

        typedArray.recycle();

        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_HARDWARE, null);

        _borderPaint.setStrokeWidth(_borderWidth);
        _borderPaint.setStrokeCap(Paint.Cap.ROUND);
        _borderPaint.setStrokeJoin(Paint.Join.ROUND);
        _borderPaint.setStyle(Paint.Style.STROKE);
        _borderPaint.setAntiAlias(true);
        _borderPaint.setColor(_borderColor);

        _backgroundPaint.setStyle(Paint.Style.FILL);
        _backgroundPaint.setAntiAlias(true);
        _backgroundPaint.setColor(_backgroundColor);

        _clipPaint.setStyle(Paint.Style.FILL);
        _clipPaint.setAntiAlias(true);
        _clipPaint.setColor(Color.RED);

        Drawable background = getBackground();
        if (background == null) {
            setBackgroundColor(Color.TRANSPARENT);
        }

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!_painted) {
            drawPath(_path);
            _borderCanvas.drawPath(_path, _borderPaint);
            _clipCanvas.drawPath(_path, _clipPaint);
            _backgroundCanvas.drawPath(_path, _backgroundPaint);
        }

        canvas.save();
        _clipPaint.setXfermode(_clipMode);
        canvas.drawBitmap(_clipBitmap, 0, 0, _clipPaint);
        canvas.drawBitmap(_borderBitmap, 0, 0, _borderPaint);
        canvas.drawBitmap(_backgroundBitmap, 0, 0, _backgroundPaint);
        canvas.restore();
        _painted = true;
    }

    private void drawPath(Path path) {
        int totalWidth = getWidth();

        int totalHeight = getHeight();

        float borderHalfWidth = (float) _borderWidth / 2;

        path.reset();
        RectF drawingRect = new RectF(borderHalfWidth, borderHalfWidth, totalWidth - borderHalfWidth, totalHeight - borderHalfWidth);
        RectF topLeftArcBound = new RectF();
        RectF topRightArcBound = new RectF();
        RectF bottomLeftArcBound = new RectF();
        RectF bottomRightArcBound = new RectF();

        topRightArcBound.set(drawingRect.right - _rightTop * 2, drawingRect.top, drawingRect.right, drawingRect.top + _rightTop * 2);
        bottomRightArcBound.set(drawingRect.right - _rightBottom * 2, drawingRect.bottom - _rightBottom * 2, drawingRect.right, drawingRect.bottom);
        bottomLeftArcBound.set(drawingRect.left, drawingRect.bottom - _leftBottom * 2, drawingRect.left + _leftBottom * 2, drawingRect.bottom);
        topLeftArcBound.set(drawingRect.left, drawingRect.top, drawingRect.left + _leftTop * 2, drawingRect.top + _leftTop * 2);

        path.reset();
        path.moveTo(drawingRect.left + _leftTop, drawingRect.top);
        path.lineTo(drawingRect.right - _rightTop, drawingRect.top);
        path.arcTo(topRightArcBound, -90, 90);
        path.lineTo(drawingRect.right, drawingRect.bottom - _rightBottom);
        path.arcTo(bottomRightArcBound, 0, 90);
        path.lineTo(drawingRect.left + _leftBottom, drawingRect.bottom);
        path.arcTo(bottomLeftArcBound, 90, 90);
        path.lineTo(drawingRect.left, drawingRect.top + _leftTop);
        path.arcTo(topLeftArcBound, 180, 90);
        path.close();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        reset();
    }

    public void setBorderWidth(int width) {
        this._borderWidth = width;
        _borderPaint.setStrokeWidth(_borderWidth);

        reset();
        invalidate();
    }

    public void setBorderColor(@ColorInt int color) {
        this._borderColor = color;
        _borderPaint.setColor(_borderColor);

        reset();
        invalidate();
    }

    public void setCornerRadius(int radius) {
        this._leftTop = radius;
        this._leftBottom = radius;
        this._rightTop = radius;
        this._rightBottom = radius;

        reset();
        invalidate();
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this._backgroundColor = backgroundColor;

        reset();
        invalidate();
    }

    private void reset() {
        if (_borderBitmap != null && !_borderBitmap.isRecycled()) {
            _borderBitmap.recycle();
        }

        if (_clipBitmap != null && !_clipBitmap.isRecycled()) {
            _clipBitmap.recycle();
        }

        if (_backgroundBitmap != null && !_backgroundBitmap.isRecycled()) {
            _backgroundBitmap.recycle();
        }
        if (getHeight() > 0) {
            _borderBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            _borderCanvas = new Canvas(_borderBitmap);

            _clipBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            _clipCanvas = new Canvas(_clipBitmap);

            _backgroundBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            _backgroundCanvas = new Canvas(_backgroundBitmap);
            _painted = false;
        }
    }
}
