import unittest
import naturalcli

class ParameterTypesTest (unittest.TestCase):  

    
    def testBinaryParamType(self):
        # Right         
        assert BinaryParamType().validateParameter("0101001")
        # Bad 
        assert not BinaryParamType().validateParameter("1234")
        assert not BinaryParamType().validateParameter("marian")

    
    def testByteParamType(self):
        # Right 
        assert ByteParamType().validateParameter("127")
        # Bad 
        assert not ByteParamType().validateParameter("1000")
        assert not ByteParamType().validateParameter("1.2")
        assert not ByteParamType().validateParameter("marian")

    
    def testDoubleParamType(self):
        # Right 
        # assert DoubleParamType().validateParameter(new Double(Double.MAX_VALUE).toString())
        assert DoubleParamType().validateParameter("12345678901234567890")
        assert DoubleParamType().validateParameter("1234567890.1234567890")
        # Bad 
        assert not DoubleParamType().validateParameter("sasd123.2")

    
    def testEmailParamType(self):
        # Right
        assert EmailParamType().validateParameter("asadad@asasda.com")
        assert EmailParamType().validateParameter("asadad@asdasd.asda.das.das.das.d.asasda.com")
        assert EmailParamType().validateParameter("asadad.d.d.sad.d@asdasd.asda.das.das.das.d.asasda.com")
        # Bad
        assert not EmailParamType().validateParameter("1234567890.1234567890")
        assert not EmailParamType().validateParameter("as as as d@ asd asd.")
        assert not EmailParamType().validateParameter("as as as d@ asd asd.com")
        assert not EmailParamType().validateParameter("@ asd asd.com")
        assert not EmailParamType().validateParameter("asdasd@")
        assert not EmailParamType().validateParameter("asdasd")
        assert not EmailParamType().validateParameter("asdasd@sadasd")

    
    def testFileParamType(self):
        assert not FileParamType().validateParameter("marian.pau.margaret")
#      if System.getProperty("path.separator").equals(",") : # windows
#            assert FileParamType().validateParameter("C:\\"))
#       else # unix
#            assert FileParamType().validateParameter("#tmp"));*/

    
    def testFloatParamType(self):
        # Right 
#        assert FloatParamType().validateParameter(new Float(Float.MAX_VALUE).toString())
        assert FloatParamType().validateParameter("12345678901234567890")
        assert FloatParamType().validateParameter("1234567890.1234567890")
        # Bad 
        assert not FloatParamType().validateParameter("sasd123.2")
#        assert not FloatParamType().validateParameter(new Double(Double.MAX_VALUE).toString())

    
    def testHexadecimalParamType(self):
        # Right 
        assert HexadecimalParamType().validateParameter("1267890")
        assert HexadecimalParamType().validateParameter("ABEF123")
        # Bad 
        assert not HexadecimalParamType().validateParameter("1234567890.1234567890")
        assert not HexadecimalParamType().validateParameter("sasd123.2")

    
    def testIdentifierParamType(self):
        # Right 
        assert IdentifierParamType().validateParameter("a")
        assert IdentifierParamType().validateParameter("asdasdasd_asdasda")
        assert IdentifierParamType().validateParameter("a12342")
        assert IdentifierParamType().validateParameter("a_1")
        # Bad 
        assert not IdentifierParamType().validateParameter("aas-asdsd-")
        assert not IdentifierParamType().validateParameter("d d")
        assert not IdentifierParamType().validateParameter("d d")
        assert not IdentifierParamType().validateParameter("1234")
        assert not IdentifierParamType().validateParameter("asdad.asdasda")
        assert not IdentifierParamType().validateParameter("_asdsada")

    
    def testIntegerParamType(self):
        # Right 
#        assert IntegerParamType().validateParameter(new Integer(Integer.MAX_VALUE).toString())
        assert IntegerParamType().validateParameter("1234567890")
        assert not IntegerParamType().validateParameter("1234567890.1234567890")
        assert not IntegerParamType().validateParameter("sasd123.2")
#        assert not IntegerParamType().validateParameter(new Double(Double.MAX_VALUE).toString())

    
    def testLongParamType(self):
        # Right 
#        assert LongParamType().validateParameter(new Long(Long.MAX_VALUE).toString())
        assert LongParamType().validateParameter("922337203685477580")
        # Bad 
        assert not LongParamType().validateParameter("1234567890.1234567890")
        assert not LongParamType().validateParameter("sasd123.2")
#        assert not LongParamType().validateParameter(new Double(Double.MAX_VALUE).toString())

    
    def testOctalParamType(self):
        # Right 
        assert OctalParamType().validateParameter("1234567"))
        # Bad 
        assert not OctalParamType().validateParameter("1234567890.1234567890"))
        assert not OctalParamType().validateParameter("sasd123.2"))
        assert not OctalParamType().validateParameter("12345678901234567890"))

    
    def testShortParamType(self):
        # Right 
#        assert ShortParamType().validateParameter(new Short(Short.MAX_VALUE).toString())
        assert ShortParamType().validateParameter("32767")
        # Bad 
        assert not ShortParamType().validateParameter("32767.1234567890")
        assert not ShortParamType().validateParameter("sasd123.2")
#        assert not ShortParamType().validateParameter(new Integer(Integer.MAX_VALUE).toString())

    
    def testStringParamType(self):
        # Always true ;-)
        pass

    
    def testURLParamType(self):
        # Right 
        assert URLParamType().validateParameter("http:#naturalcli.sourceforge.net/style.css")
        assert URLParamType().validateParameter("ftp:#naturalcli.sourceforge.net/style.css")
        # Bad 
        assert not URLParamType().validateParameter("uralcli.sourceforge.net/style.css")

    
    def void testWorkingURLParamType(self):
        # Right 
        assert WorkingURLParamType().validateParameter("http:#naturalcli.sourceforge.net/style.css")
        # Bad 
        assert not WorkingURLParamType().validateParameter("http:#fail.naturalcli.sourceforge.net/")
        assert not WorkingURLParamType().validateParameter("http:#naturalcli.sourceforge.net/fail.html")
        assert not WorkingURLParamType().validateParameter("file:#fail.txt")
