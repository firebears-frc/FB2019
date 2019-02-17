package org.firebears.util;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SPI_Arduino {

    final Preferences config = Preferences.getInstance();
    final boolean DEBUG = config.getBoolean("lidar.debug", true);

    SPI SPIArd_1 = null;
    // Port port(kOnboardCS0);
    // private final Logger logger = Logger.getLogger(this.getClass().getName());

    byte[] m_rx = new byte[1];
    byte[] m_tx = { -1 };
    // tx[0] = (byte)-1;
    float m_rxin;
    int m_urixOld = -1;

    public SPI_Arduino() {
        SPIArd_1 = new SPI(Port.kOnboardCS0);
        SPIArd_1.setChipSelectActiveHigh();
    }

    public synchronized float getdistanceAA() {
        return m_rxin;
    }

    // call this in the loop. Will it cause the loop to slow down ?????
    public synchronized void getSlavebyte() {
        int urixSgned;
        float frx;
        SPIArd_1.setChipSelectActiveLow();
        // try {SPIArd_1.wait(1);}catch (InterruptedException e) {}

        // SmartDashboard.putNumber("transmitted value", tx[0]);
        SPIArd_1.transaction(m_tx, m_rx, 1);

        m_tx[0] = m_rx[0];

        int uirx = (int) m_rx[0];
        if (uirx == 0) {
            uirx = m_urixOld;
        }
        m_urixOld = uirx;
        if (DEBUG) {
            SmartDashboard.putNumber("INT recieved value", uirx);
        }

        if (uirx < 0)
            urixSgned = uirx + 256;// Since java doesn't have unsigned, convert if neg sign bit
        else
            urixSgned = uirx;
        if (DEBUG) {
            SmartDashboard.putNumber("sign Fixed recieved value", urixSgned);
        }
        /*
         * if(urixSgned < 1 || urixSgned > 252){ urixSgned = Float.NaN; }
         */

        frx = (float) urixSgned;
        float rxinl = frx / 2.54f;// to inches
        if (DEBUG) {
            SmartDashboard.putNumber("scaled recieved value", rxinl);
        }

        m_rxin = rxinl;
        if (DEBUG) {
            SmartDashboard.putNumber("recieved value", m_rxin);
        }
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
        }
        SPIArd_1.setChipSelectActiveHigh();
        // return rxin;
    }

}
