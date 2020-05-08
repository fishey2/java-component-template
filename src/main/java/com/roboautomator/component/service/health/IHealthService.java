package com.roboautomator.component.service.health;

public interface IHealthService {

    /**
     * Checks if the service is:
     * <ul>
     *     <li>Alive</li>
     *     <li>Readable</li>
     *     <li>Writable</li>
     * </ul>
     *
     * @return the {@link Health} of the service
     */
    Health getServiceHealth();
}
