import naturalcli
"""
  Implements a command that waits for some seconds.
  
  @see SleepCommandExecutor 
  @author Ferran Busquets
"""
class SleepCommand (Command) :
    
    def __init__(self) :  
        try :
            prepare("sleep <seconds:number>", "Wait for seconds.", SleepCommandExecutor())
        except InvalidSyntaxError, e : 
            raise RuntimeError, e
