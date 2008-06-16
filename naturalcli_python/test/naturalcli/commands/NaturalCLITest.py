import unittest
from naturalcli import ICommandExecutor

class ICommandExecutor1(ICommandExecutor):
    def execute(self, parseResult): 
        print "Number:"+parseResult.getParameterValue(0)
        print "String1:"+parseResult.getParameterValue(1)
        print "String2:"+parseResult.getParameterValue(2)    

class NaturalCLITest(unittest.TestCase):  
    def setUp(self) :        
        """
          @throws java.lang.Error
        """
        self.commands = []
        naturalCLI = NaturalCLI(commands)
        commands.add(HelpCommand(commands))
        commands.add(HTMLHelpCommand(commands))
        commands.add(SleepCommand())  
        commands.add(ExecuteFileCommand(naturalCLI))
        cmd = Command("marian is the number <integer> really [<string>] of course <string>", "Just a test", ICommandExecutor1())
        commands.add(cmd)

    
    def testExecute(self):
        """
          Test method for {@link org.naturalcli.NaturalCLI#execute(java.lang.String, int)}.
        """
        naturalCLI.execute("marian is the number 1 really pau of course margaret")
#        naturalCLI.execute("marian is the number 1 really of course margaret")
