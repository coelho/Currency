package is.currency.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import is.currency.Currency;

public class AccountQueue implements Runnable {

	@SuppressWarnings("unused")
	private Currency currency;
	private BlockingQueue<AccountQuery> queue;

	public AccountQueue(Currency currency) {
		this.currency = currency;
		this.queue = new LinkedBlockingQueue<AccountQuery>();
	}

	public void run() {
		try {
			while(true) {
				AccountQuery query = null;
				while((query = this.queue.poll()) != null) {
					query.execute();
				}
				Thread.sleep(5);
			}
		} catch(InterruptedException exception) {
			//ignore
		} catch(Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public void submit(AccountQuery query) {
		this.queue.add(query);
	}

}
