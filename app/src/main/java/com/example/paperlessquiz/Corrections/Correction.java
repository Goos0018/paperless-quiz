package com.example.paperlessquiz.Corrections;

public class Correction{
    private boolean isCorrect;
    private boolean isCorrected;

    public Correction(boolean isCorrect, boolean isCorrected) {
        this.isCorrect = isCorrect;
        this.isCorrected = isCorrected;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public boolean isCorrected() {
        return isCorrected;
    }
}