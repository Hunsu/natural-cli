"""

 Represents a command definition 

 @author Ferran Busquets

"""

class Command:

    CHAR_HIDDEN_COMMAND = '.'

    help = "";
    executor = None;
    syntax = None;

    def __init__ (self):
        """
         Default constructor only for inheritors.
        """
        pass
    
    def __init__ (self, syntax, help, ce):
        """
         Constructs a new command.
              
         @param syntax the syntax for the command.
         @param helpthe help help of the command.
         @param ce command executor.
         @throws InvalidSyntaxDefinionException.
        """
        prepare(syntax, help, ce)        

    def prepare(self, syntax, help, commandexecutor):
        """
         Initialize the command.
         
         @param syntax the syntax for the command.
         @param helpthe help help of the command.
         @param ce command executor.
         @throws InvalidSyntaxDefinionException.
        """
        if help == None or len(help) == 0 :
           raise IllegalArgumentException, "Syntax cannot be empty."
        if commandexecutor == None :
           raise IllegalArgumentException, "Command executor cannot be null."
        self.help = help
        self.syntax = Syntax(syntax)
        self.executor = commandexecutor    

    def isHidden(self):
        """
         Determine if this is a hidden command.
         @return <code>true</code> if it's a hidden command, <code>false</code> if not.
        """
        return getHelp()[0] == CHAR_HIDDEN_COMMAND

    def getSyntax(self):
        """
         Returns a string with the syntax for the command.
         @return A string with the syntax for the command.
        """
        return self.syntax

    def getHelp(self):
        """
         Returns the help for the commend.
          
         @return The help for the command.
        """
        return self.help

    def getExecutor(self):
        """
         Get the executor for the command.
          
         @return the executor.
        """
        return self.executor

           