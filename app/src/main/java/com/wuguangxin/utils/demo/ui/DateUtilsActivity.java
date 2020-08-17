package com.wuguangxin.utils.demo.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wuguangxin.utils.DateUtils;
import com.wuguangxin.utils.Utils;
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
        mEditStart.setText("1986-12-13");
        mEditEnd.setText(DateUtils.getDateShortString());
        mEditFormat.setText(DateUtils.Format.FORMAT_DATE);
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
        mEditEnd.setOnLongClickListener(v -> {
            mEditEnd.setText(DateUtils.getDateShortString());
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
            R.id.diffDays_Date_Date,

            R.id.diffDays_Long_true,
            R.id.diffDays_Long_false,

            R.id.formatMinuteSecond_Long_Long_false,
            R.id.formatMinuteSecond_Long_Long_true,
            R.id.formatHourMinuteSecond_Long_Format,
            R.id.formatHourMinuteSecond_Long_true,
            R.id.formatHourMinuteSecond_Long_false,
            R.id.formatHourMinute_Long,
            R.id.formatHourMinute_Long_Boolean,
            R.id.formatHourMinute_Long_Format,
    })
    public void onClick(View v) {
        mTextView.setText("");
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
            setMessage(v, DateUtils.formatDate(getTextLong(mEditTime), DateUtils.getFormat(getText(mEditFormat, true))), getText(mEditFormat, true));
            break;
        case R.id.formatDate_Date_Format:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(getTextLong(mEditTime)), DateUtils.getFormat(getText(mEditFormat, true))), getText(mEditFormat, true));
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
            setMessage(v, DateUtils.formatDate(getTextLong(mEditTime), getText(mEditFormat, true)), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String_Pattern:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDateString(getTextLong(mEditTime)), getText(mEditFormat, true)), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String_Position:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(new Date()), getParsePosition(mEditPosition)), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String_Format:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(new Date()), DateUtils.getFormat(getText(mEditFormat, true))), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(new Date())), DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatDate_String_Format_Position:
            setMessage(v, DateUtils.formatDate(DateUtils.formatDate(new Date()), DateUtils.getFormat(getText(mEditFormat, true)), getParsePosition(mEditPosition)), DateUtils.Format.FORMAT_DATE);
            break;

        case R.id.formatTimestamp_String:
            setMessage(v, DateUtils.formatTimestamp(DateUtils.formatDateString(new Date())) + "", DateUtils.Format.FORMAT_DATE);
            break;
        case R.id.formatTimestamp_Date:
            setMessage(v, DateUtils.formatTimestamp(new Date()) + "", DateUtils.Format.FORMAT_DATE);
            break;

        case R.id.diffDays_Date_Date:
            Date startDate = DateUtils.formatDateShort(getText(mEditStart));
            Date endDate = DateUtils.formatDateShort(getText(mEditEnd));
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("开始日期：").append(DateUtils.formatDateShortString(startDate))
                    .append("\n").append("结束日期：").append(DateUtils.formatDateShortString(endDate))
                    .append("\n").append("间隔天数：").append(DateUtils.diffDays(startDate, endDate)));
            break;

        case R.id.diffDays_Long_true:
            long diffDaysTextTrue = getTextLong(mEditTotal);
            mTextView.setText( new StringBuilder()
                    .append("format：").append(getText(v))
                    .append("\n").append("time：").append(diffDaysTextTrue).append(" 毫秒")
                    .append("\n").append("result：").append(DateUtils.diffDays(diffDaysTextTrue, true)));
            break;
        case R.id.diffDays_Long_false:
            long diffDaysTextFalse = getTextLong(mEditTotal);
            mTextView.setText( new StringBuilder()
                    .append("format：").append(getText(v))
                    .append("\n").append("time：").append(diffDaysTextFalse).append(" 毫秒")
                    .append("\n").append("result：").append(DateUtils.diffDays(diffDaysTextFalse, false)));
            break;

        case R.id.formatMinuteSecond_Long_Long_false:
            long end11 = DateUtils.getDate().getTime();
            long start11 = end11- DateUtils.ONE_MONTH - Utils.getRandom((int) DateUtils.ONE_DAY); // -1个月内的随机值
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("开始时间：").append(start11)
                    .append("\n").append("结束时间：").append(end11)
                    .append("\n").append("result：").append(DateUtils.formatMinuteSecond(start11, end11))); // 同下
//                    .append("\n").append("result：").append(DateUtils.formatMinuteSecond(start11, end11, false)));
            break;
        case R.id.formatMinuteSecond_Long_Long_true:
            long end22 = DateUtils.getDate().getTime();
            long start22 = end22- DateUtils.ONE_MONTH - Utils.getRandom((int) DateUtils.ONE_DAY); // -1个月内的随机值
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("开始时间：").append(start22)
                    .append("\n").append("结束时间：").append(end22)
                    .append("\n").append("result：").append(DateUtils.formatMinuteSecond(start22, end22, true)));
            break;

        case R.id.formatHourMinuteSecond_Long_Format:
            long totalTime_format= Utils.getRandom(Integer.MAX_VALUE);
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("毫秒值：").append(totalTime_format).append("毫秒")
                    .append("\n").append("result：").append(DateUtils.formatHourMinuteSecond(totalTime_format, "%sh%sm%ss")));
            break;
        case R.id.formatHourMinuteSecond_Long_true:
            long totalTime_true = Utils.getRandom(Integer.MAX_VALUE);
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("毫秒值：").append(totalTime_true).append("毫秒")
                    .append("\n").append("result：").append(DateUtils.formatHourMinuteSecond(totalTime_true, true)));
            break;
        case R.id.formatHourMinuteSecond_Long_false:
            long totalTime_false = Utils.getRandom(Integer.MAX_VALUE);
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("毫秒值：").append(totalTime_false).append("毫秒")
                    .append("\n").append("result：").append(DateUtils.formatHourMinuteSecond(totalTime_false, false)));
            break;
        case R.id.formatHourMinute_Long:
            long totalTime22 = Utils.getRandom(Integer.MAX_VALUE);
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("毫秒值：").append(totalTime22).append("毫秒")
                    .append("\n").append("result：").append(DateUtils.formatHourMinute(totalTime22)));
            break;
        case R.id.formatHourMinute_Long_Boolean:
            long totalTime23 = Utils.getRandom(Integer.MAX_VALUE);
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("毫秒值：").append(totalTime23).append("毫秒")
                    .append("\n").append("result：").append(DateUtils.formatHourMinute(totalTime23, true)));
            break;
        case R.id.formatHourMinute_Long_Format:
            long totalTime24 = Utils.getRandom(Integer.MAX_VALUE);
            mTextView.setText( new StringBuilder(getText(v))
                    .append("\n").append("毫秒值：").append(totalTime24).append("毫秒")
                    .append("\n").append("result：").append(DateUtils.formatHourMinute(totalTime24, "%s小时%s分钟")));
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
        return getText(view, false);
    }

    private String getText(View view, boolean defUseHint) {
        String string = null;
        if (view instanceof TextView) {
            string = ((TextView) view).getText().toString();
            if (defUseHint && TextUtils.isEmpty(string)) {
                string = ((TextView) view).getHint().toString();
            }
        }
        return string;
    }

    private long getTextLong(View view) {
        return getTextLong(view, false);
    }

    private long getTextLong(View view, boolean defUseHint) {
        String text = getText(view, defUseHint);
        return TextUtils.isEmpty(text) ? 0 : Long.parseLong(text);
    }
}
