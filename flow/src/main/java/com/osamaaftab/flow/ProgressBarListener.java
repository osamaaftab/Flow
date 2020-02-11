package com.osamaaftab.flow;


public interface ProgressBarListener {

    void onProgressChanged(CircularImageView circularBar, int progress, boolean fromUser);

    /**
     * User click on circular bar
     *
     * @param circularBar : The CircularBar in which click happen
     */
    void onClick(CircularImageView circularBar);

    /**
     * User long click on circular bar
     *
     * @param circularBar : The CircularBar in which long gesture happen
     */
    void onLongPress(CircularImageView circularBar);
}
