package org.firebears.recording;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Command to stop the recording begun by {@link StartRecordingCommand}.
 */
public class StopRecordingCommand extends InstantCommand {

    private final Preferences config = Preferences.getInstance();
    private final boolean DEBUG = config.getBoolean("debug", false);

    public StopRecordingCommand(RecordingFactory factory) {
    }

    protected void initialize() {
        StartRecordingCommand.isRecording = false;
        if (DEBUG) {
            System.out.println("INITIALIZE: " + this);
        }
    }

}
