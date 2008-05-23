import naturalcli
"""
  Implements a command that executes a command list inside a file.
  
  @see ExecuteFileCommandExecutor
  @author Ferran Busquets
"""
class ExecuteFileCommand (Command) :
    
    def __init__(self, naturalCLI) :
        """
          Constructor.
          
          @param naturalCLI the naturalCLI processor to use.
        """
        try :
            prepare("execute file <filename:string>", "Execute the commands on file.",\
                    ExecuteFileCommandExecutor(naturalCLI))
        except InvalidSyntaxError, e :
            raise RuntimeError, e
