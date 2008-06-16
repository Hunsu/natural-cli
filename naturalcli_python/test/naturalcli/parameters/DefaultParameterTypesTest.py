import unittest
import naturalcli

class DefaultParameterTypesTest (unittest.TestCase):  

    """
      Test method for {@link org.naturalcli.parameters.DefaultParameterTypes#createSet()}.
    """
    
    def testCreateSet(self):
        s = DefaultParameterTypes.createSet()
        assert len(s) > 0
