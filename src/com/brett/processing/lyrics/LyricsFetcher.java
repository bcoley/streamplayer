package com.brett.processing.lyrics;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LyricsFetcher {

	LyricsFinder finder = new LyricsFinder();

	private final ExecutorService pool = Executors.newFixedThreadPool(5);

	public Future<LyricsData> findLyricsAsync(final String artist, final String title) {
		return pool.submit(new Callable<LyricsData>() {
			public LyricsData call() throws Exception {
				return finder.find(artist, title);
			}
		});
	}

}

