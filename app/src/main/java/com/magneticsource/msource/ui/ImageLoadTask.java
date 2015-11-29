package com.magneticsource.msource.ui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Carga una imagen desde internet asincronamente
 * @author César Calle
 */
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

	private String url;
	private ImageView imageView;
	private int width;
	private int height;

	public ImageLoadTask(String url, ImageView imageView) {
		this.url = url;
		this.imageView = imageView;
		this.width = imageView.getDrawable().getIntrinsicWidth();
		this.height = imageView.getDrawable().getIntrinsicHeight();
	}

	@Override
	protected Bitmap doInBackground(Void... params) {
		try {
			URL urlConnection = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlConnection
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPreExecute() {
		//imageView.setImageResource(R.drawable.loading_spinner);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (result == null)
			return;
		result = Bitmap.createScaledBitmap(result, width, height, true);
		imageView.setImageBitmap(result);
	}

}