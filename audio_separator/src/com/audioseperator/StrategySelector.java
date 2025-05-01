package com.audioseperator;

public class StrategySelector {

    /**
     * Factory method: return the right SeparationStrategy
     * based on a user-supplied name.
     */
    public static SeparationStrategy getStrategy(String strategyName) {
        switch (strategyName.toLowerCase()) {
            case "vocal":
                return new SpectralSubtractionStrategy();
            case "brass":
                return new BrassSeparationStrategy();
            default:
                throw new IllegalArgumentException("Unknown strategy: " + strategyName);
        }
    }
}
