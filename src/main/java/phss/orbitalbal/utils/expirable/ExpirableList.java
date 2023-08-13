package phss.orbitalbal.utils.expirable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExpirableList<T> extends ArrayList<T> {

    Thread expireThread;

    private final Map<T, Long> timeMap = new ConcurrentHashMap<>();
    private final long expiryInMillis;
    private boolean isAlive = false;

    public ExpirableList(Long expiryInMillis) {
        this.expiryInMillis = expiryInMillis;
    }

    void initialize() {
        isAlive = true;

        expireThread = new ExpireThread();
        expireThread.start();
    }

    public Long getRemainingTime(T key) {
        if (!timeMap.containsKey(key)) return 0L;

        long currentTime = new Date().getTime();
        return (timeMap.get(key) + expiryInMillis) - currentTime;
    }

    @Override
    public boolean add(T object) {
        if (!isAlive()) {
            initialize();
        }
        Date date = new Date();
        timeMap.put(object, date.getTime());

        return super.add(object);
    }

    @Override
    public boolean contains(Object key) {
        long currentTime = new Date().getTime();
        if (timeMap.containsKey(key)) {
            if (((timeMap.get(key) + expiryInMillis) - currentTime) < 0L) {
                timeMap.remove(key);
                remove(key);
            }
        }

        return super.contains(key);
    }

    @Override
    public boolean remove(Object key) {
        boolean isRemoved = super.remove(key);
        timeMap.remove(key);

        if (isEmpty()) isAlive = false;
        return isRemoved;
    }

    public boolean isAlive() {
        return isAlive;
    }

    class ExpireThread extends Thread {
        @Override
        public void run() {
            while (isAlive) {
                expireValues();

                try {
                    Thread.sleep(expiryInMillis / 2);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }

        private void expireValues() {
            long currentTime = new Date().getTime();
            for (T key : timeMap.keySet()) {
                if (((timeMap.get(key) + expiryInMillis) - currentTime) < 0L) {
                    remove(key);
                }
            }
        }
    }

}
