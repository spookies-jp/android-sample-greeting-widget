package jp.co.spookies.android.greetingwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GreetingWidgetConfigureActivity extends Activity {
	private int widgetId;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setResult(RESULT_CANCELED);
		setContentView(R.layout.configure);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
	}

	public void onCreateButton1Clicked(View view) {
		onCreateButtonClicked(ButtonType.RED);
	}

	public void onCreateButton2Clicked(View view) {
		onCreateButtonClicked(ButtonType.YELLOW);
	}

	public void onCreateButton3Clicked(View view) {
		onCreateButtonClicked(ButtonType.GREEN);
	}

	/**
	 * ボタン押下時の処理
	 * 入力されたメッセージと選択されたボタンタイプで、ウイジェットを作成
	 * @param buttonType
	 */
	public void onCreateButtonClicked(ButtonType buttonType) {
		EditText editText = (EditText) findViewById(R.id.message_text);
		String message = editText.getText().toString();
		GreetingWidgetProvider.createWidget(this,
				AppWidgetManager.getInstance(this), widgetId, message,
				buttonType);
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		setResult(RESULT_OK, resultValue);
		finish();
	}
}
