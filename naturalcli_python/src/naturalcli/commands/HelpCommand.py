
"""
  Implements a command that outputs help information 
  to the console.
  
  @see HelpCommandExecutor
  @author Ferran Busquets
"""
class HelpCommand (Command) :
    
    def __init__(self, commands) : 
        try :
            prepare("help", "Shows the commands help on plain text.",\
                    HelpCommandExecutor(commands) )
        except InvalidSyntaxError, e :
            raise RuntimeError, e
