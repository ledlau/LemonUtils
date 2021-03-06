package android.lemon.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;

/**
 * <p>All methods requires the caller to hold the permission
 * {@link android.Manifest.permission#VIBRATE}.
 *
 * @author Kevin
 * @date 2014-11-21
 */
public class VibrateUtils {

    /**
     * Vibrate constantly for the specified period of time.
     * <p>This method requires the caller to hold the permission
     * {@link android.Manifest.permission#VIBRATE}.
     *
     * @param milliseconds The number of milliseconds to vibrate.
     */
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    /**
     * Vibrate with a given pattern.
     * <p/>
     * <p>
     * Pass in an array of ints that are the durations for which to turn on or off
     * the vibrator in milliseconds.  The first value indicates the number of milliseconds
     * to wait before turning the vibrator on.  The next value indicates the number of milliseconds
     * for which to keep the vibrator on before turning it off.  Subsequent values alternate
     * between durations in milliseconds to turn the vibrator off or to turn the vibrator on.
     * </p><p>
     * To cause the pattern to repeat, pass the index into the pattern array at which
     * to start the repeat, or -1 to disable repeating.
     * </p>
     * <p>This method requires the caller to hold the permission
     * {@link android.Manifest.permission#VIBRATE}.
     *
     * @param pattern an array of longs of times for which to turn the vibrator on or off.
     * @param repeat  the index into pattern at which to repeat, or -1 if
     *                you don't want to repeat.
     */
    public static void vibrate(Context context, long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeat);
    }
    
	public static boolean vibratorRun = true;
	private static Vibrator vibrator;
	private static int playCount = -1;

	/**
	 * 震动 只震动一次 为-1时震动一次 为1时震动完设定的，再反复震动 为2时 反复震动数组中的内容 震动是会阻塞界面。
	 * 
	 * @param context
	 */
	public static void VibrateStart(final Context context,int count) {
		if(vibratorRun) {
			playCount = count;
			new Thread() {

				@TargetApi(Build.VERSION_CODES.HONEYCOMB)
				public void run() {
					VibrateUtils.stop();
					vibrator = (Vibrator) context
							.getSystemService(Context.VIBRATOR_SERVICE);

					if (!vibrator.hasVibrator())
						return;
					long[] pattern = {200, 200, 200, 200}; // 停止 开启 停止 开启
					vibrator.vibrate(pattern, playCount); // 重复两次上面的pattern
					// 如果只想震动一次，index设为-1
				}

			}.start();
		}
	}
	
	/**
	 * 震动 只震动一次 为-1时震动一次 为1时震动完设定的，再反复震动 为2时 反复震动数组中的内容 震动是会阻塞界面。
	 * 
	 * @param context
	 */
	public static void VibrateStart(final Context context) {
		if(vibratorRun) {
			playCount = -1;
			new Thread() {
				@TargetApi(Build.VERSION_CODES.HONEYCOMB)
				public void run() {
					VibrateUtils.stop();
					vibrator = (Vibrator) context
							.getSystemService(Context.VIBRATOR_SERVICE);

					if (!vibrator.hasVibrator())
						return;
					long[] pattern = {200, 200, 200, 200}; // 停止 开启 停止 开启
					vibrator.vibrate(pattern, playCount); // 重复两次上面的pattern
					// 如果只想震动一次，index设为-1
				}

			}.start();
		}
	}

	/**
	 * 停止震动
	 */
	public static void stop() {
		// &&vibrator.hasVibrator()
		if(vibratorRun) {
			if (vibrator != null) {
				vibrator.cancel();
				System.gc();
			}
		}
	}

}
