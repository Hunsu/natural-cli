import naturalcli
"""
  Implements a NOP command that does nothing.
  
  @see NoneCommandExecutor
  @author Ferran Busquets
 
"""
class NoneCommand (Command) :

    """
      Default contructor.
    """
    def __init__ (self):
        try :
            prepare("None command", ".Do nothing.", NoneCommandExecutor())
        except InvalidSyntaxError, e :
            raise RuntimeError, e
