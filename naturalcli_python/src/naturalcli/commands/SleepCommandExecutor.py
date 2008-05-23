import naturalcli
"""
  Executor for <code>SleepCommand</code>
   
  @see SleepCommandExecutor
  @author Ferran Busquets
"""
import time

class SleepCommandExecutor (ICommandExecutor) :

    def execute(self, parseResult) :
        time.sleep(parseResult.getParameterValue(0))
