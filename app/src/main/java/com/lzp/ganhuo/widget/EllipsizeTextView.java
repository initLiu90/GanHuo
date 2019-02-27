package com.lzp.ganhuo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;

import com.lzp.ganhuo.util.EllipsisUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class EllipsizeTextView extends AppCompatTextView {
    public EllipsizeTextView(Context context) {
        super(context);
    }

    public EllipsizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EllipsizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ellipsis();
    }

    private void ellipsis() {
        if (getLayout() == null) return;

        int ellipsisCount = getLayout().getEllipsisCount(getLineCount() - 1);//最后一行省略多少个字符
        if (ellipsisCount > 0) {
            int lineStart = getLayout().getLineStart(getLineCount() - 1);//最后一行首字符的位置
            int lineEnd = getLayout().getLineEnd(getLineCount() - 1);//最后一行末尾字符(包括被省略的字符)的位置
            CharSequence visiableCharSequence = getText().subSequence(lineStart, lineEnd - ellipsisCount);//展示的屏幕上的最后一行显示的字符（不包括被省略的字符）
//            Log.e("Test", "1111=" + visiableCharSequence.toString());

            float width = getLayout().getWidth();//Layout最大宽度
            int len = EllipsisUtil.calculate(visiableCharSequence, "...全文", width, getPaint());

            /**
             * 根据上面计算出来的 最后一行能够显示的原文本的长度，截取原文本，然后添加提示文本，调用setText()方法展示。
             */
            CharSequence text = getText().subSequence(0, lineStart + len);
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            ssb.append(text).append("...全文");
            setText(ssb);
        }
    }


}
