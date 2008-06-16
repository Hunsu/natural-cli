import unittest
import naturalcli

class SyntaxTest (unittest.TestCase):  

    """
      @throws java.lang.Error
    """
    def setUp(self) :
        self.pv = ParameterValidator()
    
    def testSyntax(self) :
        """
        Test method for {@link org.naturalcli.Syntax#Syntax(java.lang.String)}.
        @throws InvalidSyntaxError 
        """
        Syntax("marian is the best")
        Syntax("marian [is] [the] best")
        Syntax("marian [<integer>] [the] best")
        Syntax("marian is [<integer>] <identifier>")
        Syntax("marian is <integer> ...")
        try :
          Syntax("marian is [<integer>] ...")
          fail()
        except InvalidSyntaxError :
          pass 

        try :
          Syntax("marian is ...")
          fail()
        except InvalidSyntaxError :
          pass 

        try :
          Syntax("marian is the [<best:identifier>] <really:identifier>")
          fail()
        except InvalidSyntaxError :
          pass 

        try :
          Syntax("marian is ... [<best:identifier>] <really:identifier>")
          fail()
        except InvalidSyntaxError :
          pass 

    """
      Test method for {@link org.naturalcli.Syntax#parse(java.lang.String[], int, org.naturalcli.ParameterValidator)}.
      @throws InvalidSyntaxError 
      @throws UnknownParameterType 
      @throws InvalidSyntaxError 
      @throws UnknownParameterType 
    """
   
    def testParse(self):
        # 
        s = "marian is the best"
        pr = Syntax(s).parse([ "marian", "is", "the", "best" ], 0, pv) 
        assert None != pr
        assert [ True, True, True, True ] == pr.getTokensGiven()
        assert 0 == pr.getParameterCount())
        assert 0 == len(pr.getParameterValues())
        #
        pr = Syntax(s).parse([ "a", "b", "c", "marian", "is", "the", "best" ], 3, pv)
        assert None != pr
        assert [ True, True, True, True] == pr.getTokensGiven()
        assert 0 == pr.getParameterCount()
        assert 0 == len(pr.getParameterValues())
        assert None == Syntax(s).parse([ "marian", "is", "the", "best" ], 1, pv)
        assert None == Syntax(s).parse([ "marian", "the", "best" ], 0, pv)
        assert None == Syntax(s).parse([ "marian", "is", "the", "best", "really" ], 0, pv)
        # 
        s = "marian <hi:integer> the <bye:identifier>"
        pr = Syntax(s).parse([ "marian", "3", "the", "best" ], 0, pv)
        assert None != pr
        assert [ True, True, True, True ] == pr.getTokensGiven()
        assert 2 == pr.getParameterCount()
        assert [ 3, "best"] == pr.getParameterValues()
        pr = Syntax(s).parse([ "marian", "31231", "the", "best"], 0, pv)
        assert None != pr
        assert [ True, True, True, True ] == pr.getTkensGiven()
        assert 2 == pr.getParameterCount()
        assert [ 31231, "best" ] == pr.getParameterValues()
        assert None == Syntax(s).parse([ "marian", "is", "the", "best" ], 0, pv)
        assert None == Syntax(s).parse([ "marian", "1.2", "the", "best" ], 0, pv)
        # 
        s = "marian [<hi:integer>] the <bye:identifier>"
        pr = Syntax(s).parse([ "marian", "1", "the", "best" ], 0, pv)
        assert None != pr
        assert 2 == pr.getParameterCount()
        assert [ 1, "best" ] == pr.getParameterValues()
        assert [ True, True, True, True ] == pr.getTokensGiven()
        pr = Syntax(s).parse([ "marian", "the", "best" ], 0, pv)
        assert None != pr
        assert 2 == pr.getParameterCount()
        assert [ None, "best" ] == pr.getParameterValues()
        assert [ True, False, True, True ] == pr.getTokensGiven()
        #
        s = "marian [<hi:integer>] the [<hello:integer>] <bye:identifier>"
        pr = Syntax(s).parse([ marian", "the", "best" ], 0, pv)
        assert None != pr
        assert 3 == pr.getParameterCount()
        assert [ None, None, "best" ] == pr.getParameterValues()
        assert [ True, False, True, False, True ] == pr.getTokensGiven()
        pr = Syntax(s).parse([ "marian", "1", "the", "123", "really" ], 0, pv)
        assert None != pr
        assert 3 = pr.getParameterCount()
        assert [ 1, 123, "really" ] == pr.getParameterValues()
        assert [ True, True, True, True, True ] == pr.getTokensGiven()
        assert None == Syntax(s).parse([ "marian", "1", "the", "123" ], 0, pv)
        assert None == Syntax(s).parse([ "marian", "1", "the", "best", "really" ], 0, pv)
        assert None == Syntax(s).parse([ "marian", "1", "the" ], 0, pv)
        #
        s = "marian [<integer>] the [<identifier>] <integer>"
        pr = Syntax(s).parse([ "marian", "the", "best", "123" ], 0, pv)
        assert None != pr
        assertEquals(3, pr.getParameterCount())
        assert [ None, "best", 123 ] == pr.getParameterValues()
        assert [ True, False, True, True, True ] == pr.getTokensGiven()
        pr = Syntax(s).parse([  "marian", "the", "123" ], 0, pv)
        assert None != pr
        assert 3 == pr.getParameterCount()
        assert [ None, None, 123 ] == pr.getParameterValues()
        assert [ True, False, True, False, True ] == pr.getTokensGiven()
        #
        s = "marian is the <integer> ..."
        pr = Syntax(s).parse([ "marian", "is", "the", "1"  ], 0, pv)
        assert None != pr
        assert 1 == pr.getParameterCount()
        assert [ 1 ] == pr.getParameterValues()
        assert [ True, True, True, True, False ] == pr.getTokensGiven()
        pr = Syntax(s).parse([  "marian", "is", "the", "1", "2" ], 0, pv)
        assert None != pr
        assert 2 == pr.getParameterCount()
        assert [ 1, 2 ] == pr.getParameterValues()
        assert [ True, True, True, True, True ] == pr.getTokensGiven()
        pr = Syntax(s).parse([  "marian", "is", "the", "1", "2", "3" ], 0, pv)
        assert None != pr
        assert 3 == pr.getParameterCount()
        assert [ 1, 2, 3 ] == pr.getParameterValues()
        assert [ True, True, True, True, True ] == pr.getTokensGiven()
