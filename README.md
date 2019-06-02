# FB2019

Our robot..

## Converting to the new WPILibJ:
* Change all subsystems to extend SendableSubsystemBase
  * Remove initDefaultCommand method.
* Change all commands to extend SendableCommandBase
  * methods become public:  initialize, execute, isFinished, end
  * Change end method to take a boolean argument
  * requires becomes addRequirement

## TODO:
* Rewrite Config class to not use Preferences
* Figure out how to mock ShuffleBoardTab
* Mocking NetworkTables
