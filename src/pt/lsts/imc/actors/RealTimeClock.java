package pt.lsts.imc.actors;

public class RealTimeClock implements ActorClock {

	@Override
	public void sleep(long millis) throws InterruptedException {
		Thread.sleep(millis);
	}

	@Override
	public long curTime() {
		return System.currentTimeMillis();
	}

	@Override
	public long duration(long millis) {
		return millis;
	}

}
