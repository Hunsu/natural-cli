"""
  Executor for <code>HelpCommand</code>
   
  @see HelpCommandExecutor
  @author Ferran Busquets
"""
class HelpCommandExecutor (ICommandExecutor) :

    commands = None

    """
      Constructor.
      @param commands the set of commands for the help
    """    
    def __init__(self, commands) :
        self.commands = commands

    def execute(self, parseResult) :
        for c in commands : 
            if c.isHidden() :
                continue
            print c.getSyntax()
            print "\t" + c.getHelp()
            print
