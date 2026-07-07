package ru.one.stream.internetsercher.utils;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@Component
public class VirtualExecutorService implements ExecutorService {

    private final ExecutorService VIRTUAL_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void shutdown() {
        VIRTUAL_EXECUTOR.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return VIRTUAL_EXECUTOR.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return VIRTUAL_EXECUTOR.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return VIRTUAL_EXECUTOR.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return VIRTUAL_EXECUTOR.awaitTermination(timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        VIRTUAL_EXECUTOR.execute(command);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return VIRTUAL_EXECUTOR.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return VIRTUAL_EXECUTOR.submit(task, result);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return VIRTUAL_EXECUTOR.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return VIRTUAL_EXECUTOR.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return VIRTUAL_EXECUTOR.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return VIRTUAL_EXECUTOR.invokeAny(tasks, timeout, unit);
    }

    @PreDestroy
    public void close() {
        VIRTUAL_EXECUTOR.shutdown();
        try {
            if (!VIRTUAL_EXECUTOR.awaitTermination(5, TimeUnit.SECONDS)) {
                VIRTUAL_EXECUTOR.shutdownNow();
            }
        } catch (InterruptedException e) {
            VIRTUAL_EXECUTOR.shutdownNow();
        }
    }


    public Future<?> submit(Runnable runnable) {
        return VIRTUAL_EXECUTOR.submit(runnable);
    }
}
