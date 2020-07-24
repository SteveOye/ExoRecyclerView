package com.example.exorecyclerview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<ListModel> listModelList;
    private Context context;

    private enum VolumeState {ON, OFF}

    ;
    SimpleExoPlayer exoPlayer;

    private VolumeState volumeState;

    RequestManager requestManager;

    public RecyclerAdapter(ArrayList<ListModel> listModelList, Context context) {
        this.listModelList = listModelList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (listModelList.get(position).getDescription().equalsIgnoreCase("video")) {
            return 1;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == 0) {
            view = inflater.inflate(R.layout.recycler_item, parent, false);
            return new AdsViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.video_item, parent, false);
            return new VideoVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ListModel listModel = listModelList.get(position);

        if (listModel.getDescription().equalsIgnoreCase("video")) {
            VideoVH videoVH = (VideoVH) holder;
            initPlayer(((VideoVH) holder).playerView, listModel.getMedia_url(), ((VideoVH) holder).progressBar);
            ((VideoVH) holder).titleText.setText(listModel.getTitle());
            ((VideoVH) holder).descText.setText(listModel.getDescription());

            //Play video
        } else {
            AdsViewHolder adsViewHolder = (AdsViewHolder) holder;

            ((AdsViewHolder) holder).titleText.setText(listModel.getTitle());
            ((AdsViewHolder) holder).descText.setText(listModel.getDescription());
            Glide.with(context)
                    .load(listModel.getMedia_url())
                    .into(((AdsViewHolder) holder).imageView);
        }
    }

    private void initPlayer(final PlayerView playerView, String uri, final ProgressBar progressBar) {


        TrackSelector trackSelector = new DefaultTrackSelector();

        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        playerView.setPlayer(exoPlayer);
        exoPlayer.setPlayWhenReady(false);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "ExoRecyclerView"));

        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(uri));

        exoPlayer.prepare(videoSource);

        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        exoPlayer.seekTo(500);

        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {

                    case Player.STATE_BUFFERING:
//                        Log.e(TAG, "onPlayerStateChanged: Buffering video.");
                        if (progressBar != null) {
                            progressBar.setVisibility(VISIBLE);
                        }

                        break;
                    case Player.STATE_ENDED:
                        break;
                    case Player.STATE_IDLE:

                        break;
                    case Player.STATE_READY:
//                        Log.e(TAG, "onPlayerStateChanged: Ready to play.");
                        if (progressBar != null) {
                            progressBar.setVisibility(GONE);
                        }
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listModelList.size();
    }

    class AdsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleText, descText;

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.thumbnail);
            descText = itemView.findViewById(R.id.desc);
            titleText = itemView.findViewById(R.id.title);
        }
    }

    class VideoVH extends RecyclerView.ViewHolder {


        PlayerView playerView;
        TextView titleText, descText;
        ConstraintLayout VV;
        ProgressBar progressBar;
        ImageView volumeControl;

        public VideoVH(@NonNull View itemView) {
            super(itemView);

            playerView = itemView.findViewById(R.id.playerView);
            descText = itemView.findViewById(R.id.desc);
            titleText = itemView.findViewById(R.id.title);
            volumeControl = itemView.findViewById(R.id.volume_control);
            VV = itemView.findViewById(R.id.parent);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
