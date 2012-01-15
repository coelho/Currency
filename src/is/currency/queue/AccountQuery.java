package is.currency.queue;

public abstract class AccountQuery implements Runnable {

	private boolean completed;

	public AccountQuery() {
		this.completed = false;
	}

	public final boolean execute() {
		if(this.completed) {
			return false;
		}
		try {
			this.run();
		} catch(Throwable throwable) {
			throwable.printStackTrace();
		}
		this.completed = true;
		return true;
	}

	public final void awaitUninterruptedly() {
		try {
			while(!this.completed) {
				Thread.sleep(1);
			}
		} catch(Throwable throwable) {
			throwable.printStackTrace();
		}
	}

}
