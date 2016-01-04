package com.dazhong.idan;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ���˶����text view�������������һ�п��󣬿��ң����ж���
 *
 * @author YD
 */
public class AlignTextView extends TextView {
    private float textHeight; // �������ָ߶�
    private int width; // textView���
    private List<String> lines = new ArrayList<String>(); // �ָ�����
    private List<Integer> tailLines = new ArrayList<Integer>(); // β��
    private Align align = Align.ALIGN_LEFT; // Ĭ�����һ�������
    private boolean firstCalc = true;  // ��ʼ������
    private int oldPaddingBottom = Integer.MIN_VALUE;  // ��һ�ε�paddingBottom���ò����»���ʱ����

    // β�ж��뷽ʽ
    public enum Align {
        ALIGN_LEFT, ALIGN_CENTER, ALIGN_RIGHT  // ���У����󣬾���,��Զ������һ��
    }

    public AlignTextView(Context context) {
        super(context);
        setTextIsSelectable(false);
    }

//    public AlignTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        setTextIsSelectable(false);
//
////        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AlignTextView);
//
////        int alignStyle = ta.getInt(R.styleable.AlignTextView_align, 0);
//        switch (alignStyle) {
//            case 1:
//                align = Align.ALIGN_CENTER;
//                break;
//            case 2:
//                align = Align.ALIGN_RIGHT;
//                break;
//            default:
//                align = Align.ALIGN_LEFT;
//                break;
//        }
//
//        ta.recycle();
//    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //���Ƚ��и߶ȵ���
        if (firstCalc) {
            width = getMeasuredWidth();
            String text = getText().toString();
            TextPaint paint = getPaint();
            lines.clear();
            tailLines.clear();

            // �ı����л��з�ʱ���ָ������
            String[] items = text.split("\\n");
            for (String item : items) {
                calc(paint, item);
            }

            //���״�paddingBottomΪ��׼���˺��ڴ˻����ϵ���
            if (oldPaddingBottom == Integer.MIN_VALUE) {
                oldPaddingBottom = getPaddingBottom();
            }

            //��ȡ�и�
            textHeight = 1.0f * measureTextViewHeight(text, paint.getTextSize(), getMeasuredWidth
                    () - getPaddingLeft() - getPaddingRight()) / getLineCount();

            //����ʵ�ʸ߶�,���϶�����еĸ߶�(һ���Ǽ���)
            float heightGap = textHeight * (lines.size() - getLineCount());

            int height = getHeight();

            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            if (height < screenHeight) {
                getLayoutParams().height = getHeight() - (int) Math.ceil(heightGap);
            } else {
                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), oldPaddingBottom
                        + (int) Math.floor(heightGap));
            }
            firstCalc = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();

        width = getMeasuredWidth();

        Paint.FontMetrics fm = paint.getFontMetrics();
        float firstHeight = getTextSize() - (fm.bottom - fm.descent + fm.ascent - fm.top);

        int gravity = getGravity();
        if ((gravity & 0x1000) == 0) { // �Ƿ�ֱ����
            firstHeight = firstHeight + (textHeight - firstHeight) / 2;
        }

        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        width = width - paddingLeft - paddingRight;

        for (int i = 0; i < lines.size(); i++) {
            float drawY = i * textHeight + firstHeight;
            String line = lines.get(i);
            // �滭��ʼx����
            float drawSpacingX = paddingLeft;
            float gap = (width - paint.measureText(line));
            float interval = gap / (line.length() - 1);

            // �������һ��
            if (tailLines.contains(i)) {
                interval = 0;
                if (align == Align.ALIGN_CENTER) {
                    drawSpacingX += gap / 2;
                } else if (align == Align.ALIGN_RIGHT) {
                    drawSpacingX += gap;
                }
            }

            for (int j = 0; j < line.length(); j++) {
                float drawX = paint.measureText(line.substring(0, j)) + interval * j;
                canvas.drawText(line.substring(j, j + 1), drawX + drawSpacingX, drawY +
                        paddingTop, paint);
            }
        }
    }

    /**
     * ����β�ж��뷽ʽ
     *
     * @param align ���뷽ʽ
     */
    public void setAlign(Align align) {
        this.align = align;
        invalidate();
    }

    /**
     * ����ÿ��Ӧ��ʾ���ı���
     *
     * @param text Ҫ������ı�
     */
    private void calc(Paint paint, String text) {
        if (text.length() == 0) {
            lines.add("\n");
            return;
        }
        int startPosition = 0; // ��ʼλ��
        float oneChineseWidth = paint.measureText("��");
        int ignoreCalcLength = (int) (width / oneChineseWidth + 0.99); // ���Լ���ĳ���
        StringBuilder sb = new StringBuilder(text.substring(0, Math.min(ignoreCalcLength, text
                .length())));


        for (int i = ignoreCalcLength; i < text.length(); i++) {
            if (paint.measureText(text.substring(startPosition, i + 1)) > width) {
                startPosition = i;
                //��֮ǰ���ַ��������б���
                lines.add(sb.toString());

                sb = new StringBuilder();

                //��ӿ�ʼ���Ե��ַ��������Ȳ���Ļ�ֱ�ӽ���,�������
                if ((text.length() - startPosition) >= ignoreCalcLength) {
                    sb.append(text.substring(startPosition, startPosition + ignoreCalcLength));
                } else {
                    lines.add(text.substring(startPosition));
                    break;
                }

                i = i + ignoreCalcLength;
            } else {
                sb.append(text.charAt(i));
            }
        }
        if (sb.length() > 0) {
            lines.add(sb.toString());
        }

        tailLines.add(lines.size() - 1);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        firstCalc = true;
        super.setText(text, type);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (bottom != getPaddingBottom()) {
            oldPaddingBottom = Integer.MIN_VALUE;
        }
        super.setPadding(left, top, right, bottom);
    }


    /**
     * ��ȡ�ı�ʵ����ռ�߶ȣ��û������и�
     *
     * @param text        �ı�
     * @param textSize    �����С
     * @param deviceWidth ��Ļ���
     * @return �߶�
     */
    private int measureTextViewHeight(String text, float textSize, int deviceWidth) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(deviceWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }
}