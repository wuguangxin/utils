package com.wuguangxin.utils.demo.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wuguangxin.utils.DateUtils;
import com.wuguangxin.utils.demo.R;

import java.text.ParsePosition;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class DateUtilsActivity extends BaseActivity {

    @BindView(R.id.text) TextView mTextView;
    @BindView(R.id.formatDateString_Long_Et) TextView mFormatDateStringEt;
    @BindView(R.id.edit_time) TextView mEditTime;
    @BindView(R.id.edit_format) TextView mEditFormat;
    @BindView(R.id.edit_position) TextView mEditPosition;

    @BindView(R.id.edit_start) TextView mEditStart;
    @BindView(R.id.edit_end) TextView mEditEnd;

    @BindView(R.id.edit_total) TextView mEditTotal;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_date_utils;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());
    }

    @Override
    public void initData() {
        mFormatDateStringEt.setText(System.currentTimeMillis() + "");
        mEditTime.setText(System.currentTimeMillis() + "");
    }

    @Override
    public void setListener() {
        mFormatDateStringEt.setOnLongClickListener(v -> {
            mFormatDateStringEt.setText(System.currentTimeMillis() + "");
            return false;
        });
        mEditTime.setOnLongClickListener(v -> {
            mEditTime.setText(System.currentTimeMillis() + "");
            return false;
        });

    }

    @OnClick({
            R.id.getDate,
            R.id.getDateString,
            R.id.getDateShort,
            R.id.getDateShortString,
            R.id.getDateStringFromNet,
            R.id.getTimestampFromNet,
            // =====================
            R.id.formatDate_Date,
            R.id.formatDateString_Long,
            R.id.formatDateString_String,
            R.id.formatDateString_Date,
            R.id.formatDateNoSecondString_Long,
            R.id.formatDateShortString_Long,
            R.id.formatDateShortString_Date,

            R.id.formatDate_Long_Format,
            R.id.formatDate_Date_Format,

            R.id.formatDate_Long,
            R.id.formatDateShort_Long,
            R.id.formatDateShort_String,
            R.id.formatDateShort_Date,
            R.id.formatDate_Long_pattern,
            R.id.formatDate_String_Pattern,
            R.id.formatDate_String_Position,
            R.id.formatDate_String_Format,
            R.id.formatDate_String,
            R.id.formatDate_String_Format_Position,

            R.id.formatTimestamp_String,
            R.id.formatTimestamp_Date,
            R.id.getIntervalDays_Date_Date,
            R.id.diffDays_Date_Date,

            R.id.diffDays_Long_true,
            R.id.diffDays_Long_false,

            R.id.diffMinuteSecond_Long_Long,
            R.id.formatHourMinuteSecond_Long_Format,
            R.id.formatHourMinuteSecond_Long_Boolean,
            R.id.formatHourMinute_Long,
            R.id.formatHourMinute_Long_Boolean,
            R.id.formatHourMinute_Long_Format,
    })
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.getDate:
            setMessage(v, DateUtils.getDate(), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.getDateString:
            setMessage(v, DateUtils.getDateString(), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.getDateShort:
            setMessage(v, DateUtils.getDateShort(), DateUtils.Format.FORMAT_DATE_SHORT);
            break;
        case R.id.getDateShortString:
            setMessage(v, DateUtils.getDateShortString(), DateUtils.Format.FORMAT_DATE_SHORT);
            break;
        case R.id.getDateStringFromNet:
            setMessage(v, DateUtils.getDateStringFromNet(), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.getTimestampFromNet:
            setMessage(v, DateUtils.getTimestampFromNet() + "", DateUtils.Format.FORMAT_DATE);
            break;

        //
        case R.id.formatDate_Date:
            setMessage(v, DateUtils.formatDate(new Date()) + "", DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDateString_Long:
            setMessage(v, DateUtils.formatDateString(Long.parseLong(getText(mFormatDateStringEt))), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDateString_String:
            setMessage(v, DateUtils.formatDateString(getText(mFormatDateStringEt)), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDateString_Date:
            setMessage(v, DateUtils.formatDateString(new Date()), DateUtils.Format.FORMAT_DATE);
            break;


        case R.id.formatDateNoSecondString_Long:
            setMessage(v, DateUtils.formatDateNoSecondString(getTextLong(mFormatDateStringEt)), DateUtils.Format.FORMAT_DATE_2);
            break;
        case R.id.formatDateShortString_Long:
            setMessage(v, DateUtils.formatDateShortString(getTextLong(mFormatDateStringEt)), DateUtils.Format.FORMAT_DATE_SHORT);
            break;
        case R.id.formatDateShortString_Date:
            setMessage(v, DateUtils.formatDateShortString(new Date()), DateUtils.Format.FORMAT_DATE_SHORT);
            break;

        case R.id.formatDate_Long_Format:
            setMessage(v, DateUtils.formatDate(getTextLong(mEditTime), DateUtils.getFormat(getText(mEditFormat))), getText(mEditFormat));
            break;
        case R.id.formatDate_Date_Format:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(getTextLong(mEditTime)), DateUtils.getFormat(getText(mEditFormat))), getText(mEditFormat));
            break;

        case R.id.formatDate_Long:
            setMessage(v, DateUtils.formatDate(getTextLong(mEditTime)), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDateShort_Long:
            setMessage(v, DateUtils.formatDateShort(getTextLong(mEditTime)), DateUtils.Format.FORMAT_DATE_SHORT);
            break;
        case R.id.formatDateShort_String:
            setMessage(v, DateUtils.formatDateShort(DateUtils.formatDateString(getTextLong(mEditTime))), DateUtils.Format.FORMAT_DATE_SHORT);

            break;
        case R.id.formatDateShort_Date:
            setMessage(v, DateUtils.formatDateShort(DateUtils.formatDate(getTextLong(mEditTime))), DateUtils.Format.FORMAT_DATE_SHORT);
            break;
        case R.id.formatDate_Long_pattern:
            setMessage(v, DateUtils.formatDate(getTextLong(mEditTime), getText(mEditFormat)), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String_Pattern:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDateString(getTextLong(mEditTime)), getText(mEditFormat)), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String_Position:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(new Date()), getParsePosition(mEditPosition)), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String_Format:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(new Date()), DateUtils.getFormat(getText(mEditFormat))), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(new Date())), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String_Format_Position:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(new Date()), DateUtils.getFormat(getText(mEditFormat)), getParsePosition(mEditPosition)), DateUtils.Format.FORMAT_DATE);
            break;

        case R.id.formatTimestamp_String:
            setMessage(v, DateUtils.formatTimestamp(DateUtils.formatDateString(new Date())) + "", DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatTimestamp_Date:
            setMessage(v, DateUtils.formatTimestamp(new Date()) + "", DateUtils.Format.FORMAT_DATE);
            break;


        case R.id.getIntervalDays_Date_Date:
            Date startDate = DateUtils.formatDateShort(getText(mEditStart));
            Date endDate = DateUtils.formatDateShort(getText(mEditStart));
            mTextView.setText( new StringBuilder(getText(v))
                    .append("开始日期：").append(DateUtils.formatDateString(startDate)).append("\n")
                    .append("结束日期：").append(DateUtils.formatDateString(endDate)).append("\n")
                    .append("间隔天数：").append(DateUtils.getIntervalDays(startDate, endDate)).append("天"));
            break;
        case R.id.diffDays_Date_Date:
            Date startDate1 = DateUtils.formatDateShort(getText(mEditStart));
            Date endDate1 = DateUtils.formatDateShort(getText(mEditStart));
            mTextView.setText( new StringBuilder(getText(v))
                    .append("开始日期：").append(DateUtils.formatDateString(startDate1)).append("\n")
                    .append("结束日期：").append(DateUtils.formatDateString(endDate1)).append("\n")
                    .append("格式化：").append(DateUtils.diffDays(startDate1, endDate1)));
            break;

        case R.id.diffDays_Long_true:
//            mTextView.setText( new StringBuilder(getText(v))
//                    .append("格式化：").append(DateUtils.diffDays(startDate2, endDate2)));
            break;
        case R.id.diffDays_Long_false:
            break;

        case R.id.diffMinuteSecond_Long_Long:
            break;
        case R.id.formatHourMinuteSecond_Long_Format:
            break;
        case R.id.formatHourMinuteSecond_Long_Boolean:
            break;
        case R.id.formatHourMinute_Long:
            break;
        case R.id.formatHourMinute_Long_Boolean:
            break;
        case R.id.formatHourMinute_Long_Format:
            break;
        }
    }

    private ParsePosition getParsePosition(View v) {
        String position = ((TextView)v).getText().toString().trim();
        if (!TextUtils.isEmpty(position)) {
            return new ParsePosition(Integer.parseInt(position));
        }
        return new ParsePosition(0);
    }

    private void setMessage(View view, Date date, String pattern) {
        mTextView.setText(new StringBuilder().append(getText(view))
                .append("\n").append("source： ").append(date)
                .append("\n").append("pattern：").append(pattern)
                .append("\n").append("result： ").append(DateUtils.formatDateString(date)));
    }

    private void setMessage(View view, String resultDate, String pattern) {
        setMessage(view, null, resultDate, pattern);
    }

    private void setMessage(View view, String sourceDate, String resultDate, String pattern) {
        mTextView.setText(new StringBuilder().append(getText(view))
                .append("\n").append("source： ").append(sourceDate == null ? "当前日期" : sourceDate)
                .append("\n").append("pattern：").append(pattern)
                .append("\n").append("result： ").append(resultDate));
    }

    private String getText(View view) {
        return view instanceof TextView ? ((TextView) view).getText().toString() : null;
    }

    private long getTextLong(View view) {
        String text = getText(view);
        return text == null || text.length() < 13 ? 0 : Long.parseLong(text);
    }
}
