package com.mishin870.xenoseus.gif;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.view.View;

/**
 * Анимированный gif-view
 */
public class MovieGifView extends View {
	public float x, y;
	private Movie movie;
	private InputStream stream;
	private long movieStart;
	private int movieWidth, movieHeight;
	private long movieDuration;
	
	public MovieGifView(Context context) {
		super(context);
	}
	
	public MovieGifView(Context context, InputStream stream) {
		super(context);
		
		this.stream = stream;
		movie = Movie.decodeStream(this.stream);
		
		movieWidth = movie.width();
		movieHeight = movie.height();
		movieDuration = movie.duration();
	}
	
	public MovieGifView(Context context, int resourceId) {
		super(context);
		
		InputStream stream = context.getResources().openRawResource(resourceId);
		
		movie = Movie.decodeStream(stream);
		movieWidth = movie.width();
		movieHeight = movie.height();
		movieDuration = movie.duration();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(movieWidth, movieHeight);
	}
	
	public int getMovieWidth() {
		return movieWidth;
	}
	
	public int getMovieHeight() {
		return movieHeight;
	}
	
	public long getMovieDuration() {
		return movieDuration;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);
		super.onDraw(canvas);
		final long now = SystemClock.uptimeMillis();
		if (movieStart == 0)
			movieStart = now;
		
		final int diff = (int) ((now - movieStart) % movie.duration());
		movie.setTime(diff);
		movie.draw(canvas, x, y);
		this.invalidate();
	}
}