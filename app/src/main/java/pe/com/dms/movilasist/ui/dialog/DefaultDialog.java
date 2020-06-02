package pe.com.dms.movilasist.ui.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.ui.adapter.dialog.DialogDefaultAdapter;
import pe.com.dms.movilasist.ui.filters.PatternInputFilter;
import pe.com.dms.movilasist.util.FontUtils;
import pe.com.dms.movilasist.util.KeyboardUtils;

public class DefaultDialog extends DialogFragment {

    private static final String TAG = DefaultDialog.class.getSimpleName();

    public static final int VALUE_NULL = -1;

    public enum DialogType {
        ALERT,
        CONFIRM,
        INPUT,
        ITEMS,
        ITEMS_INPUT,
        SINGLE_CHOICE
    }

    TextView title;
    EditText inputOthers;
    TextView message;
    Button accept;
    Button cancel;
    Button neutral;
    RecyclerView recycler;
    View containerButton;
    ViewGroup mContainerInflater;
    AppCompatImageView ivIcon;

    private OnSingleButtonListener mPositiveListener;
    private OnSingleButtonListener mNegativeListener;
    private OnSingleButtonListener mAnyListener;
    private OnInputListener mAInputListener;
    private OnItemsListener mItemsListener;
    private OnSingleChoiceItemsListener mSingleChoiceItemsListener;
    private OnCancelListener mCancelListener;

    private DialogDefaultAdapter adapter;

    private String mTitle;
    private String mMessage;
    private String mTextNegativeButton;
    private String mTextPositiveButton;
    private int mTextColorNegativeButton;
    private int mTextColorPositiveButton;
    private DialogType mDialogType;
    private ArrayList<CharSequence> mListItems;
    private int mSelectedIndexList;

    /**
     * {@link DialogType#INPUT}
     **/
    private CharSequence mInputHint;
    private CharSequence mInputText;
    private int mInputType;
    private int mInputMaxLength;
    private Pattern mInputPattern;
    //private Typeface typeface;
    private int mIcon;

    public DefaultDialog() {
    }

    @SuppressLint("ValidFragment")
    public DefaultDialog(Builder builder) {
        mTitle = builder.title;
        mMessage = builder.message;
        mTextNegativeButton = builder.textNegativeButton;
        mTextPositiveButton = builder.textPositiveButton;
        mTextColorNegativeButton = builder.textColorNegativeButton;
        mTextColorPositiveButton = builder.textColorPositiveButton;
        mDialogType = builder.dialogType;
        mInputHint = builder.inputHint;
        mInputText = builder.inputText;
        mInputType = builder.inputType;
        mInputMaxLength = builder.inputMaxLength;
        mInputPattern = builder.inputPattern;
        mPositiveListener = builder.onPositiveCallback;
        mNegativeListener = builder.onNegativeCallback;
        mAnyListener = builder.onAnyCallback;
        mAInputListener = builder.inputCallback;
        mListItems = builder.items;
        mSelectedIndexList = builder.selectedIndex;
        mItemsListener = builder.listCallback;
        mSingleChoiceItemsListener = builder.listCallbackSingleChoice;
        mCancelListener = builder.onCancelListener;
        setCancelable(builder.isCancelable);
        mIcon = builder.icon;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = createDialog();
        if (mDialogType == DialogType.INPUT) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        return dialog;
    }

    @UiThread
    public AlertDialog createDialog() {
        //typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Medium.ttf");
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view1 = layoutInflater.inflate(R.layout.dialog_default, null);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(view1);

        title = view1.findViewById(R.id.text_title);
        message = view1.findViewById(R.id.text_message);
        inputOthers = view1.findViewById(R.id.input_others);
        accept = view1.findViewById(R.id.btn_accept);
        cancel = view1.findViewById(R.id.btn_cancel);
        neutral = view1.findViewById(R.id.btn_neutral);
        recycler = view1.findViewById(R.id.recycler);
        containerButton = view1.findViewById(R.id.container_button);
        ivIcon = view1.findViewById(R.id.iv_icon);
        mContainerInflater = view1.findViewById(R.id.container_inflater);

        setupRecyclerView();

        if (mTitle != null) {
            title.setVisibility(View.VISIBLE);
            title.setText(mTitle);
        } else {
            title.setVisibility(View.GONE);
        }

        if (mIcon == -1) {
            ivIcon.setVisibility(View.GONE);
        } else {
            ivIcon.setVisibility(View.VISIBLE);
            ivIcon.setImageResource(mIcon);
        }

        switch (mDialogType) {
            case CONFIRM:
                mContainerInflater.setVisibility(View.GONE);
                accept.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                if (mMessage != null) {
                    message.setVisibility(View.VISIBLE);
                    message.setText(mMessage);
                } else {
                    message.setVisibility(View.GONE);
                }

                accept.setText(mTextPositiveButton);
                accept.setTextColor(mTextColorPositiveButton);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPositiveListener != null)
                            mPositiveListener.onClick(getDialog());
                        dismiss();
                    }
                });

                cancel.setText(mTextNegativeButton);
                cancel.setTextColor(mTextColorNegativeButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mNegativeListener != null)
                            mNegativeListener.onClick(getDialog());
                        dismiss();
                    }
                });
                break;
            case ALERT:
                mContainerInflater.setVisibility(View.GONE);
                accept.setVisibility(View.VISIBLE);
                if (mMessage != null) {
                    message.setText(mMessage);
                    message.setVisibility(View.VISIBLE);
                } else {
                    message.setVisibility(View.GONE);
                }

                accept.setText(mTextPositiveButton);
                accept.setTextColor(mTextColorPositiveButton);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPositiveListener != null)
                            mPositiveListener.onClick(getDialog());
                        dismiss();
                    }
                });
                break;
            case INPUT:
                mContainerInflater.setVisibility(View.GONE);
                if (mMessage != null) {
                    message.setText(mMessage);
                    message.setVisibility(View.VISIBLE);
                } else {
                    message.setVisibility(View.GONE);
                }

                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                View view = inflater1.inflate(R.layout.partial_input, null);
                dialogBuilder.setView(view);

                final EditText input = (EditText) view.findViewById(R.id.input);

                if (mInputHint != null) {
                    input.setHint(mInputHint);
                }
                if (mInputText != null) {
                    //input.setText(FontUtils.typeface(typeface, mInputText));
                    input.setText(mInputText);
                    input.setSelection(input.getText().length());
                }
                if (mInputType != VALUE_NULL) {
                    input.setInputType(mInputType);
                }
                input.setSingleLine(true);
                setupInputFilter(input, mInputMaxLength, mInputPattern);

                input.requestFocus();

                input.addTextChangedListener(
                        new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (mInputPattern != null) {
                                    Matcher matcher = mInputPattern.matcher(editable.toString());
                                    if (!input.getText().toString().isEmpty()) {
                                        if (!matcher.matches()) {
                                            input.setError(getResources().getString(R.string.error));
                                        }
                                    }
                                }
                                final int length = editable.toString().length();
                                if (mAInputListener != null)
                                    mAInputListener.onInput(getDialog(), input.getText().toString(), input);
                            }
                        });

                input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            if (mAInputListener != null)
                                mAInputListener.onInput(getDialog(), input.getText().toString(), input);
                            if (mPositiveListener != null)
                                mPositiveListener.onClick(getDialog());
                        }
                        return false;
                    }
                });

                accept.setText(mTextPositiveButton);
                accept.setTextColor(mTextColorPositiveButton);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAInputListener != null)
                            mAInputListener.onInput(getDialog(), input.getText().toString(), input);
                        if (mPositiveListener != null)
                            mPositiveListener.onClick(getDialog());
                    }
                });

                cancel.setText(mTextNegativeButton);
                cancel.setTextColor(mTextColorNegativeButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mNegativeListener != null)
                            mNegativeListener.onClick(getDialog());
                    }
                });
                break;
            case ITEMS_INPUT:
                mContainerInflater.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.VISIBLE);
                message.setVisibility(View.GONE);
                accept.setVisibility(View.VISIBLE);
                CharSequence[] arrayItemInput = new CharSequence[mListItems.size()];
                arrayItemInput = mListItems.toArray(arrayItemInput);
                Log.e(TAG, "arrayItemInput: " + Arrays.toString(arrayItemInput));
                ArrayList<String> dataListItemInput = new ArrayList<>();
                for (CharSequence c : arrayItemInput) {
                    dataListItemInput.add(c.toString());
                }

                adapter.setData(dataListItemInput);
                Log.e(TAG, "dataListItemInput: " + dataListItemInput);

                inputOthers.setHint(getResources().getString(R.string.field_others_type_pet));

                if (mInputHint != null) {
                    inputOthers.setHint(mInputHint);
                }
                if (mInputText != null) {
                    //inputOthers.setText(FontUtils.typeface(typeface, mInputText));
                    inputOthers.setText(mInputText);
                    inputOthers.setSelection(inputOthers.getText().length());
                }
                if (mInputType != VALUE_NULL) {
                    inputOthers.setInputType(mInputType);
                }
                inputOthers.setSingleLine(true);
                setupInputFilter(inputOthers, mInputMaxLength, mInputPattern);

                inputOthers.requestFocus();

                inputOthers.addTextChangedListener(
                        new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (mInputPattern != null) {
                                    Matcher matcher = mInputPattern.matcher(editable.toString());
                                    if (!inputOthers.getText().toString().isEmpty()) {
                                        if (!matcher.matches()) {
                                            inputOthers.setError(getResources().getString(R.string.error));
                                        }
                                    }
                                }
                                final int length = editable.toString().length();
                                if (mAInputListener != null)
                                    mAInputListener.onInput(getDialog(), inputOthers.getText().toString(), inputOthers);
                            }
                        });

                inputOthers.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            if (mAInputListener != null)
                                mAInputListener.onInput(getDialog(), inputOthers.getText().toString(), inputOthers);
                            if (mPositiveListener != null)
                                mPositiveListener.onClick(getDialog());
                        }
                        return false;
                    }
                });

                accept.setText(mTextPositiveButton);
                accept.setTextColor(mTextColorPositiveButton);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPositiveListener != null)
                            mPositiveListener.onClick(getDialog());
                    }
                });

                cancel.setText(mTextNegativeButton);
                cancel.setTextColor(mTextColorNegativeButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mNegativeListener != null)
                            mNegativeListener.onClick(getDialog());
                    }
                });
                break;
            case SINGLE_CHOICE:
                mContainerInflater.setVisibility(View.GONE);
                CharSequence[] array = new CharSequence[mListItems.size()];
                array = mListItems.toArray(array);

                dialogBuilder.setSingleChoiceItems(
                        array,
                        mSelectedIndexList,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSelectedIndexList = which;
                                CharSequence[] localArray = new CharSequence[mListItems.size()];
                                localArray = mListItems.toArray(localArray);
                                if (mSingleChoiceItemsListener != null)
                                    mSingleChoiceItemsListener.onSelection(
                                            getDialog(), mSelectedIndexList, localArray[mSelectedIndexList]);
                            }
                        });

                accept.setText(mTextPositiveButton);
                accept.setTextColor(mTextColorPositiveButton);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPositiveListener != null)
                            mPositiveListener.onClick(getDialog());
                        dismiss();
                    }
                });

                cancel.setText(mTextNegativeButton);
                cancel.setTextColor(mTextColorNegativeButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mNegativeListener != null)
                            mNegativeListener.onClick(getDialog());
                        dismiss();
                    }
                });
                break;
            case ITEMS:
                mContainerInflater.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                message.setVisibility(View.GONE);
                CharSequence[] array2 = new CharSequence[mListItems.size()];
                array2 = mListItems.toArray(array2);
                Log.e(TAG, "array2: " + Arrays.toString(array2));
                ArrayList<String> dataList = new ArrayList<>();
                for (CharSequence c : array2) {
                    dataList.add(c.toString());
                }

                adapter.setData(dataList);
                Log.e(TAG, "dataList: " + dataList);

                accept.setText(mTextPositiveButton);
                accept.setTextColor(mTextColorPositiveButton);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPositiveListener != null)
                            mPositiveListener.onClick(getDialog());
                    }
                });

                cancel.setText(mTextNegativeButton);
                cancel.setTextColor(mTextColorNegativeButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mNegativeListener != null)
                            mNegativeListener.onClick(getDialog());
                    }
                });
                break;
        }

        dialogBuilder.getContext().setTheme(R.style.Theme_AppCompat_Dialog);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.getContext().setTheme(R.style.Theme_AppCompat_Dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                cancel = ((AlertDialog) dialog).getButton(
                        DialogInterface.BUTTON_NEGATIVE);
                accept = ((AlertDialog) dialog).getButton(
                        DialogInterface.BUTTON_POSITIVE);

                cancel.setVisibility(mTextNegativeButton == null ? View.GONE : View.VISIBLE);
                accept.setVisibility(mTextPositiveButton == null ? View.GONE : View.VISIBLE);

                if (mTextNegativeButton == null
                        && mTextPositiveButton == null)
                    containerButton.setVisibility(View.GONE);
                else
                    containerButton.setVisibility(View.VISIBLE);

                cancel.setTextColor(mTextColorNegativeButton);
                accept.setTextColor(mTextColorPositiveButton);
            }
        });

        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mDialogType == DialogType.INPUT)
            KeyboardUtils.hideKeyboard(getActivity());
        if (mCancelListener != null)
            mCancelListener.onCancel(dialog);

        super.onCancel(dialog);
    }

    @Override
    public void dismiss() {
        if (mDialogType == DialogType.INPUT)
            KeyboardUtils.hideKeyboard(getActivity());
        super.dismiss();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(linearLayoutManager);

        adapter = new DialogDefaultAdapter();
        adapter.setOnListener(new DialogDefaultAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, String item, int position, boolean longPress) {
                if (!longPress)
                    if (mItemsListener != null)
                        mItemsListener.onSelection(
                                getDialog(),
                                position,
                                item);
                dismiss();
                return false;
            }
        });
        recycler.setAdapter(adapter);
    }

    private void setupInputFilter(EditText input, int inputMaxLength, Pattern pattern) {
        if (pattern != null) {
            if (inputMaxLength != Integer.MAX_VALUE)
                input.setFilters(new InputFilter[]{
                        new InputFilter.LengthFilter(inputMaxLength),
                        //new InputFilter.AllCaps(),
                        new PatternInputFilter(pattern)
                });
            else
                input.setFilters(new InputFilter[]{
                        //new InputFilter.AllCaps(),
                        new PatternInputFilter(pattern)
                });
        } else {
            if (inputMaxLength != Integer.MAX_VALUE)
                input.setFilters(new InputFilter[]{
                        new InputFilter.LengthFilter(inputMaxLength),
                        //new InputFilter.AllCaps()
                });
            else
                input.setFilters(new InputFilter[]{
                        //new InputFilter.AllCaps()
                });
        }
    }

    public static class Builder {

        private OnSingleButtonListener onPositiveCallback;
        private OnSingleButtonListener onNegativeCallback;
        private OnSingleButtonListener onAnyCallback;
        private OnInputListener inputCallback;
        private OnItemsListener listCallback;
        private OnSingleChoiceItemsListener listCallbackSingleChoice;
        private OnCancelListener onCancelListener;

        private ArrayList<CharSequence> items;
        private int selectedIndex = -1;

        private CharSequence inputText;
        private CharSequence inputHint;
        private int inputType;
        private int inputMaxLength;
        private Pattern inputPattern;

        private Context context;
        private String title, message, textNegativeButton, textPositiveButton;
        private int icon = -1;
        private int textColorNegativeButton, textColorPositiveButton;
        private boolean isCancelable;
        private DialogType dialogType;

        private View view;

        public Builder(@NonNull Context context) {
            this.context = context;
            this.isCancelable = true;
            this.textColorPositiveButton = context.getResources().getColor(R.color.colorAccent);
            this.textColorNegativeButton = context.getResources().getColor(R.color.secondary_text_selector_light);
            this.inputHint = null;
            this.inputText = null;
            this.inputType = VALUE_NULL;
            this.inputMaxLength = Integer.MAX_VALUE;
            this.inputPattern = null;
            this.icon = -1;
        }

        public Builder setIcon(@DrawableRes int icon) {
            this.icon = icon;
            return this;
        }

        public Builder title(@StringRes int stringRes) {
            title(this.context.getString(stringRes));
            return this;
        }

        public Builder title(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder message(@StringRes int stringRes) {
            message(this.context.getString(stringRes));
            return this;
        }

        public Builder message(@NonNull String message) {
            this.message = message;
            return this;
        }

        public Builder textNegativeButton(@StringRes int stringRes) {
            textNegativeButton(this.context.getString(stringRes));
            return this;
        }

        public Builder textNegativeButton(@NonNull String textNegativeButton) {
            this.textNegativeButton = textNegativeButton;
            return this;
        }

        public Builder textPositiveButton(@StringRes int stringRes) {
            textPositiveButton(this.context.getString(stringRes));
            return this;
        }

        public Builder textPositiveButton(@NonNull String textPositiveButton) {
            this.textPositiveButton = textPositiveButton;
            return this;
        }

        public Builder textColorNegativeButton(@ColorRes int color) {
            this.textColorNegativeButton = this.context.getResources().getColor(color);
            return this;
        }

        public Builder textColorPositiveButton(@ColorRes int color) {
            this.textColorPositiveButton = this.context.getResources().getColor(color);
            return this;
        }

        public Builder dialogType(DialogType dialogType) {
            this.dialogType = dialogType;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public Builder customView(View view) {
            this.view = view;
            return this;
        }

        public Builder onPositive(@NonNull OnSingleButtonListener callback) {
            this.onPositiveCallback = callback;
            return this;
        }

        public Builder onNegative(@NonNull OnSingleButtonListener callback) {
            this.onNegativeCallback = callback;
            return this;
        }

        public Builder onAny(@NonNull OnSingleButtonListener callback) {
            this.onAnyCallback = callback;
            return this;
        }

        public Builder onInputCallback(@NonNull OnInputListener callback) {
            this.inputCallback = callback;
            return this;
        }

        public Builder inputType(int type) {
            this.inputType = type;
            return this;
        }

        public Builder inputPattern(Pattern inputPattern) {
            this.inputPattern = inputPattern;
            return this;
        }

        public Builder inputPattern(@Nullable String inputPattern) {
            return inputPattern(Pattern.compile(inputPattern));
        }

        public Builder inputPattern(@StringRes int inputPattern) {
            return inputPattern(inputPattern == 0 ? null : context.getResources().getString(inputPattern));
        }

        public Builder input(@Nullable CharSequence hint, @Nullable CharSequence inputText, int inputMaxLength) {
            this.inputHint = hint;
            this.inputText = inputText;
            this.inputMaxLength = inputMaxLength;
            return this;
        }

        public Builder input(@StringRes int hint, @StringRes int inputText, int maxLength) {
            return input(hint == VALUE_NULL ? null : context.getText(hint),
                    inputText == VALUE_NULL ? null : context.getText(inputText),
                    maxLength == VALUE_NULL ? Integer.MAX_VALUE : maxLength);
        }


        public Builder items(@NonNull Collection collection) {
            if (collection.size() > 0) {
                final CharSequence[] array = new CharSequence[collection.size()];
                int i = 0;
                for (Object obj : collection) {
                    array[i] = obj.toString();
                    i++;
                }
                items(array);
            } else if (collection.size() == 0) {
                items = new ArrayList<>();
            }
            return this;
        }

        public Builder items(@ArrayRes int itemsRes) {
            items(this.context.getResources().getTextArray(itemsRes));
            return this;
        }

        public Builder items(@NonNull CharSequence... items) {
            this.items = new ArrayList<>();
            Collections.addAll(this.items, items);
            return this;
        }

        public Builder itemsCallback(@NonNull OnItemsListener callback) {
            this.listCallback = callback;
            this.listCallbackSingleChoice = null;
            return this;
        }

        /**
         * Pass anything below 0 (such as -1) for the selected index to leave all options unselected
         * initially. Otherwise pass the index of an item that will be selected initially.
         *
         * @param selectedIndex The checkbox index that will be selected initially.
         * @param callback      The callback that will be called when the presses the positive button.
         * @return The Builder instance so you can chain calls to it.
         */
        public Builder itemsCallbackSingleChoice(int selectedIndex,
                                                 @NonNull OnSingleChoiceItemsListener callback) {
            this.selectedIndex = selectedIndex;
            this.listCallback = null;
            this.listCallbackSingleChoice = callback;
            return this;
        }

        public Builder onCancelListener(@NonNull OnCancelListener callback) {
            this.onCancelListener = callback;
            return this;
        }

        /**
         * Configura el dialogo con los datos ingresados. IMPORTANTE: No hacer llamado a este metodo si
         * es que se llama a {@link Builder#buildAndShow}.
         *
         * @return {@link DefaultDialog}.
         */
        @UiThread
        public DefaultDialog build() {
            return new DefaultDialog(this);
        }

        /**
         * Muestra el dialogo evitando alguna excepcion. IMPORTANTE: No hacer llamado a este método si
         * es que se llama a {@link Builder#build}.
         *
         * @param fragmentManager {@link FragmentManager}.
         * @param tag             Tag.
         */
        public void buildAndShow(FragmentManager fragmentManager, String tag) {
            Fragment frag = fragmentManager.findFragmentByTag(tag);
            if (frag != null) {
                ((DefaultDialog) frag).dismiss();
            }
            try {
                build().show(fragmentManager, tag);
            } catch (IllegalStateException e) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(build(), tag);
                ft.commitAllowingStateLoss();
            }
        }
    }

    public interface OnItemsListener {
        void onSelection(DialogInterface dialog, int which, CharSequence text);
    }

    public interface OnSingleChoiceItemsListener {
        boolean onSelection(DialogInterface dialog, int which, CharSequence text);
    }

    public interface OnSingleButtonListener {
        void onClick(@NonNull DialogInterface dialog);
    }

    public interface OnInputListener {
        void onInput(@NonNull DialogInterface dialog, CharSequence input, EditText editText);
    }

    public interface OnCancelListener {
        void onCancel(@NonNull DialogInterface dialog);
    }
}
