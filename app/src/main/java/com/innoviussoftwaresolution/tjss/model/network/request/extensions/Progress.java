package com.innoviussoftwaresolution.tjss.model.network.request.extensions;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.utils.M;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author Sony Raj on 25-10-17.
 */

public final class Progress {

    public static final int UPLOAD_STARTED = 1;
    public static final int UPLOAD_SUCCESS = 2;
    public static final int UPLOAD_FAILED = 3;

    public void run(String url, final ProgressListener progressListener,
                    UploadStatusListener statusListener) throws Exception {
        statusListener.onStatusChanged(UPLOAD_STARTED);
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response original = chain.proceed(chain.request());
                        return original.newBuilder()
                                .body(new ProgressResponseBody(original.body(), progressListener))
                                .build();
                    }
                })
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                statusListener.onStatusChanged(UPLOAD_FAILED);
            } else {
                statusListener.onStatusChanged(UPLOAD_SUCCESS);
            }
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            M.log(e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }

    }

    public interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }

    @IntDef({UPLOAD_STARTED, UPLOAD_SUCCESS, UPLOAD_FAILED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UploadStatus {
    }

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody mResponseBody;
        private final ProgressListener mListener;
        private BufferedSource mBufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
            mResponseBody = responseBody;
            mListener = listener;
        }

        @Nullable
        @Override
        public MediaType contentType() {
            return mResponseBody.contentType();
        }

        @Override
        public long contentLength() {
            return mResponseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (mBufferedSource == null) {
                mBufferedSource = Okio.buffer(source(mResponseBody.source()));
            }
            return mBufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    mListener.update(totalBytesRead, mResponseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }

    }

}
