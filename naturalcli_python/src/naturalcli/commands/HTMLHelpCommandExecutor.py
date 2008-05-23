import naturalcli
"""
  Executor for <code>HTMLHelpCommand</code>
   
  @see HTMLHelpCommandExecutor
  @author Ferran Busquets
"""
class HTMLHelpCommandExecutor (ICommandExecutor) :

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
            syn = c.getSyntax().getDefinition().replace("<", "&lt;").replace(">", "&gt;")
            print "<b>" + syn + "</b><br>"
            print "<p>&nbsp;&nbsp;&nbsp;" + c.getHelp()+ "<br>"
            print "<p>"
