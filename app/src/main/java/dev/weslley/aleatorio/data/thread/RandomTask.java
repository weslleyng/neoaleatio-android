package dev.weslley.aleatorio.data.thread;

import android.os.AsyncTask;

import dev.weslley.aleatorio.service.RandomService;

public class RandomTask extends AsyncTask<Integer, Integer, Long> {


    private final RandomListener listener;
    private RandomService randomService;

    public RandomTask(RandomListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        this.randomService = new RandomService();
        listener.showProgress();
    }

    @Override
    protected Long doInBackground(Integer... integers) {
        Integer min = integers[0];
        Integer max = integers[1];
        return randomService.generateRandom(min,max);
    }

    @Override
    protected void onPostExecute(Long result) {
        listener.onResult(result);
    }

    public interface RandomListener{
        void onResult(Long result);
        void showProgress();
    }

}
