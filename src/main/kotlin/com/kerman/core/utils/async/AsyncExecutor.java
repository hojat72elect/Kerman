package com.kerman.core.utils.async;

import com.kerman.core.utils.Disposable;
import com.kerman.core.utils.KermanRuntimeException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Info : This class was inspired by "com.badlogic.gdx.utils.async.AsyncExecutor".
 * <p>
 * Allows asynchronous execution of {@link AsyncTask} instances on a separate thread. Needs to be disposed via a call to
 * {@link #dispose()} when no longer used, in which case the executor waits for running tasks to finish. Scheduled but not yet
 * running tasks will not be executed.
 */
public class AsyncExecutor implements Disposable {
    private final ExecutorService executor;

    /**
     * Creates a new AsyncExecutor with the name "AsyncExecutor-Thread".
     */
    public AsyncExecutor(int maxConcurrent) {
        this(maxConcurrent, "AsyncExecutor-Thread");
    }

    /**
     * Creates a new AsyncExecutor that allows maxConcurrent {@link Runnable} instances to run in parallel.
     * @param name          The name of the threads.
     */
    public AsyncExecutor(int maxConcurrent, final String name) {
        executor = Executors.newFixedThreadPool(maxConcurrent, r -> {
            Thread thread = new Thread(r, name);
            thread.setDaemon(true);
            return thread;
        });
    }

    /**
     * Submits a {@link Runnable} to be executed asynchronously. If maxConcurrent runnables are already running, the runnable will
     * be queued.
     *
     * @param task the task to execute asynchronously
     */
    public <T> AsyncResult<T> submit(final AsyncTask<T> task) {
        if (executor.isShutdown()) {
            throw new KermanRuntimeException("Cannot run tasks on an executor that has been shutdown (disposed)");
        }
        return new AsyncResult<>(executor.submit(task::call));
    }

    /**
     * Waits for running {@link AsyncTask} instances to finish, then destroys any resources like threads. Can not be used after
     * this method is called.
     */
    @Override
    public void dispose() {
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new KermanRuntimeException("Couldn't shutdown loading thread", e);
        }
    }
}
