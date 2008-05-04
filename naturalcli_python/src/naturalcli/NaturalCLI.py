
"""
  A set of commands understood by the CLI
  
  @author Ferran Busquets
"""
class NaturalCLI :
    
    commands=[]
    
    pv = None
    
    def __init__(self, commands, pv) :
        """
          Creates a new instance.
          
          @param commands the set of commands that can be executed.
          @param pv the parameter validator.
        """
        self.commands = commands
        self.pv = pv

    def __init__(self, commands) :
        """ 
          Creates a new instance with the default parameter validator.
          
          @param commands the set of commands that can be executed.
        """
        self.commands = commands
        self.pv = ParameterValidator()

    def execute_args_list(self, args, first) :
        """
          Runs a command based on the arguments.
          
          @param args  the arguments to be parsed
          @param first the index on <code>args</code> of the first string for the arguments.
          @throws ExecutionError 
        """
        if args == None :
            raise IllegalArgumentError, "The parameter for the arguments cannot be None."
        if len(args) == 0 :
            return
        # Look for the command that the parse works
        parseResult = None
        command = None
        for c in commands : 
            try :
                parseResult = c.getSyntax().parse(args, first, pv)
                if parseResult != None :
                    command = c
                    break
            except UnknownParameterType, e :
                raise ExecutionError, "Unknown parameter type when matching command."+e
        if command == None :
            raise ExecutionError, "No command matches."
        # Execute the command
        command.getExecutor().execute(parseResult)

    def execute(self, args, first) :
        """
          Runs a command based on the arguments.
          
          @param args  the string arguments to run.
          @param first the index on <code>args</code> of the first string for the arguments.
          @throws ExecutionError 
          @throws UnknownParameterType 
        """
        if args == None :
            raise IllegalArgumentError, "The parameter argument string cannot be None."
        execute_args_list(args.split(" "), first)

    def execute_args_list(self, args) :
        """
          Runs a command based on the arguments.
          
          @param args  the arguments to be parsed
          @throws ExecutionError 
        """
        execute_args_list(args, 0)

    def execute(self, args) :
        """
          Runs a command based on the arguments in the string.
          
          @param args  the string with the arguments to be parsed
          @throws ExecutionError 
        """
        execute(args, 0)
