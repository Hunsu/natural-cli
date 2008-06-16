import unittest
import naturalcli


class TokenTest (unittest.TestCase):  

    """
      Test method for {@link org.naturalcli.Token#isOptional()}.
    """
    
    def testSetText(self):
        # ok
        Token("marian")
        Token("<marian>")
        Token("[marian]")
        Token("[<marian>]")
        # not ok
        try :
            Token("marian]")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("marian>")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("marian>]") 
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("<marian")
            fail() 
        except InvalidTokenError:
            pass
        
        try :
            Token("<marian]") 
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("<marian>]")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("<[marian")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("<[marian>")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("<[marian>]")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("[marian")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("[marian>")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("[marian>]")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token("")
            fail() 
        except InvalidTokenError:
            pass
        
        try : 
            Token(None)
            fail() 
        except InvalidTokenError:
            pass

    
    def testIsOptional(self):
        """
          Test method for {@link org.naturalcli.Token#isOptional()}.
        """
        # true
        assert Token("[marian]").isOptional()
        # false
        assert Token("marian").isOptional()
        assert Token("ma[]rian").isOptional()
    
    def testIsParameter(self):
        """
          Test method for {@link org.naturalcli.Token#isParameter()}.
        """
        # true
        assert Token("<marian>").isParameter()
        assert Token("[<marian>]").isParameter()
        # false
        assert Token("marian").isParameter()
        assert Token("ma<>rian").isParameter()

    
    def testIsOptionalParameter(self):
        """
          Test method for {@link org.naturalcli.Token#isOptionalParameter()}.
        """
        # true
        assert Token("[<marian>]").isOptionalParameter()
        assert Token("[<marian:integer>]").isOptionalParameter()
        # false
        assert Token("marian").isOptionalParameter()
        assert Token("[marian]").isOptionalParameter()
        assert Token("<marian:integer>").isOptionalParameter()

    """
      Test method for {@link org.naturalcli.Token#getParameterName()}.
    """
    
    def testGetParameterName(self):
        # None
        assert None == Token("marian").getParameterName()
        # equals
        assert "marian" == Token("<marian>").getParameterName()
        assert "marian" == Token("<marian:integer>").getParameterName()
        assert "marian" == Token("<marian:email>").getParameterName()

    
    def testGetParameterTypeName(self):
        """
          Test method for {@link org.naturalcli.Token#getParameterTypeName()}.
        """
        # None
        assert None == Token("marian").getParameterTypeName()
        # equals
        assert "integer" == Token("<integer>").getParameterTypeName()
        assert "integer" == Token("<marian:integer>").getParameterTypeName()
        assert "email" == Token("<marian:email>").getParameterTypeName()

    
    def testIsIdentifier(self):
        """
          Test method for {@link org.naturalcli.Token#isIdentifier()}.
        """
        # Ok
        assert Token("marian").isIdentifier()
        assert Token("[marian]").isIdentifier()
        # Not ok
        assert not Token("<marian>").isIdentifier()
        assert not Token("[<marian>]").isIdentifier()

    
    def testGetWord(self):
        """
          Test method for {@link org.naturalcli.Token#getWord()}.
        """
        assert "marian" == Token("<marian>").getWord())
        assert "marian" == Token("[marian]").getWord())
        assert "marian:integer" == Token("<marian:integer>").getWord())
        assert "marian:email" == Token("[<marian:email>]").getWord())

    """
      Test method for {@link org.naturalcli.Token#matches(java.lang.String, org.naturalcli.parameters.ParameterValidator)}.
      @throws UnknownParameterType 
    """
    
    def testMatches(self):
        ParameterValidator pv = new ParameterValidator()
        # Ok
        assert Token("marian").matches("marian", pv))
        assert Token("<integer>").matches("1234", pv))
        assert Token("<marian:email>").matches("marian@marian.org", pv))
        # Not ok
        assert not Token("marian").matches("1234", pv))
        assert not Token("marian").matches(None, pv))
        assert not Token("marian").matches("", pv))
        assert not Token("<integer>").matches("marian", pv))
        assert not Token("<marian:email>").matches("marian", pv))
