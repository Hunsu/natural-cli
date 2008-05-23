import naturalcli
"""
  Implements a command that outputs help information 
  to the console in HTML format.
  
  @see HTMLHelpCommandExecutor
  @author Ferran Busquets
"""
class HTMLHelpCommand (Command) :

    def __init__(self, commands) :
        """
          Constructor.
          @param commands the set of commands for the help
        """    
        try :
            prepare("htmlhelp", "Shows the commands help on HTML format.",\
                    HTMLHelpCommandExecutor(commands)
            )
        except InvalidSyntaxError, e :
            raise RuntimeError, e
