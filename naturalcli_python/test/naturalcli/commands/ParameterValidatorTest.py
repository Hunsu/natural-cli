import unittest
import naturalcli

class ParameterValidatorTest (unittest.TestCase):  
    def testValidate(self):
        """
          Test method for {@link org.naturalcli.ParameterValidator#validate(java.lang.String, java.lang.String)}.
        """    
        pv = ParameterValidator()
        # Right 
        assert None == pv.validate("test.afdf", "email")
        assert None == pv.validate("hello", "identifier")
        assert None == pv.validate("1234", "integer")
        assert None == pv.validate("asdadsa", "string")
        # Bad 
        assert None != pv.validate("", "email")
        assert None != pv.validate("hello", "email")
        assert None != pv.validate("1234", "email")
        assert None != pv.validate("", "identifier")
        assert None != pv.validate("test.afdf", "identifier")
        assert None != pv.validate("1234", "identifier")
        assert None != pv.validate("", "integer")
        assert None != pv.validate("hello", "integer")
        assert None != pv.validate("test.afdf", "integer")
