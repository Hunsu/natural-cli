
"""
  Executor for <code>ExecuteFileCommand</code>
   
  @see ExecuteFileCommand
  @author Ferran Busquets
"""
class ExecuteFileCommandExecutor (ICommandExecutor) :

    COMMENT = "#"
    
    naturalCLI = None
    
    def __init__(self, naturalCLI) :
        self.naturalCLI = naturalCLI

    def execute(self, parseResult) :
        f = open(parseResult.getParameterValue(0))
        try:
            for line in f:
                if command.startswith(COMMENT) :
                    continue
                naturalCLI.execute(command, 0)
        finally:
            f.close()        
