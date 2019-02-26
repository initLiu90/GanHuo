package com.lzp.ganhuo.api.net;

import com.lzp.ganhuo.api.ApiCallException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * RxJava2CallAdapterFactory wrap that handle error response.
 */
public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private RxJava2CallAdapterFactory original;

    public static RxErrorHandlingCallAdapterFactory create() {
        return new RxErrorHandlingCallAdapterFactory(null, false);
    }

    public static RxErrorHandlingCallAdapterFactory createAsync() {
        return new RxErrorHandlingCallAdapterFactory(null, true);
    }

    public static RxErrorHandlingCallAdapterFactory createWithScheduler(Scheduler scheduler) {
        if (scheduler == null) throw new NullPointerException("scheduler == null");
        return new RxErrorHandlingCallAdapterFactory(scheduler, false);
    }

    private RxErrorHandlingCallAdapterFactory(Scheduler scheduler, boolean isAsync) {
        if (scheduler != null) {
            original = RxJava2CallAdapterFactory.create();
        } else if (isAsync) {
            original = RxJava2CallAdapterFactory.createAsync();
        } else {
            original = RxJava2CallAdapterFactory.create();
        }
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper<>(original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private CallAdapter wrapped;

        public RxCallAdapterWrapper(CallAdapter wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        public Object adapt(Call<R> call) {
            return ((Observable) wrapped.adapt(call))
                    .onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                        @Override
                        public ObservableSource apply(Throwable throwable) throws Exception {
                            return Observable.error(ApiCallException.from(throwable));
                        }
                    });
        }
    }
}
