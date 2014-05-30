package jp.co.spookies.android.greetingwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

public class GreetingWidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			updateWidget(context, appWidgetManager, appWidgetId);
		}
	}

	public static void createWidget(Context context, AppWidgetManager manager,
			int widgetId, String message, ButtonType buttonType) {
		saveWidget(context, widgetId, message, buttonType.id);
		updateWidget(context, manager, widgetId);
	}

	public static void updateWidget(Context context, AppWidgetManager manager,
			int widgetId) {
		String message = getWidgetMessage(context, widgetId);
		Intent intent = new Intent(context, TweetService.class);
		intent.putExtra("message", message);
		PendingIntent pendingIntent = PendingIntent.getService(context,
				widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		RemoteViews view = new RemoteViews(context.getPackageName(),
				getWidgetLayoutId(context, widgetId));
		view.setOnClickPendingIntent(R.id.tweet_button, pendingIntent);
		view.setCharSequence(R.id.message, "setText", message);
		manager.updateAppWidget(widgetId, view);
	}

	private static String getWidgetMessage(Context context, int widgetId) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String key = getMessageKey(widgetId);
		return prefs.getString(key, "");
	}

	private static int getWidgetLayoutId(Context context, int widgetId) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String key = getButtonTypeIdKey(widgetId);
		ButtonType buttonType = ButtonType.getById(prefs.getInt(key, 1));
		return buttonType.layoutId;
	}

	private static void saveWidget(Context context, int widgetId,
			String message, int buttonTypeId) {
		Editor prefs = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
		prefs.putString(getMessageKey(widgetId), message);
		prefs.putInt(getButtonTypeIdKey(widgetId), buttonTypeId);
		prefs.commit();
	}

	private static String getMessageKey(int widgetId) {
		return "message_" + widgetId;
	}

	private static String getButtonTypeIdKey(int widgetId) {
		return "drawable_" + widgetId;
	}
}
