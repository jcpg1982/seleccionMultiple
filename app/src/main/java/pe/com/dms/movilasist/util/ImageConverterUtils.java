package pe.com.dms.movilasist.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.FloatRange;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pe.com.dms.movilasist.helpers.StorageManager;

import static pe.com.dms.movilasist.util.ImageConverterUtils.ScalingLogic.CROP;

public class ImageConverterUtils {

    static String TAG = ImageConverterUtils.class.getSimpleName();
    private static int dstWidth;
    private static int dstHeight;

    public static Bitmap fileToBitmap(File file) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(file.getPath());
        } catch (OutOfMemoryError | Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap uriToBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (OutOfMemoryError | Exception e) {
            e.printStackTrace();
            try {
                bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
            } catch (OutOfMemoryError | Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }

    public static Uri bitmapToUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap getBitmapForVectorDrawable(VectorDrawable vectorDrawable) {
        Bitmap bitmap =
                Bitmap.createBitmap(
                        vectorDrawable.getIntrinsicWidth(),
                        vectorDrawable.getIntrinsicHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap drawableToBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        return drawableToBitmap(drawable);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (drawable instanceof VectorDrawable)
                return getBitmapForVectorDrawable((VectorDrawable) drawable);
            else
                throw new IllegalArgumentException("unsupported drawable type");
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Bitmap bitmap;
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    /**
     * Bitmap image from the URL received.
     *
     * @param imageUrl Url
     * @return {@link Bitmap}
     */
    public static Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Utility function for decoding an image resource. The decoded bitmap will
     * be optimized for further scaling to the requested destination dimensions
     * and scaling logic.
     *
     * @param res          The resources object containing the image data
     * @param resId        The resource id of the image data
     * @param dstWidth     Width of destination area
     * @param dstHeight    Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Decoded bitmap
     */
    public static Bitmap decodeResource(Resources res, int resId, int dstWidth, int dstHeight,
                                        ScalingLogic scalingLogic) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth,
                dstHeight, scalingLogic);
        Bitmap unscaledBitmap = BitmapFactory.decodeResource(res, resId, options);

        return unscaledBitmap;
    }

    public static Bitmap decodeFile(String path, int dstWidth, int dstHeight,
                                    ScalingLogic scalingLogic) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth,
                dstHeight, scalingLogic);
        Bitmap unscaledBitmap = BitmapFactory.decodeFile(path, options);

        return unscaledBitmap;
    }


    /**
     * Utility function for creating a scaled version of an existing bitmap
     *
     * @param unscaledBitmap Bitmap to scale
     * @param dstWidth       Wanted width of destination bitmap
     * @param dstHeight      Wanted height of destination bitmap
     * @param scalingLogic   Logic to use to avoid image stretching
     * @return New scaled bitmap object
     */
    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight,
                                            ScalingLogic scalingLogic) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight, scalingLogic);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight, scalingLogic);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight,
                                            ScalingLogic scalingLogic, Matrix matrix) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight, scalingLogic);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight, scalingLogic);
        /*Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(),
                Config.ARGB_8888);*/
        Bitmap scaledBitmap = Bitmap.createBitmap(unscaledBitmap, 0, 0, dstWidth,
                dstHeight, matrix, true);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    /**
     * ScalingLogic defines how scaling should be carried out if source and
     * destination image has different aspect ratio.
     * <p>
     * CROP: Scales the image the minimum amount while making sure that at least
     * one of the two dimensions fit inside the requested destination area.
     * Parts of the source image will be cropped to realize this.
     * <p>
     * FIT: Scales the image the minimum amount while making sure both
     * dimensions fit inside the requested destination area. The resulting
     * destination dimensions might be adjusted to a smaller size than
     * requested.
     */
    public static enum ScalingLogic {
        CROP, FIT
    }

    /**
     * Calculate optimal down-sampling factor given the dimensions of a source
     * image, the dimensions of a destination area and a scaling logic.
     *
     * @param srcWidth     Width of source image
     * @param srcHeight    Height of source image
     * @param dstWidth     Width of destination area
     * @param dstHeight    Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal down scaling sample size for decoding
     */
    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth,
                                          int dstHeight,
                                          ScalingLogic scalingLogic) {
        final float srcAspect = (float) srcWidth / (float) srcHeight;
        final float dstAspect = (float) dstWidth / (float) dstHeight;
        switch (scalingLogic) {
            case FIT:
                if (srcAspect > dstAspect) {
                    return srcWidth / dstWidth;
                } else {
                    return srcHeight / dstHeight;
                }
            case CROP:
                if (srcAspect > dstAspect) {
                    return srcHeight / dstHeight;
                } else {
                    return srcWidth / dstWidth;
                }

        }
        return 1;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final float height = options.outHeight;
        final float width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final float halfHeight = height / 2;
            final float halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    /**
     * Calculates source rectangle for scaling bitmap
     *
     * @param srcWidth     Width of source image
     * @param srcHeight    Height of source image
     * @param dstWidth     Width of destination area
     * @param dstHeight    Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal source rectangle
     */
    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth,
                                        int dstHeight,
                                        ScalingLogic scalingLogic) {
        switch (scalingLogic) {
            case CROP:
                final float srcAspect = (float) srcWidth / (float) srcHeight;
                final float dstAspect = (float) dstWidth / (float) dstHeight;

                if (srcAspect > dstAspect) {
                    final int srcRectWidth = (int) (srcHeight * dstAspect);
                    final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
                    return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
                } else {
                    final int srcRectHeight = (int) (srcWidth / dstAspect);
                    final int scrRectTop = (int) (srcHeight - srcRectHeight) / 2;
                    return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
                }
            case FIT:
                return new Rect(0, 0, srcWidth, srcHeight);
        }
        return null;
    }

    /**
     * Calculates destination rectangle for scaling bitmap
     *
     * @param srcWidth     Width of source image
     * @param srcHeight    Height of source image
     * @param dstWidth     Width of destination area
     * @param dstHeight    Height of destination area
     * @param scalingLogic Logic to use to avoid image stretching
     * @return Optimal destination rectangle
     */
    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth,
                                        int dstHeight,
                                        ScalingLogic scalingLogic) {
        switch (scalingLogic) {
            case FIT:
                final float srcAspect = (float) srcWidth / (float) srcHeight;
                final float dstAspect = (float) dstWidth / (float) dstHeight;

                if (srcAspect > dstAspect) {
                    return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
                } else {
                    return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
                }
            case CROP:
                return new Rect(0, 0, dstWidth, dstHeight);
        }
        return null;
    }

    public static String decodeFile(Context context, String path,
                                    int desiredWidth, int desiredHeight, int id) {
        String strMyImagePath = null;
        Bitmap scaledBitmap;

        // Store to tmp file
        File mFolder = StorageManager.Folder.getCacheFolder(context);
        if (!mFolder.exists()) mFolder.mkdir();

        //String name = "avatar_tmp.jpg";
        String name = StorageManager.generateFilenameId(
                StorageManager.StoragePrefix.IMAGE, id,
                FileUtils.getExtension(path));

        File file = new File(mFolder.getAbsolutePath(), name);
        Matrix matrix = new Matrix();
        matrix.postRotate(0);

        try {
            // Part 1: Decode image
            scaledBitmap = decodeFile(path, desiredWidth, desiredHeight,
                    ImageConverterUtils.ScalingLogic.FIT);
            if (!(scaledBitmap.getWidth() <= desiredWidth
                    && scaledBitmap.getHeight() <= desiredHeight)) {
                // Part 2: Scale image
                scaledBitmap = createScaledBitmap(scaledBitmap, desiredWidth, desiredHeight,
                        ImageConverterUtils.ScalingLogic.FIT, matrix);
            } else {
                scaledBitmap.recycle();
                return path;
            }

            strMyImagePath = file.getAbsolutePath();
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (strMyImagePath == null)
            return path;
        return strMyImagePath;
    }

    public static File decodeFile(Context context, String path,
                                  int width, int height,
                                  String namePhoto) {
        Bitmap scaledBitmap;
        Log.e(TAG, "path recibido: " + path);

        File folder = StorageManager.Folder.getCacheFolder(context);
        if (!folder.exists()) folder.mkdirs();

        Log.e(TAG, "folder creado: " + folder);

        String nameTemp = StorageManager.generateNamePhoto(
                namePhoto,
                FileUtils.getExtension(path));

        Log.e(TAG, "nameTemp creado: " + nameTemp);

        File file = new File(folder.getAbsolutePath(), nameTemp);

        Log.e(TAG, "file creado: " + file);

        float rotate = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                rotate = 0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = -270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = -180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = -90;
                break;
        }
        Log.e(TAG, "orientation : " + orientation);
        Log.e(TAG, "rotate: " + rotate);

        try {
            scaledBitmap = rotateBitmap(fileToBitmap(new File(path)), rotate);
            Log.e(TAG, "scaledBitmap Width:" + scaledBitmap.getWidth());
            Log.e(TAG, "scaledBitmap Height:" + scaledBitmap.getHeight());
            if (!(scaledBitmap.getWidth() <= width
                    && scaledBitmap.getHeight() <= height)) {
                scaledBitmap = createScaledBitmap(scaledBitmap, width, height,
                        CROP);
                Log.e(TAG, "scaledBitmap new Width:" + scaledBitmap.getWidth());
                Log.e(TAG, "scaledBitmap new Height:" + scaledBitmap.getHeight());
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                return file;
            } catch (FileNotFoundException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, @FloatRange(from = 0.0, to = 360.0) float degree) {
        if (bitmap != null && degree != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(-degree);
            try {
                bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        matrix,
                        true);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return bitmap;
    }


    public static String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm = Bitmap
        byte[] byteBitmap = baos.toByteArray();
        bitmap.recycle();
        return Base64.encodeToString(byteBitmap, Base64.DEFAULT);
    }

    public static Bitmap resizeBitmap(Context context, Uri uri,
                                      int width, int height) {
        Bitmap bitmapPath;
        InputStream is = null;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            is = context.getContentResolver().openInputStream(uri);
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver()
                    .openInputStream(uri), null, options);
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / width),
                    Math.ceil(options.outHeight / height));
            options.inJustDecodeBounds = false;
            bitmapPath = BitmapFactory.decodeStream(context.getContentResolver()
                    .openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            bitmapPath = BitmapFactory.decodeStream(is);
            e.printStackTrace();
        }
        return bitmapPath;
    }
}