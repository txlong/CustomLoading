/*
 * Copyright 2013 Storm Zhang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.storm.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.storm.customloading.R;

public class MainActivity extends Activity {

	private ProgressWheel pwOne, pwTwo;
	private ProgressIndicator mProgressIndicator;
	boolean running;
	int wheelProgress = 0, indicatorProgress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pwOne = (ProgressWheel) findViewById(R.id.progress_bar_one);
		pwTwo = (ProgressWheel) findViewById(R.id.progress_bar_two);
		mProgressIndicator = (ProgressIndicator) findViewById(R.id.progress_indicator);
		mProgressIndicator.setPieStyle(true);
		pwOne.spin();
//		new Thread(r).start();
		new Thread(indicatorRunnable).start();

		Button startBtn = (Button) findViewById(R.id.btn_start);
		startBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!running) {
					wheelProgress = 0;
					pwTwo.resetCount();
					new Thread(r).start();
				}
			}
		});
	}

	final Runnable r = new Runnable() {
		public void run() {
			running = true;
			while (wheelProgress < 361) {
				pwTwo.incrementProgress();
				wheelProgress++;
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			running = false;
		}
	};

	final Runnable indicatorRunnable = new Runnable() {
		public void run() {
			while (indicatorProgress < 361) {
				mProgressIndicator.setValue(indicatorProgress / (float) 360);
				indicatorProgress++;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
}
