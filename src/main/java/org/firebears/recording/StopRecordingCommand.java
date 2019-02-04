package org.firebears.recording;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Command to stop the recording begun by {@link StartRecordingCommand}.
 */
public class StopRecordingCommand extends InstantCommand {
    
    public StopRecordingCommand(RecordingFactory factory)  {
    }

    protected void initialize() {
        StartRecordingCommand.isRecording = false;
    }

}
