import unittest
import naturalcli
import naturalcli.commands

class CommandTest(unittest.TestCase):     
    def testIsHidden(self):
       """
        Test method for {@link org.naturalcli.Command#isHidden()}.
        @throws InvalidSyntaxException
       """ 
       assert Command("marian is the best", ". Hello world", NullCommandExecutor()).isHidden()
       assert not Command("marian is the best", "Hello world", NullCommandExecutor()).isHidden()
		

