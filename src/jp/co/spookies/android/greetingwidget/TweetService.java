package jp.co.spookies.android.greetingwidget;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class TweetService extends Service {
	private String toastMessage = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		// twitter”FØŽž‚É“o˜^‚µ‚½tokenAtoken_secret‚ðŽæ“¾
		final String token = prefs.getString("token", "");
		final String tokenSecret = prefs.getString("token_secret", "");
		if (token.length() > 0 && tokenSecret.length() > 0) {
			final String message = intent.getStringExtra("message");
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						Twitter twitter = new TwitterFactory().getInstance();
						twitter.setOAuthConsumer(
								getString(R.string.consumer_key),
								getString(R.string.consumer_secret));
						twitter.setOAuthAccessToken(new AccessToken(token,
								tokenSecret));
						twitter.updateStatus(message);
						toastMessage = getString(R.string.message_success);
					} catch (TwitterException e) {
						e.printStackTrace();
						toastMessage = getString(R.string.message_error);
					}
					TweetService.this.stopSelf();
				}
			};
			thread.start();
		} else {
			Intent newIntent = new Intent(this, LoginActivity.class);
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(newIntent);
			stopSelf();
		}
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		if (toastMessage != null) {
			Toast.makeText(TweetService.this, toastMessage, Toast.LENGTH_LONG)
					.show();
		}
	}
}
