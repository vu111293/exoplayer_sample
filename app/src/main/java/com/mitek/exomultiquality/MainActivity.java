package com.mitek.exomultiquality;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;

import com.mitek.exomultiquality.models.YtFragmentedVideo;
import com.mitek.exomultiquality.models.YtStream;
import com.mitek.exomultiquality.player.HexoPlayer;

import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.VideoStream;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    static String VIDEO240_STREAM_URL = "https://r3---sn-8pxuuxa-q5qee.googlevideo.com/videoplayback?expire=1578347305&ei=yVYTXp2bOsWBowPf15KAAg&ip=171.249.157.184&id=o-AFntLJawbEcX_Un2KIXvuGNcdP52RDIcrCD9tM3bePhr&itag=133&aitags=133%2C134%2C135%2C136%2C137%2C160%2C242%2C243%2C244%2C247%2C248%2C278%2C394%2C395%2C396%2C397%2C398%2C399&source=youtube&requiressl=yes&mm=31%2C29&mn=sn-8pxuuxa-q5qee%2Csn-npoe7nez&ms=au%2Crdu&mv=m&mvi=2&pl=21&initcwndbps=853750&mime=video%2Fmp4&gir=yes&clen=4211434&dur=215.506&lmt=1577471613799948&mt=1578325615&fvip=3&keepalive=yes&fexp=23842630&c=WEB&txp=5535432&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=mm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AHylml4wRgIhALgcP46PUEUdskt3aVauexJpnLZ50Ubyxi3-m7Dpwf22AiEA-7F7dBQpi_Xl7HlZoA9DSDu4HENOSTS8jx54Szg4z7k%3D&sig=ALgxI2wwRAIgJm7pT8ggi0aoTw0mT6RlcO_bVbB3FQpmFPzg4FQC7w4CIB-jclo12gxrtzQlboR__ssaC28bZunHCiJ1Ub7LIWzy";
    static String VIDEO480_STREAM_URL = "https://r3---sn-8pxuuxa-q5qee.googlevideo.com/videoplayback?expire=1578347305&ei=yVYTXp2bOsWBowPf15KAAg&ip=171.249.157.184&id=o-AFntLJawbEcX_Un2KIXvuGNcdP52RDIcrCD9tM3bePhr&itag=135&aitags=133%2C134%2C135%2C136%2C137%2C160%2C242%2C243%2C244%2C247%2C248%2C278%2C394%2C395%2C396%2C397%2C398%2C399&source=youtube&requiressl=yes&mm=31%2C29&mn=sn-8pxuuxa-q5qee%2Csn-npoe7nez&ms=au%2Crdu&mv=m&mvi=2&pl=21&initcwndbps=853750&mime=video%2Fmp4&gir=yes&clen=10880212&dur=215.506&lmt=1577471613803248&mt=1578325615&fvip=3&keepalive=yes&fexp=23842630&c=WEB&txp=5535432&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=mm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AHylml4wRgIhALgcP46PUEUdskt3aVauexJpnLZ50Ubyxi3-m7Dpwf22AiEA-7F7dBQpi_Xl7HlZoA9DSDu4HENOSTS8jx54Szg4z7k%3D&sig=ALgxI2wwRQIgOlDbSFr0Jq4SeavIwHx7LQtxeYnnXfeJGVHIKCc79AICIQClblqBe4DRGpY6ltXyoDQUYNnsDO_zd4EZRD_oLb4G9g==";
    static String AUDIO_STREAM_URL = "https://r3---sn-8pxuuxa-q5qee.googlevideo.com/videoplayback?expire=1578347305&ei=yVYTXp2bOsWBowPf15KAAg&ip=171.249.157.184&id=o-AFntLJawbEcX_Un2KIXvuGNcdP52RDIcrCD9tM3bePhr&itag=140&source=youtube&requiressl=yes&mm=31%2C29&mn=sn-8pxuuxa-q5qee%2Csn-npoe7nez&ms=au%2Crdu&mv=m&mvi=2&pl=21&initcwndbps=853750&mime=audio%2Fmp4&gir=yes&clen=3489536&dur=215.574&lmt=1577470951665372&mt=1578325615&fvip=3&keepalive=yes&fexp=23842630&c=WEB&txp=5531432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=mm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AHylml4wRgIhALgcP46PUEUdskt3aVauexJpnLZ50Ubyxi3-m7Dpwf22AiEA-7F7dBQpi_Xl7HlZoA9DSDu4HENOSTS8jx54Szg4z7k%3D&sig=ALgxI2wwRQIhAIM3Zq_E1HmGYT7rnruFeO8S1AkVNsw0ZR4LxrG9FIfNAiBERc4luzZuCx_Jlm6qsH83CsFRNY1qbrP32oPEHMfexQ==";
    static String LOCAL_VIDEO_PATH = "/storage/emulated/0/Download/vid.mp4";

    private List<YtFragmentedVideo> videoClips = new ArrayList<>();
    private HexoPlayer hexoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        print("create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hexoPlayer = (HexoPlayer) findViewById(R.id.hexoplayer);
        hexoPlayer.setActivity(this);
        hexoPlayer.playFromFile(LOCAL_VIDEO_PATH);
//        fetchClips();
    }

    @Override
    public void onResume() {
        super.onResume();
        print("resume");
    }

    @Override
    public void onPause() {
        print("pause");
        super.onPause();
        hexoPlayer.pause();
    }

    @Override
    public void onStop() {
        print("release");
        super.onStop();
        hexoPlayer.release();
    }

//    public void enterPIPMode(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
//            videoPosition = player.currentPosition
//            playerView.useController = false
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val params = PictureInPictureParams.Builder()
//                this.enterPictureInPictureMode(params.build())
//            } else {
//                this.enterPictureInPictureMode()
//            }
//            /* We need to check this because the system permission check is publically hidden for integers for non-manufacturer-built apps
//               https://github.com/aosp-mirror/platform_frameworks_base/blob/studio-3.1.2/core/java/android/app/AppOpsManager.java#L1640
//
//               ********* If we didn't have that problem *********
//                val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
//                if(appOpsManager.checkOpNoThrow(AppOpManager.OP_PICTURE_IN_PICTURE, packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).uid, packageName) == AppOpsManager.MODE_ALLOWED)
//
//                30MS window in even a restricted memory device (756mb+) is more than enough time to check, but also not have the system complain about holding an action hostage.
//             */
//            Handler().postDelayed({checkPIPPermission()}, 30)
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    public void checkPIPPermission(){
//        isPIPModeeEnabled = isInPictureInPictureMode
//        if(!isInPictureInPictureMode){
//            onBackPressed();
//        }
//    }
//
//    @Override
//    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
//        inPIP = isInPictureInPictureMode;
//        if (isInPictureInPictureMode) {
////            landscapeViews();
////            bottomAdv.setVisibility(View.GONE);
//            hexoPlayer.enterFullScreen();
//
//        } else {
//            hexoPlayer.exitFullScreen();
////            portraitViews();
//        }
//    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    class PipExtractTask extends AsyncTask<String, Void, List<YtFragmentedVideo>> {

        protected List<YtFragmentedVideo> doInBackground(String... youtubeId) {
            List<YtFragmentedVideo> clips = new ArrayList<>();
            try {
                String url = "https://www.youtube.com/watch?v=" + youtubeId[0];
                NewPipe.init(DownloaderImpl.init(null), new Localization("AR", "es"));
                StreamExtractor extractor = ServiceList.YouTube.getStreamExtractor(url);
                extractor.fetchPage();

                // Get best audio
                YtStream audioClip = null;
                for (AudioStream a : extractor.getAudioStreams()) {
                    if (a.getFormat() == MediaFormat.M4A) {
                        audioClip = new YtStream();
                        audioClip.url = a.getUrl();
                        audioClip.format = a.getFormat().suffix;
                        audioClip.height = String.valueOf(a.getAverageBitrate());
                        break;
                    }
                }

                if (audioClip == null) {
                    // get mix audio/video clip
                    return clips;
                }

                for (VideoStream v : extractor.getVideoOnlyStreams()) {
                    if (v.getFormat() == MediaFormat.MPEG_4) {
                        YtFragmentedVideo clip = new YtFragmentedVideo();
                        clip.videoFile = new YtStream(v.getUrl(), v.getResolution(), v.getFormat().suffix);
                        clip.audioFile = audioClip;
                        clip.height = Integer.parseInt(v.getResolution().substring(0, v.getResolution().length() - 1));
                        clips.add(clip);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return clips;
        }

        protected void onPostExecute(List<YtFragmentedVideo> clips) {
            videoClips = clips;
            hexoPlayer.setVideos(clips);
            hexoPlayer.play();
        }
    }

    private void doExtractVideo() {
        (new PipExtractTask()).execute("uR8Mrt1IpXg");
    }

    private void fetchClips() {
        print("fetch Clips");
        if (videoClips.size() == 0) {
            try {
                doExtractVideo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            hexoPlayer.play();
        }
    }

    // utils method
    private void print(String msg) {
        Log.d(TAG, ">>>>>>>>>>>         " + msg);
    }

}
