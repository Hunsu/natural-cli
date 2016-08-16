package org.naturalcli.parameters;

import org.junit.Assert;
import org.junit.Test;

public class ParameterTypesTest {

    @Test
    public final void testBinaryParamType()  {
        // Right
        Assert.assertTrue(new BinaryParamType().validateParameter("0101001"));
        // Bad
        Assert.assertFalse(new BinaryParamType().validateParameter("1234"));
        Assert.assertFalse(new BinaryParamType().validateParameter("marian"));
    }
    
    @Test
    public final void testByteParamType()  {
        // Right
        Assert.assertTrue(new ByteParamType().validateParameter("127"));
        // Bad
        Assert.assertFalse(new ByteParamType().validateParameter("1000"));
        Assert.assertFalse(new ByteParamType().validateParameter("1.2"));
        Assert.assertFalse(new ByteParamType().validateParameter("marian"));
    }
    
    
    @Test
    public final void testDoubleParamType()  {
        // Right
        Assert.assertTrue(new DoubleParamType().validateParameter(new Double(Double.MAX_VALUE).toString()));
        Assert.assertTrue(new DoubleParamType().validateParameter("12345678901234567890"));
        Assert.assertTrue(new DoubleParamType().validateParameter("1234567890.1234567890"));
        // Bad
        Assert.assertFalse(new DoubleParamType().validateParameter("sasd123.2"));
    }
    
    @Test
    public final void testEmailParamType()  {
        // Right
        Assert.assertTrue(new EmailParamType().validateParameter("asadad@asasda.com"));
        Assert.assertTrue(new EmailParamType().validateParameter("asadad@asdasd.asda.das.das.das.d.asasda.com"));
        Assert.assertTrue(new EmailParamType().validateParameter("asadad.d.d.sad.d@asdasd.asda.das.das.das.d.asasda.com"));
        // Bad
        Assert.assertFalse(new EmailParamType().validateParameter("1234567890.1234567890"));
        Assert.assertFalse(new EmailParamType().validateParameter("as as as d@ asd asd."));
        Assert.assertFalse(new EmailParamType().validateParameter("as as as d@ asd asd.com"));
        Assert.assertFalse(new EmailParamType().validateParameter("@ asd asd.com"));
        Assert.assertFalse(new EmailParamType().validateParameter("asdasd@"));
        Assert.assertFalse(new EmailParamType().validateParameter("asdasd"));
        Assert.assertFalse(new EmailParamType().validateParameter("asdasd@sadasd"));
        
    }
    
    @Test
    public final void testFileParamType()  {
        Assert.assertFalse(new FileParamType().validateParameter("marian.pau.margaret"));
  /*      if (System.getProperty("path.separator").equals(",")) // windows
            Assert.assertTrue(new FileParamType().validateParameter("C:\\"));
        else // unix
            Assert.assertTrue(new FileParamType().validateParameter("//tmp"));*/
    }
    
    @Test
    public final void testFloatParamType()  {
        // Right
        Assert.assertTrue(new FloatParamType().validateParameter(new Float(Float.MAX_VALUE).toString()));
        Assert.assertTrue(new FloatParamType().validateParameter("12345678901234567890"));
        Assert.assertTrue(new FloatParamType().validateParameter("1234567890.1234567890"));
        // Bad
        Assert.assertFalse(new FloatParamType().validateParameter("sasd123.2"));
        Assert.assertFalse(new FloatParamType().validateParameter(new Double(Double.MAX_VALUE).toString()));
    }
    
    @Test
    public final void testHexadecimalParamType()  {
        // Right
        Assert.assertTrue(new HexadecimalParamType().validateParameter("1267890"));
        Assert.assertTrue(new HexadecimalParamType().validateParameter("ABEF123"));
        // Bad
        Assert.assertFalse(new HexadecimalParamType().validateParameter("1234567890.1234567890"));
        Assert.assertFalse(new HexadecimalParamType().validateParameter("sasd123.2"));
    }
    
    @Test
    public final void testIdentifierParamType()  {
        // Right
        Assert.assertTrue(new IdentifierParamType().validateParameter("a"));
        Assert.assertTrue(new IdentifierParamType().validateParameter("asdasdasd_asdasda"));
        Assert.assertTrue(new IdentifierParamType().validateParameter("a12342"));
        Assert.assertTrue(new IdentifierParamType().validateParameter("a_1"));
        // Bad
        Assert.assertFalse(new IdentifierParamType().validateParameter("aas-asdsd-"));
        Assert.assertFalse(new IdentifierParamType().validateParameter("d d"));
        Assert.assertFalse(new IdentifierParamType().validateParameter("d d"));
        Assert.assertFalse(new IdentifierParamType().validateParameter("1234"));
        Assert.assertFalse(new IdentifierParamType().validateParameter("asdad.asdasda"));
        Assert.assertFalse(new IdentifierParamType().validateParameter("_asdsada"));
    }
    
    @Test
    public final void testIntegerParamType()  {
        // Right
        Assert.assertTrue(new IntegerParamType().validateParameter(new Integer(Integer.MAX_VALUE).toString()));
        Assert.assertTrue(new IntegerParamType().validateParameter("1234567890"));
        Assert.assertFalse(new IntegerParamType().validateParameter("1234567890.1234567890"));
        Assert.assertFalse(new IntegerParamType().validateParameter("sasd123.2"));
        Assert.assertFalse(new IntegerParamType().validateParameter(new Double(Double.MAX_VALUE).toString()));
    }
    
    @Test
    public final void testLongParamType()  {
        // Right
        Assert.assertTrue(new LongParamType().validateParameter(new Long(Long.MAX_VALUE).toString()));
        Assert.assertTrue(new LongParamType().validateParameter("922337203685477580"));
        // Bad
        Assert.assertFalse(new LongParamType().validateParameter("1234567890.1234567890"));
        Assert.assertFalse(new LongParamType().validateParameter("sasd123.2"));
        Assert.assertFalse(new LongParamType().validateParameter(new Double(Double.MAX_VALUE).toString()));
    }
    
    @Test
    public final void testOctalParamType()  {
        // Right
        Assert.assertTrue(new OctalParamType().validateParameter("1234567"));
        // Bad
        Assert.assertFalse(new OctalParamType().validateParameter("1234567890.1234567890"));
        Assert.assertFalse(new OctalParamType().validateParameter("sasd123.2"));
        Assert.assertFalse(new OctalParamType().validateParameter("12345678901234567890"));
    }
    
    @Test
    public final void testShortParamType()  {
        // Right
        Assert.assertTrue(new ShortParamType().validateParameter(new Short(Short.MAX_VALUE).toString()));
        Assert.assertTrue(new ShortParamType().validateParameter("32767"));
        // Bad
        Assert.assertFalse(new ShortParamType().validateParameter("32767.1234567890"));
        Assert.assertFalse(new ShortParamType().validateParameter("sasd123.2"));
        Assert.assertFalse(new ShortParamType().validateParameter(new Integer(Integer.MAX_VALUE).toString()));
    }
    
    @Test
    public final void testStringParamType()  {
        // Always true ;-)
    }
    
    @Test
    public final void testURLParamType()  {
        // Right
        Assert.assertTrue(new URLParamType().validateParameter("http://naturalcli.sourceforge.net/style.css"));
        Assert.assertTrue(new URLParamType().validateParameter("ftp://naturalcli.sourceforge.net/style.css"));
        // Bad
        Assert.assertFalse(new URLParamType().validateParameter("uralcli.sourceforge.net/style.css"));
    }
    
    @Test
    public final void testWorkingURLParamType()  {
        // Right
        Assert.assertTrue(new WorkingURLParamType().validateParameter("http://naturalcli.sourceforge.net/style.css"));
        // Bad
        Assert.assertFalse(new WorkingURLParamType().validateParameter("http://fail.naturalcli.sourceforge.net/"));
        Assert.assertFalse(new WorkingURLParamType().validateParameter("http://naturalcli.sourceforge.net/fail.html"));
        Assert.assertFalse(new WorkingURLParamType().validateParameter("file://fail.txt"));
    }
}
