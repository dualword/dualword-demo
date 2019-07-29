package com.example.android_exoplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerActivity extends Activity {
  private SimpleExoPlayer player;
  private SimpleExoPlayerView playerView;
  private DefaultTrackSelector trackSelector;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.player_activity);
    playerView = (SimpleExoPlayerView)findViewById(R.id.player_view);
    playerView.requestFocus();
  }

  @Override
  public void onNewIntent(Intent intent) {
    releasePlayer();
    setIntent(intent);
  }

  @Override
  public void onStart() {
    super.onStart();
      initializePlayer();
  }

  @Override
  public void onStop() {
    super.onStop();
      releasePlayer();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  private void initializePlayer() {
    Intent intent = getIntent();
    if (player == null) {
      trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter()));
      DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this,
          null, DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON);
      player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
      playerView.setPlayer(player);
      player.setPlayWhenReady(true);
    }

    Uri[] uris = new Uri[]{intent.getData()};
    MediaSource mediaSource = new ExtractorMediaSource(uris[0],
            new DefaultDataSourceFactory(this, Util.getUserAgent(this, "")), new DefaultExtractorsFactory(),
            new Handler(), null);
    player.prepare(mediaSource, false, false);
  }

  private void releasePlayer() {
    if (player != null) {
      player.release();
      player = null;
      trackSelector = null;
    }
  }

}
