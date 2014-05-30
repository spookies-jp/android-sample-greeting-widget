package jp.co.spookies.android.greetingwidget;

public enum ButtonType {
	RED(1, R.layout.widget_red), YELLOW(2, R.layout.widget_yellow), GREEN(3,
			R.layout.widget_green);

	int id;
	int layoutId;

	private ButtonType(int id, int layoutId) {
		this.id = id;
		this.layoutId = layoutId;
	}

	public static ButtonType getById(int id) {
		for (ButtonType buttonType : ButtonType.values()) {
			if (buttonType.id == id) {
				return buttonType;
			}
		}
		return null;
	}
}
