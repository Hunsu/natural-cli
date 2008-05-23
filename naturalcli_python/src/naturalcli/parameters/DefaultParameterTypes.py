import naturalcli

"""
  Helper class to obtain a set of default parameter types.
  
  @author Ferran Busquets
"""
class DefaultParameterTypes :
    
    def createSet() :
        """
          Creates a set with a default parameter types.
          
          @return a set with the default parameter types.
          @see EmailParamType
          @see IdentifierParamType
          @see IntegerParamType
          @see StringParamType
          @see FileParamType
        """
        s = []
        s.append(BinaryParamType())
        s.append(DoubleParamType())
        s.append(EmailParamType())
        s.append(FileParamType())
        s.append(FloatParamType())
        s.append(HexadecimalParamType())
        s.append(IdentifierParamType())
        s.append(IntegerParamType())
        s.append(OctalParamType())
        s.append(StringParamType())
        s.append(URLParamType())
        s.append(WorkingURLParamType())
        return s
