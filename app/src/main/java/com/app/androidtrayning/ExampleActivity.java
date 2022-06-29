package com.app.androidtrayning;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExampleActivity extends AppCompatActivity {
	/**
	 * Basic Observable, Observer, Subscriber example
	 * Observable emits list of animal names
	 * You can see Disposable introduced in this example
	 * */

	private static final String TAG = ExampleActivity.class.getSimpleName();
	private Disposable disposable;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		// Observable
		Observable<String> animalObservable = getAnimalObservable();
		// Observer
		Observer<String> animalObserver = getAnimalObserver();
		// Observer subscribing to Observable
		animalObservable
				.observeOn(Schedulers.io())
				.subscribeOn(AndroidSchedulers.mainThread())
				.subscribe(animalObserver);
	}
	private Observer<String> getAnimalObserver() {
		return new Observer<String>() {
			@Override
			public void onSubscribe(Disposable d) {
				Log.d(TAG, "onSubscribe: ");
			}

			@Override
			public void onNext(String s) {
				Log.d(TAG, "Name: " + s);
			}

			@Override
			public void onError(Throwable e) {
				Log.e(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "All items are emitted!");
			}
		};
	}
	private Observable<String> getAnimalObservable() {
	return	Observable.just("Wolf", "Lion", "Tiger", "Chicken", "Amirreza", "Dog", "Eagle");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Don't send event once the activity is destroyed
		disposable.dispose();
	}
}