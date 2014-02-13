package actors.messages;

public  class UpdateCursorEvent implements IndexEvent {
	private String cursor;

	public UpdateCursorEvent(String cursor) {
		super();
		this.cursor = cursor;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
