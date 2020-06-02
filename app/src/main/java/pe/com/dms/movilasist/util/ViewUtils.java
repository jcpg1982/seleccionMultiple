package pe.com.dms.movilasist.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EdgeEffect;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.EdgeEffectCompat;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import pe.com.dms.movilasist.R;

public class ViewUtils {
    public static int getStatusBarHeight(Context context) {
        final Resources r = context.getResources();
        int resourceId = r.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) return r.getDimensionPixelSize(resourceId);
        return 0;
    }

    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarHeight;
    }

    public static int getNavBarHeight(Context context) {
        return getNavigationBarSize(context).y;
    }

    public static Point getNavigationBarSize(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the right
        if (appUsableSize.x < realScreenSize.x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }

        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }

        // navigation bar is not present
        return new Point();
    }

    private static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        return size;
    }

    /**
     * Calcula la altura del navBar si lo tiene.
     *
     * @param context Activity.
     * @return 0 si no tiene navBar o el la altura si lo tiene.
     * @see <a
     * href='http://stackoverflow.com/questions/20264268/how-to-get-height-and-width-of-navigation-bar-programmatically/'>Obtiene
     * height navBar</a>
     */
    @Deprecated
    public static int getNavBarHeight(Activity context) {
        /*if (context == null || context.isFinishing()) {
            return 0;
        } else if (!context.getResources().getBoolean(R.bool.translucent_nav)) {
            // Translucent nav is disabled
            return 0;
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (!hasMenuKey || !hasBackKey) {
                //The device has a navigation bar
                Resources resources = context.getResources();

                int orientation = context.getResources().getConfiguration().orientation;
                int resourceId;
                if (isTablet(context)) {
                    resourceId = resources.getIdentifier(
                            orientation == Configuration.ORIENTATION_PORTRAIT
                                    ? "navigation_bar_height"
                                    : "navigation_bar_height_landscape",
                            "dimen", "android");
                } else {
                    resourceId = resources.getIdentifier(
                            orientation == Configuration.ORIENTATION_PORTRAIT
                                    ? "navigation_bar_height"
                                    : "navigation_bar_width",
                            "dimen", "android");
                }

                if (resourceId > 0) {
                    return context.getResources().getDimensionPixelSize(resourceId);
                }
            }
        }

        return 0;
    }

    private static boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void setOverflowButtonColor(@NonNull Activity activity, final @ColorInt int color) {
        final String overflowDescription =
                activity.getString(R.string.abc_action_menu_overflow_description);
        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        final ArrayList<View> outViews = new ArrayList<>();
                        decorView.findViewsWithText(
                                outViews, overflowDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                        if (outViews.isEmpty()) return;
                        if (outViews.get(0) instanceof AppCompatImageView) {
                            final AppCompatImageView overflow = (AppCompatImageView) outViews.get(0);
                            overflow.setImageDrawable(
                                    TintUtils.createTintedDrawable(overflow.getDrawable(), color));
                            removeOnGlobalLayoutListener(decorView, this);
                        }
                    }
                });
    }

    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings("deprecation")
    private static void removeOnGlobalLayoutListener(
            View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    public static Drawable createCardSelector(Context context) {
        final int accentColor = ResolveUtils.resolveColor(context, R.attr.colorAccent);
        final int activated = ColorUtils.adjustAlpha(accentColor, 0.5f);
        final int pressed = ColorUtils.adjustAlpha(accentColor, 0.75f);

        final StateListDrawable baseSelector = new StateListDrawable();
        baseSelector.addState(new int[]{android.R.attr.state_activated}, new ColorDrawable(activated));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(
                    ColorStateList.valueOf(accentColor), baseSelector, new ColorDrawable(Color.WHITE));
        }

        baseSelector.addState(new int[]{}, new ColorDrawable(Color.TRANSPARENT));
        baseSelector.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressed));
        return baseSelector;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void setLightStatusBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        int flags = view.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        view.setSystemUiVisibility(flags);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void clearLightStatusBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        int flags = view.getSystemUiVisibility();
        flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        view.setSystemUiVisibility(flags);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTaskDescriptionColor(@NonNull Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
        color = ColorUtils.stripAlpha(color);
        Bitmap icon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            icon = getAppIcon(activity.getPackageManager(), activity.getPackageName());
        } else {
            icon = ((BitmapDrawable) activity.getApplicationInfo()
                    .loadIcon(activity.getPackageManager())).getBitmap();
        }
        if (icon != null) {
            ActivityManager.TaskDescription td =
                    new ActivityManager.TaskDescription((String) activity.getTitle(), icon, color);
            activity.setTaskDescription(td);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Bitmap getAppIcon(PackageManager mPackageManager, String packageName) {
        try {
            Drawable drawable = mPackageManager.getApplicationIcon(packageName);

            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof AdaptiveIconDrawable) {
                Drawable backgroundDr = ((AdaptiveIconDrawable) drawable).getBackground();
                Drawable foregroundDr = ((AdaptiveIconDrawable) drawable).getForeground();

                Drawable[] drr = new Drawable[2];
                drr[0] = backgroundDr;
                drr[1] = foregroundDr;

                LayerDrawable layerDrawable = new LayerDrawable(drr);

                int width = layerDrawable.getIntrinsicWidth();
                int height = layerDrawable.getIntrinsicHeight();

                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);

                layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                layerDrawable.draw(canvas);

                return bitmap;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void translucentStatusBar(Activity activity) {
        setupStatusBar(activity, activity.getResources().getColor(R.color.overlay_dark));
    }

    public static void transparentStatusBar(Activity activity) {
        setupStatusBar(activity, Color.TRANSPARENT);
    }

    private static void setupStatusBar(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            activity.getWindow().setStatusBarColor(color);
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void setViewPagerEdgeEffectColor(ViewPager viewPager, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field field = ViewPager.class.getDeclaredField("mLeftEdge");
                field.setAccessible(true);
                EdgeEffectCompat mLeftEdge = (EdgeEffectCompat) field.get(viewPager);
                if (mLeftEdge != null) {
                    field = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");
                    field.setAccessible(true);
                    EdgeEffect mEdgeEffect = (EdgeEffect) field.get(mLeftEdge);
                    if (mEdgeEffect != null) {
                        mEdgeEffect.setColor(color);
                    }
                }

                field = ViewPager.class.getDeclaredField("mRightEdge");
                field.setAccessible(true);
                EdgeEffectCompat mRightEdge = (EdgeEffectCompat) field.get(viewPager);
                if (mRightEdge != null) {
                    field = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");
                    field.setAccessible(true);
                    EdgeEffect mEdgeEffect = (EdgeEffect) field.get(mRightEdge);
                    if (mEdgeEffect != null) {
                        mEdgeEffect.setColor(color);
                    }
                }
            } catch (Exception e) {
                //LogUtils.throwable(e);
            }
        }
    }

    public static void setScrollViewEdgeEffectColor(ScrollView scrollView, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field field = ScrollView.class.getDeclaredField("mEdgeGlowTop");
                field.setAccessible(true);
                EdgeEffect mEdgeGlowTop = (EdgeEffect) field.get(scrollView);
                if (mEdgeGlowTop != null) {
                    mEdgeGlowTop.setColor(color);
                }

                field = ScrollView.class.getDeclaredField("mEdgeGlowBottom");
                field.setAccessible(true);
                EdgeEffect mEdgeGlowBottom = (EdgeEffect) field.get(scrollView);
                if (mEdgeGlowBottom != null) {
                    mEdgeGlowBottom.setColor(color);
                }
            } catch (Exception e) {
                Log.e("", "" + e);
            }
        }
    }

    public static void shakeView(final View view, final float x, final int num) {
        if (num == 6) {
            view.setTranslationX(0);
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationX",
                DisplayUtil.dpToPx(view.getContext(), x)));
        animatorSet.setDuration(50);
        animatorSet.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        shakeView(view, num == 5 ? 0 : -x, num + 1);
                    }
                });
        animatorSet.start();
    }

    /**
     * Habilita o desabilita los controles de una vista contenedora.
     *
     * @param enable    si es tru Habilita, false desabilita.
     * @param viewGroup Vista contenedora de los controles a procesar.
     */
    public static void disableEnableControls(boolean enable, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    /**
     * Sets the margins for a given view
     *
     * @param view   View to set the margin for
     * @param left   left margin, in pixels
     * @param top    top margin, in pixels
     * @param right  right margin, in pixels
     * @param bottom bottom margin, in pixels
     */
    public static void setMargins(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        p.setMargins(left, top, right, bottom);
        view.setLayoutParams(p);
    }

    public static void justifyTextView(final TextView textView) {
        final AtomicBoolean isJustify = new AtomicBoolean(false);
        final String textString = textView.getText().toString();
        final TextPaint textPaint = textView.getPaint();
        CharSequence textViewText = textView.getText();
        final Spannable builder = textViewText instanceof Spannable ?
                (Spannable) textViewText :
                new SpannableString(textString);
        textView.post(new Runnable() {
            @Override
            public void run() {
                if (!isJustify.get()) {
                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();
                    for (int i = 0; i < lineCount; i++) {
                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);
                        String lineString = textString.substring(lineStart, lineEnd);
                        if (i == lineCount - 1) {
                            break;
                        }
                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");
                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();
                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;
                        Set<Integer> endsSpace = spacePositionInEnds(lineString);
                        for (int j = 0; j < lineString.length(); j++) {
                            char c = lineString.charAt(j);
                            Drawable drawable = new ColorDrawable(0x00ffffff);
                            if (c == ' ') {
                                if (endsSpace.contains(j)) {
                                    drawable.setBounds(0, 0, 0, 0);
                                } else {
                                    drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                }
                                ImageSpan span = new ImageSpan(drawable);
                                builder.setSpan(span, lineStart + j, lineStart + j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }
                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }

    private static Set<Integer> spacePositionInEnds(String string) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == ' ') {
                result.add(i);
            } else {
                break;
            }
        }

        if (result.size() == string.length()) {
            return result;
        }

        for (int i = string.length() - 1; i > 0; i--) {
            char c = string.charAt(i);
            if (c == ' ') {
                result.add(i);
            } else {
                break;
            }
        }
        return result;
    }
}
