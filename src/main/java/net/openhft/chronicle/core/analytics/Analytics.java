package net.openhft.chronicle.core.analytics;

import net.openhft.chronicle.core.internal.analytics.AnalyticsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

/**
 * Provides means for libraries to report analytics to an upstream receiver.
 * <p>
 * Analytics instances only provides a best-effort to propagate
 * events to the upstream receiver.
 * <p>
 * Analytics can be turned off buy setting the system property
 * "chronicle.analytics.disable=true" prior to acquiring any Analytics
 * instances.
 */
public interface Analytics {

    /**
     * Notifies that the library has started.
     * <p>
     * Depending on settings and other conditions, the event may or may not be
     * sent upstream. For example, some implementations may
     * send a limited number of upstream events per time unit.
     * <p>
     */
    default void onStart() {
        onStart(Collections.emptyMap());
    }

    /**
     * Notifies that the library has started including the provided
     * {@code eventParameters} in the event.
     * <p>
     * Depending on settings and other conditions, the event may or may not be
     * sent upstream. For example, some implementations may
     * send a limited number of upstream events per time unit.
     * <p>
     */
    default void onStart(@NotNull Map<String, String> eventParameters) {
        onFeature("started", eventParameters);
    }

    /**
     * Notifies that a certain feature in a library has been used as
     * identified by the provided feature {@code id)}.
     * <p>
     * Depending on settings and other conditions, the event may or may not be
     * sent upstream. For example, some implementations may
     * send a limited number of upstream events per time unit.
     */
    default void onFeature(@NotNull String id) {
        onFeature(id, Collections.emptyMap());
    }

    /**
     * Notifies that a certain feature in a library has been used as
     * identified by the provided feature {@code id)} including the provided
     * {@code eventParameters} in the event.
     * <p>
     * Depending on settings and other conditions, the event may or may not be
     * sent upstream. For example, some implementations may
     * send a limited number of upstream events per time unit.
     */
    void onFeature(@NotNull String id, @NotNull Map<String, String> eventParameters);

    /**
     * Returns an existing Analytics instance for the provided {@code artifactId} and
     * the provided {@code version}. If no such instance exists, one will be created.
     *
     * @param artifactId of the library (e.g. chronicle-queue)
     * @param version of the library (e.g. 5.20.111)
     * @return an Analytics instance for the provided {@code artifactId} and
     *         the provided {@code version}
     */
    @NotNull
    static Analytics acquire(@NotNull final String artifactId, @NotNull final String version) {
        return AnalyticsUtil.acquire(artifactId, version);
    }

}